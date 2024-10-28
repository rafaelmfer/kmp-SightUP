package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
import platform.AVFoundation.requestAccessForMediaType
import platform.UIKit.UIColor
import platform.UIKit.UIView

//@OptIn(ExperimentalForeignApi::class)
//@Composable
//actual fun DistanceToCamera(
//    distance: State<String>,
//    aspectRatio: Float,
//    showCameraView: Boolean,
//): Camera {

@OptIn(ExperimentalForeignApi::class)
@Composable
fun failedCamera () {

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

    // Tal vez el problema esta aca?
    val cameraPreviewLayer = AVCaptureVideoPreviewLayer(session = session)

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val container = UIView()

//            val container = object : UIView() {
//                override fun layoutSubviews() {
//                    super.layoutSubviews()
//                    cameraPreviewLayer.frame = bounds // Asegúrate de actualizar el frame
//                }
//            }

            container.layer.borderColor = UIColor.redColor.CGColor
            container.layer.borderWidth = 2.0

            // Camera preview layer no se ve
            cameraPreviewLayer.backgroundColor = UIColor.greenColor.CGColor
            cameraPreviewLayer.borderColor = UIColor.blueColor.CGColor
            cameraPreviewLayer.borderWidth = 2.0

            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            container.layer.addSublayer(cameraPreviewLayer)
            println("Added cameraPreviewLayer. Total sublayers: ${container.layer.sublayers?.size}")
            container.layer.sublayers?.forEach { layer ->
                println("Layer: $layer")
            }

            // Ajusta el frame después de agregar el layer
            //  container.layoutIfNeeded()
            //cameraPreviewLayer.frame = container.bounds


            AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                if (granted) {
                    CoroutineScope(Dispatchers.Default).launch {
                        println("Attempting to start camera session") // Esto se ve
                        session.startRunning() // Esto se ve
                        println(session.connections().joinToString(" ----- "))
                        println("Camera session started: ${session.isRunning()}") // Esto no se ve
                    }
                } else {
                    println("Camera access denied")
                }
            }

            container
        },

        onRelease = {
            session.stopRunning()
        }
    )

//    return object : Camera {
//        override val getDistanceToCamera: State<String>
//            get() = distance
//    }
}

