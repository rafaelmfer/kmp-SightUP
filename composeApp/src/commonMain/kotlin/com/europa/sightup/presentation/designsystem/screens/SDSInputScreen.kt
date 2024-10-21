package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SDSInputScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SightUPTheme.spacing.spacing_base),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var inputText by remember { mutableStateOf("") }

        SDSInput(
            modifier = Modifier
                .padding(16.dp),
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = "Email",
            hint = "Email",
            isError = false,
            isEnabled = true,
            fullWidth = false
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
        SDSInput(
            modifier = Modifier,
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = "Email",
            hint = "Email",
            isError = false,
            isEnabled = true,
            fullWidth = true
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
        SDSInput(
            modifier = Modifier,
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = "Email",
            hint = "Email",
            isError = false,
            isEnabled = false,
            fullWidth = true
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
        SDSInput(
            modifier = Modifier,
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = "Email",
            hint = "Email",
            isError = true,
            isEnabled = true,
            fullWidth = true
        )
    }
}