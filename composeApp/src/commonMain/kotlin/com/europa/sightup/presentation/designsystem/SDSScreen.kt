package com.europa.sightup.presentation.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing

@Composable
fun SDSSamplesScreen(navController: NavHostController) {
    val buttonModifier = Modifier
        .padding(SightUPTheme.spacing.spacing_base)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SightUPTheme.spacing.spacing_base)
    ) {
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.AudioVisualizer)
            },
            modifier = buttonModifier
        ) {
            Text("Audio Visualizer")
        }
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.SDSBottomSheet)
            },
            modifier = buttonModifier
        ) {
            Text("BottomSheet")
        }
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.Countdown)
            },
            modifier = buttonModifier
        ) {
            Text("Countdown")
        }
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.TextStyles)
            },
            modifier = buttonModifier
        ) {
            Text("Text Styles")
        }
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.SDSButton)
            },
            modifier = buttonModifier,
        ) {
            Text("SDS Buttons")
        }
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.SDSControlE)
            },
            modifier = buttonModifier,
        ) {
            Text("SDS Control E")
        }
    }
}