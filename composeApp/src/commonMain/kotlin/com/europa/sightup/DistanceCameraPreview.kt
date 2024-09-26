// commonMain/kotlin/DistanceCameraPreview.kt
package com.europa.sightup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun DistanceCameraPreview(
    distance: State<String>,
    aspectRatio: Float
): CameraAction

interface CameraAction {
    fun startCamera()
    fun stopCamera()
}