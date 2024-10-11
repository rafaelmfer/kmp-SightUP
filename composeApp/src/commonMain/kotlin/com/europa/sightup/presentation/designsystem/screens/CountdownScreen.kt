package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.designsystem.components.Countdown
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import multiplatform.network.cmptoast.showToast

@Composable
fun CountdownScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SightUPTheme.spacing.spacing_base)
    ) {
        Countdown(
            modifier = Modifier.align(Alignment.Center),
            startsIn = 5,
            onCountdownFinished = {
                showToast(
                    message = "Countdown finished",
                    bottomPadding = 100,
                )
            },
        )
    }
}