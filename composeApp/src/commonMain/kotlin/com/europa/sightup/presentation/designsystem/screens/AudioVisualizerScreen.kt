package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.AudioVisualizer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AudioVisualizerScreen() {
    var randomize by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AudioVisualizer(
            barCount = 7,
            barMaxHeight = 120.dp,
            barMinHeight = 20.dp,
            barWidth = 8.dp,
            animationSpeed = 300,
            randomAnimation = randomize // Alternar entre random e wave animation
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { randomize = !randomize }) {
            Text(text = if (randomize) "Wave Animation" else "Random Animation")
        }
    }
}