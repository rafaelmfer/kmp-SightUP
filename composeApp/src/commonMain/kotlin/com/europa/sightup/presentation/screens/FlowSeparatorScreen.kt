package com.europa.sightup.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.europa.sightup.SightUPApp
import com.europa.sightup.getPlatform
import com.europa.sightup.presentation.navigation.OnboardingScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import kotlinx.serialization.Serializable

@Serializable
data object FlowSeparator

// TODO: DELETE THIS SCREEN IN THE FUTURE
@Composable
fun FlowSeparatorScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navController?.navigate(OnboardingScreens.OnboardingInit)
            },
            colors = ButtonColors(
                containerColor = SightUPTheme.sightUPColors.success_300,
                contentColor = SightUPTheme.sightUPColors.white,
                disabledContainerColor = SightUPTheme.sightUPColors.neutral_300,
                disabledContentColor = SightUPTheme.sightUPColors.black
            ),
        ) {
            Text("Before Login")
        }
        Spacer(modifier = Modifier.padding(SightUPTheme.spacing.spacing_base))
        Button(onClick = {
            navController?.navigate(SightUPApp)
        }) {
            Text("After Login")
            println(getPlatform().isDebug)
        }
    }
}