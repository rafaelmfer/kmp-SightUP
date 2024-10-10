package com.europa.sightup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.europa.sightup.presentation.AppNavHost
import com.europa.sightup.presentation.components.DesignSystemSamples
import com.europa.sightup.presentation.components.designSystemNavGraph
import com.europa.sightup.presentation.navigation.OnboardingScreens
import com.europa.sightup.presentation.navigation.onboardingNavGraph
import com.europa.sightup.presentation.screens.FlowSeparator
import com.europa.sightup.presentation.screens.FlowSeparatorScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import kotlinx.serialization.Serializable

@Serializable
data object SightUPApp

@Serializable
data object AppInit

@Composable
fun Init() {
    val navController = rememberNavController()
    InitNavGraph(navController = navController)
}

@Composable
fun InitNavGraph(
    navController: NavHostController
) {
    SightUPTheme {
        NavHost(
            navController = navController,
            startDestination = if (getPlatform().isDebug) AppInit else OnboardingScreens.OnboardingInit
        ) {
            composable<AppInit> {
                AppEntryPoint(navController = navController)
            }
            composable<FlowSeparator> {
                FlowSeparatorScreen(navController = navController)
            }
            onboardingNavGraph(navController)

            composable<SightUPApp> {
                AppNavHost()
            }
           designSystemNavGraph(navController)
        }
    }
}

@Composable
fun AppEntryPoint(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(FlowSeparator)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Start the App")
        }

        Button(
            onClick = {
                navController.navigate(DesignSystemSamples.Home)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("See Design System")
        }
    }
}

