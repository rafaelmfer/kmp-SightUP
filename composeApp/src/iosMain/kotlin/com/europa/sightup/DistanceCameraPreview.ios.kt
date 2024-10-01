package com.europa.sightup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import com.europa.sightup.native.CameraViewController

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
class CameraImpl : Camera {

    private var _distanceToCamera = mutableStateOf("Unknown")
    override val distanceToCamera: State<String> get() = _distanceToCamera

    fun updateDistance(newDistance: String) {
        _distanceToCamera.value = newDistance
    }
}

private sealed interface CameraAccess {
    data object Undefined : CameraAccess
    data object Denied : CameraAccess
    data object Authorized : CameraAccess
}

@Composable
actual fun DistanceCameraPreview(
    distance: State<String>,
    aspectRatio: Float,
): Camera {

    var cameraAccess by remember { mutableStateOf<CameraAccess>(CameraAccess.Undefined) }
    val cameraImpl = remember { CameraImpl() }

    LaunchedEffect(Unit) {
        when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
            AVAuthorizationStatusAuthorized -> {
                cameraAccess = CameraAccess.Authorized
            }

            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                cameraAccess = CameraAccess.Denied
            }

            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { success ->
                    cameraAccess = if (success) CameraAccess.Authorized else CameraAccess.Denied
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
        when (cameraAccess) {
            CameraAccess.Undefined -> {
                CircularProgressIndicator(color = Color.White)
            }

            CameraAccess.Denied -> {
                Text("Camera access denied", color = Color.White)
            }

            CameraAccess.Authorized -> {
                DisplayCamera(cameraImpl = cameraImpl)
            }
        }
    }

    return CameraImpl()
}

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun DisplayCamera(cameraImpl: CameraImpl) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val viewController = CameraViewController()

           val distance = viewController.onDistanceUpdate()
           cameraImpl.updateDistance(distance.toString())

            viewController.view
        }
    )
}


