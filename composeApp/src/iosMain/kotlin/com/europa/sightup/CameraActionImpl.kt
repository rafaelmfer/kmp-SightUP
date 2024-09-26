package com.europa.sightup

import platform.AVFoundation.AVCaptureSession

//@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
class CameraActionImpl(
    private val session: AVCaptureSession
)  : CameraAction {

    override fun startCamera() {
        session.startRunning()
    }

    override fun stopCamera() {
     //       cameraActionImpl.stopCamera()
        session.stopRunning()
    }

}
