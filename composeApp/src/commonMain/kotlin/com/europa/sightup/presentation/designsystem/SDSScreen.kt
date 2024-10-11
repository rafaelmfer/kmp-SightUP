package com.europa.sightup.presentation.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing

@Composable
fun DesignSystemSamplesScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SightUPTheme.spacing.spacing_base)
    ) {
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.Countdown)
            },
            modifier = Modifier
                .padding(SightUPTheme.spacing.spacing_base)
        ) {
            Text("Countdown")
        }
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.TextStyles)
            },
            modifier = Modifier
                .padding(SightUPTheme.spacing.spacing_base)
        ) {
            Text("Text Styles")
        }
    }
}