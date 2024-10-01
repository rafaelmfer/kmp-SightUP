package com.europa.sightup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showCamera by remember { mutableStateOf(false) }
        val distanceState = remember { mutableStateOf("Calculating...") }

        Column(
            Modifier.fillMaxWidth()
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showCamera = !showCamera }) {
                Text(if (showCamera) "Hide Camera" else "Show Camera")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showCamera) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Distance: ${distanceState.value}", style = MaterialTheme.typography.bodyMedium)
                    val camera = DistanceCameraPreview(distance = distanceState, aspectRatio = 3f / 4f)
                    distanceState.value = camera.distanceToCamera.value
                }
            }
        }
    }
}

