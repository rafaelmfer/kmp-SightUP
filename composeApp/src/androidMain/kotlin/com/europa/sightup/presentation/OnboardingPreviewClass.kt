package com.europa.sightup.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.europa.sightup.presentation.screens.onboarding.DisclaimerScreen
import com.europa.sightup.presentation.screens.onboarding.TutorialScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Disclaimer() {
    SightUPTheme {
        DisclaimerScreen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Tutorial() {
    SightUPTheme {
        TutorialScreen()
    }
}