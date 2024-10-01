package com.europa.sightup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AndroidCamera(
    private val context: Context,
    private val previewView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
    val distanceState: MutableState<String>
){

    data class CameraInfo(val focalLengthMm: Float, val pixelSizeMmX: Float)

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val faceDetector: FaceDetector by lazy {
        FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .build()
        )
    }

    private val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    init {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        context, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() {
        if (lifecycleOwner is androidx.activity.ComponentActivity) {
            ActivityCompat.requestPermissions(
                lifecycleOwner,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    // This function handles the response from the user when permission is requested**
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                distanceState.value = "Camera permission denied"
            }
        }
    }


    @OptIn(ExperimentalGetImage::class)
    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }
            val imageAnalyzer = setupImageAnalyzer()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_FRONT_CAMERA,
                    preview, imageAnalyzer
                )
            } catch (e: Exception) {
                Log.e("Camera", "Camera bind failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun setupImageAnalyzer(): ImageAnalysis {
        return ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor) { imageProxy -> processImageProxy(imageProxy) }
            }
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        faceDetector.process(inputImage)
            .addOnSuccessListener { faces ->
                faces.firstOrNull()?.let { face ->
                    getFocalLengthAndPixelSize()?.let { cameraInfo ->
                        val distance = calculateDistance(face, cameraInfo.focalLengthMm, cameraInfo.pixelSizeMmX)
                        distanceState.value = "%.2f cm".format(distance)
                    } ?: run {
                        distanceState.value = "Error calculating"
                    }
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }

    private fun calculateDistance(face: Face, focalLengthMm: Float, pixelSizeMmX: Float): Float {
        val REAL_FACE_WIDTH_CM = 14f
        val faceWidthPixels = face.boundingBox.width()
        val faceWidthMm = faceWidthPixels * pixelSizeMmX
        val correctionFactor = 4.5f
        return (REAL_FACE_WIDTH_CM * focalLengthMm) / (faceWidthMm * correctionFactor)
    }

    private fun getFocalLengthAndPixelSize(): CameraInfo? {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val frontCameraId = cameraManager.cameraIdList.firstOrNull { id ->
            cameraManager.getCameraCharacteristics(id)
                .get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        } ?: return null

        val characteristics = cameraManager.getCameraCharacteristics(frontCameraId)
        val focalLengthMm = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)?.firstOrNull()
        val sensorWidthMm = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)?.width ?: 0f
        val pixelWidth = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)?.width ?: 0
        val pixelSizeMmX = if (pixelWidth > 0) sensorWidthMm / pixelWidth else 0f

        return CameraInfo(focalLengthMm ?: 0f, pixelSizeMmX)
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }
}
