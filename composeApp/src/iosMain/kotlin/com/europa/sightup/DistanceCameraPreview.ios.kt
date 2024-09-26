package com.europa.sightup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.position
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun DistanceCameraPreview(
    distance: State<String>,
    aspectRatio: Float,
): CameraAction {

    // Inicializar cámara
    val device = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
        (device as AVCaptureDevice).position == AVCaptureDevicePositionFront
    }!! as AVCaptureDevice

    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null) as AVCaptureDeviceInput

    val output = AVCaptureStillImageOutput().apply {
        outputSettings = mapOf(AVVideoCodecKey to AVVideoCodecJPEG)
    }

    val session = AVCaptureSession().apply {
        sessionPreset = AVCaptureSessionPresetPhoto
        addInput(input)
        addOutput(output)
    }

    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val container = UIView().apply {
                layer.addSublayer(cameraPreviewLayer)
            }
            session.startRunning()
            container
        },
        onRelease = {
            session.stopRunning()
        }
    )

    // Devuelve una instancia de CameraAction para acciones adicionales
    return CameraActionImpl(session)
}
