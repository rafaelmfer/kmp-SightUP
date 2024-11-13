package com.europa.sightup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.tooling.preview.devices.WearDevices
import com.europa.sightup.components.SDSButtonWear
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun MainScreen(navController: NavController? = null) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        modifier = Modifier.fillMaxSize()
            .background(SightUPTheme.sightUPColors.primary_200)
    ) {
        SDSButtonWear(
            text = "Visual Acuity",
            onClick = {
                navController?.navigate("visual_acuity")
            },
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
        )
        SDSButtonWear(
            text = "Astigmatism",
            onClick = {
                navController?.navigate("astigmatism")
            },
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
        )
    }
}