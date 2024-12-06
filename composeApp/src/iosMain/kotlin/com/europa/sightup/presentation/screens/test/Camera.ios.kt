package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.UIKitView
import com.europa.sightup.native.CameraViewController
import kotlinx.cinterop.ExperimentalForeignApi

@Composable
actual fun DistanceToCamera(
    distance: State<String>,
    showCameraView: Boolean,
): Camera {

    val cameraImpl = remember {
        CameraImpl(
            distanceState = distance as MutableState<String>
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        DisplayCamera(cameraImpl = cameraImpl)
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

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
class CameraImpl(
    private val distanceState: MutableState<String>,
) : Camera {

    override val getDistanceToCamera: State<String> get() = distanceState

    fun updateDistance(newDistance: String) {
        distanceState.value = newDistance
    }
}