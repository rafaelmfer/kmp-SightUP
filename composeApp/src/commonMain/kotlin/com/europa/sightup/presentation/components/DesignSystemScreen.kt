package com.europa.sightup.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DesignSystemSamplesScreen(navController: NavHostController) {
    Column {
        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.TextStyles)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Text Styles")
        }
    }
}