// commonMain > DistanceCameraPreview.kt
package com.europa.sightup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

interface Camera {
    val distanceToCamera: State<String>
}

@Composable
expect fun DistanceCameraPreview(
    distance: State<String>,
    aspectRatio: Float,
): Camera

