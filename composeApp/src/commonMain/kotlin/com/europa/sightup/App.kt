package com.europa.sightup

// commonMain > App.kt
import androidx.compose.foundation.border
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
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform


import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showCamera by remember { mutableStateOf(false) }
        val distance = remember { mutableStateOf("Calculating...") }

        Column(
            Modifier.fillMaxWidth()
                    .padding(16.dp)
                .border(2.dp, Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showCamera = !showCamera }) {
                Text(if (showCamera) "Hide Camera" else "Show Camera")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showCamera) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                            .border(2.dp, Color.Green)
                ) {
                    Text("Distance: ${distance.value}", style = MaterialTheme.typography.bodyMedium)


                    DistanceCameraPreview(distance = distance, aspectRatio = 3f / 4f)
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun App() {
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
//}

