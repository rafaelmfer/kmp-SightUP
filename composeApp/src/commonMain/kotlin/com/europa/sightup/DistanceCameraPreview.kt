// commonMain/kotlin/DistanceCameraPreview.kt
package com.europa.sightup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun DistanceCameraPreview(
    distance: State<String>,
    aspectRatio: Float     // The aspect ratio to ensure the image does not distort
): CameraAction  // This returns a platform-specific CameraAction object to start/stop the camera and calculate distance
