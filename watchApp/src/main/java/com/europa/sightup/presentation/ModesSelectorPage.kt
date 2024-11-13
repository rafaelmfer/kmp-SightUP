package com.europa.sightup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import com.europa.sightup.R
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Composable
fun ModesSelectorPage() {
    val scrollState = rememberScrollState()
    var selectedIcon by remember { mutableStateOf("smartwatch") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(SightUPTheme.sightUPColors.primary_200),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    selectedIcon = "touch"
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.touch_mode),
                    contentDescription = "Touch Mode",
                    tint = if (selectedIcon == "touch") SightUPTheme.sightUPColors.primary_600 else SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(
                onClick = {
                    selectedIcon = "voice"
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.voice_mode),
                    contentDescription = "Voice Mode",
                    tint = if (selectedIcon == "voice") SightUPTheme.sightUPColors.primary_600 else SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(
                onClick = {
                    selectedIcon = "smartwatch"
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.smartwatch_mode),
                    contentDescription = "Smartwatch Mode",
                    tint = if (selectedIcon == "smartwatch") SightUPTheme.sightUPColors.primary_600 else SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}