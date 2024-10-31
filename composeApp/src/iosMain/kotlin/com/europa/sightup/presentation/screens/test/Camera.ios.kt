package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.UIKitView
import com.europa.sightup.native.CameraViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType

//@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
//class CameraImpl : Camera {
//    private val _distanceToCamera = mutableStateOf("Unknown")
//    override val getDistanceToCamera: State<String> get() = _distanceToCamera
//
//    fun updateDistance(newDistance: String) {
//        //println("CameraImpl: Updating distance to $newDistance")
//        _distanceToCamera.value = newDistance
//    }
//}

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
class CameraImpl(
    private val distanceState: MutableState<String>
) : Camera {

    override val getDistanceToCamera: State<String> get() = distanceState

    fun updateDistance(newDistance: String) {
        distanceState.value = newDistance
    }
}


private sealed interface CameraAccess {
    data object Undefined : CameraAccess
    data object Denied : CameraAccess
    data object Authorized : CameraAccess
}

@Composable
actual fun DistanceToCamera(
    distance: State<String> ,
    aspectRatio: Float,
    showCameraView: Boolean,
): Camera {

    var cameraAccess by remember { mutableStateOf<CameraAccess>(CameraAccess.Undefined) }
    val cameraImpl = remember { CameraImpl(
        distanceState = distance as MutableState<String>
    ) }

    // Crear un nuevo `State` que observe `cameraImpl.getDistanceToCamera`
   // val distanceState = remember { mutableStateOf(cameraImpl.getDistanceToCamera.value) }

    // Actualizar `distanceState` cuando cambia `cameraImpl.getDistanceToCamera`
//    LaunchedEffect(cameraImpl.getDistanceToCamera.value) {
//        distanceState.value = cameraImpl.getDistanceToCamera.value
//        println("iOS: Updated internal distanceState to ${distanceState.value}")
//    }

//    // Sincronizar `distance` con el valor de `cameraImpl.getDistanceToCamera`
//    LaunchedEffect(cameraImpl.getDistanceToCamera.value) {
//        val newDistance = cameraImpl.getDistanceToCamera.value
//        (distance as? MutableState<String>)?.value = newDistance
//        println("iOS: Updated distance in composable to $newDistance")  // pero lo que tengo que actualizar es distance, no?
//    }

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

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when (cameraAccess) {
            CameraAccess.Undefined -> CircularProgressIndicator(color = Color.White)
            CameraAccess.Denied -> Text("Camera access denied", color = Color.White)
            CameraAccess.Authorized -> DisplayCamera(cameraImpl = cameraImpl)
        }
    }
    return cameraImpl
}

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun DisplayCamera(cameraImpl: CameraImpl) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val viewController = CameraViewController().apply {
                setDistanceUpdateCallback { distance ->
                    if (distance != null) {
                        cameraImpl.updateDistance(distance)
                    }
                }
            }
            viewController.view
        }
    )
}