package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.designsystem.components.SDSControlE
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SDSControlEScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SightUPTheme.spacing.spacing_base),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SDSControlE(
            upButtonOnClickResult = {
                showToast(
                    "Up button clicked",
                    bottomPadding = 40
                )
            },
            leftButtonOnClickResult = {
                showToast(
                    "Left button clicked",
                    bottomPadding = 40
                )
            },
            rightButtonOnClickResult = {
                showToast(
                    "Right button clicked",
                    bottomPadding = 40
                )
            },
            downButtonOnClickResult = {
                showToast(
                    "Down button clicked",
                    bottomPadding = 40
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}