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

// CameraX implementation using ML Kit for face detection
class AndroidCameraAction(
    private val context: Context,
    private val previewView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
    private val distanceState: MutableState<String> // State to update distance
) : CameraAction {

    data class CameraInfo(
        val focalLengthMm: Float,
        val pixelSizeMmX: Float,
    )

    // Camera executor for handling background tasks
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    // FaceDetector setup using ML Kit
    private val faceDetector: FaceDetector by lazy {
        FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                .build()
        )
    }

    // CameraX provider to handle camera lifecycle
    private val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    init {
        // Check if permissions are granted, if not, request them
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    // Function to check if permissions are granted
    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Function to request camera permission
    private fun requestCameraPermission() {
        if (lifecycleOwner is androidx.activity.ComponentActivity) {
            ActivityCompat.requestPermissions(
                lifecycleOwner,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Callback when permission result is received
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the camera
                startCamera()
            } else {
                // Permission denied, handle the case
                distanceState.value = "Camera permission denied"
            }
        }
    }

    // Function to start the camera using CameraX
    @OptIn(ExperimentalGetImage::class)
    override fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Setup for the preview
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            // Setup for image analysis (face detection)
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, ImageAnalysis.Analyzer { imageProxy ->
                        processImageProxy(imageProxy)
                    })
                }

            // Use the front camera for analysis
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind any previously bound use cases before rebinding
                cameraProvider.unbindAll()
                // Bind the preview and image analysis use cases to the camera lifecycle
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer)
            } catch (e: Exception) {
                Log.e("Camera", "Camera bind failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    // Stop camera operation
    override fun stopCamera() {
        cameraProviderFuture.get().unbindAll()
    }

    // Calculate distance based on face bounding box and camera characteristics
    private fun calculateDistance(face: Face, focalLengthMm: Float, pixelSizeMmX: Float): Float {
        val REAL_FACE_WIDTH_CM = 15f // Average face width
        val faceWidthPixels = face.boundingBox.width()
        if (faceWidthPixels <= 0) return 0f
        val faceWidthMm = faceWidthPixels * pixelSizeMmX
        return (REAL_FACE_WIDTH_CM * focalLengthMm) / (faceWidthMm*7f)
    }

    // Process the image using ML Kit for face detection
    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        faceDetector.process(inputImage)
            .addOnSuccessListener { faces ->
                faces.firstOrNull()?.let { face ->
                    val cameraInfo = getFocalLengthAndPixelSize()
                    if (cameraInfo != null) {
                        val distance = calculateDistance(face, cameraInfo.focalLengthMm, cameraInfo.pixelSizeMmX)
                        distanceState.value = "%.2f cm".format(distance) // Update distance state
                    } else {
                        distanceState.value = "Error calculating"
                    }
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }

    // Retrieve focal length and pixel size from camera characteristics
    private fun getFocalLengthAndPixelSize(): CameraInfo? {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val frontCameraId = getFrontCameraId(cameraManager) ?: return null
        val characteristics = cameraManager.getCameraCharacteristics(frontCameraId)

        // Get the focal length in mm
        val focalLengthMm = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)?.firstOrNull()
        val sensorWidthMm = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)?.width ?: 0f
        val pixelWidth = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)?.width ?: 0
        val pixelSizeMmX = if (pixelWidth > 0) sensorWidthMm / pixelWidth else 0f

        return CameraInfo(focalLengthMm ?: 0f, pixelSizeMmX)
    }

    // Retrieve the front camera ID
    private fun getFrontCameraId(cameraManager: CameraManager): String? {
        return cameraManager.cameraIdList.firstOrNull { id ->
            val characteristics = cameraManager.getCameraCharacteristics(id)
            characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }
}
