package com.europa.sightup.presentation.screens.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

interface Camera {
    val getDistanceToCamera: State<String>
}

@Composable
expect fun DistanceToCamera(
    distance: State<String>,
    aspectRatio: Float,
    showCameraView: Boolean = true,
): Camera