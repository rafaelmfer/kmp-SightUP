package com.europa.sightup.presentation.screens.test

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
actual fun DistanceToCamera(
    distance: State<String>,
    showCameraView: Boolean,
): Camera {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val cameraInstance = remember {
        AndroidCameraImpl(
            context = context,
            previewView = previewView,
            lifecycleOwner = lifecycleOwner,
            distanceState = distance as MutableState<String>
        )
    }

    CameraPreview(previewView, showCameraView)

    return object : Camera {
        override val getDistanceToCamera: State<String>
            get() = cameraInstance.distanceState
    }

}

@Composable
fun CameraPreview(
    previewView: PreviewView,
    showCameraView: Boolean,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = if (showCameraView) Modifier.fillMaxSize() else Modifier.height(0.dp),
            factory = { previewView },
            update = {
                previewView.scaleType = PreviewView.ScaleType.FILL_CENTER
            }
        )
    }
}