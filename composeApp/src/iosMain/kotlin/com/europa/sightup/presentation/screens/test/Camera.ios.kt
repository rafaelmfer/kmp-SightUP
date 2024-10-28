package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.position
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun DistanceToCamera(
    distance: State<String>,
    aspectRatio: Float,
    showCameraView: Boolean,
): Camera {
//    val device = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
//        (device as AVCaptureDevice).position == AVCaptureDevicePositionFront
//    }!! as AVCaptureDevice
    val device = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
        (device as AVCaptureDevice).position == AVCaptureDevicePositionFront
    } as? AVCaptureDevice ?: throw IllegalStateException("No front camera found")


    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null) as AVCaptureDeviceInput

    val output = AVCaptureStillImageOutput()
    output.outputSettings = mapOf(AVVideoCodecKey to AVVideoCodecJPEG)

    val session = AVCaptureSession()

    session.sessionPreset = AVCaptureSessionPresetPhoto

    session.addInput(input)
    session.addOutput(output)

    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }

    UIKitView(
        modifier = Modifier.fillMaxSize().background(Color.Cyan),
        factory = {
            val container = UIView()
            container.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            cameraPreviewLayer.frame = container.bounds
            session.startRunning()



            container
        },

    )
// Deberia incluirlo ?
//    session.stopRunning()

    return object : Camera {
        override val getDistanceToCamera: State<String>
            get() = distance
    }

}
