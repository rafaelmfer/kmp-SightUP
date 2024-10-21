package com.europa.sightup.presentation.screens.test

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AndroidCameraImpl(
    private val context: Context,
    private val previewView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
    val distanceState: MutableState<String>,
) {

    private val cameraExecutor: ExecutorService = Executors.newCachedThreadPool()

    init {
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Setup the preview view
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, FaceAnalyzer { faces ->

                        if (faces.isNotEmpty()) {
                            val firstFace = faces.first()
                            val cameraInfo = getFocalLengthAndPixelSize()
                            val distance = calculateDistance(
                                firstFace,
                                cameraInfo.focalLengthMm,
                                cameraInfo.pixelSizeMmX
                            )
                            distanceState.value = distance.toString()
                        } else {
                            distanceState.value = "No face detected"
                        }
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                //cameraProvider.unbind(preview, imageAnalyzer)

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalyzer
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun calculateDistance(
        face: Face,
        focalLengthMm: Float,
        pixelSizeMmX: Float,
        realFaceWidthCm: Float = 14.0f,
        correctionFactor: Float = 5.0f,
    ): Float {
        val faceWidthPixels = face.boundingBox.width()
        val faceWidthMm = faceWidthPixels * pixelSizeMmX
        return (realFaceWidthCm * focalLengthMm) / (faceWidthMm * correctionFactor)
    }

    private fun getFocalLengthAndPixelSize(): CameraInfo {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val frontCameraId = cameraManager.cameraIdList.firstOrNull { id ->
            cameraManager.getCameraCharacteristics(id)
                .get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        }

        val defaultFocalLengthMm = 4.15f
        val defaultPixelSizeMmX = 0.0012f

        if (frontCameraId == null) {
            return CameraInfo(defaultFocalLengthMm, defaultPixelSizeMmX)
        }

        val characteristics = cameraManager.getCameraCharacteristics(frontCameraId)
        val focalLengthMm = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)?.firstOrNull()
            ?: defaultFocalLengthMm
        val sensorWidthMm = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)?.width
            ?: return CameraInfo(focalLengthMm, defaultPixelSizeMmX)
        val pixelWidth = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)?.width ?: 0
        val pixelSizeMmX = if (pixelWidth > 0) sensorWidthMm / pixelWidth else defaultPixelSizeMmX

        return CameraInfo(focalLengthMm, pixelSizeMmX)
    }

    data class CameraInfo(
        val focalLengthMm: Float,
        val pixelSizeMmX: Float,
    )

    fun shutdown() {
        cameraExecutor.shutdown()
    }

    inner class FaceAnalyzer(private val onFacesDetected: (List<Face>) -> Unit) : ImageAnalysis.Analyzer {
        private val detector = FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .build()
        )

        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                detector.process(image)
                    .addOnSuccessListener { faces ->
                        onFacesDetected(faces)
                        if (faces.isNotEmpty()) {
                            val cameraInfo = getFocalLengthAndPixelSize()
                            val distance = calculateDistance(faces[0], cameraInfo.focalLengthMm, cameraInfo.pixelSizeMmX)
                            distanceState.value = distance.toString()
                        } else {
                            distanceState.value = "No face detected"
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("FaceAnalyzer", "Face detection failed", e)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }
}
