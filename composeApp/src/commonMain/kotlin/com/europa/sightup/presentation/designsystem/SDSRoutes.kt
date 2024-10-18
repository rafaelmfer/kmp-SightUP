package com.europa.sightup.presentation.designsystem

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.europa.sightup.presentation.designsystem.components.SdsButtonScreen
import com.europa.sightup.presentation.designsystem.screens.AudioVisualizerScreen
import com.europa.sightup.presentation.designsystem.screens.CountdownScreen
import com.europa.sightup.presentation.designsystem.screens.SDSBottomSheetScreen
import com.europa.sightup.presentation.designsystem.screens.SDSControlEScreen
import com.europa.sightup.presentation.designsystem.screens.TextStylesScreen
import kotlinx.serialization.Serializable

sealed interface DesignSystemSamples {

    @Serializable
    data object DesignSystemInit : DesignSystemSamples

    @Serializable
    data object Home : DesignSystemSamples

    @Serializable
    data object TextStyles : DesignSystemSamples

    @Serializable
    data object Countdown : DesignSystemSamples

    @Serializable
    data object AudioVisualizer : DesignSystemSamples

    @Serializable
    data object SDSButton : DesignSystemSamples

    @Serializable
    data object SDSBottomSheet : DesignSystemSamples

    @Serializable
    data object SDSControlE : DesignSystemSamples
}

fun NavGraphBuilder.designSystemNavGraph(navController: NavHostController) {
    navigation<DesignSystemSamples.DesignSystemInit>(
        startDestination = DesignSystemSamples.Home,
    ) {
        composable<DesignSystemSamples.Home> {
            SDSSamplesScreen(navController = navController)
        }
        composable<DesignSystemSamples.TextStyles> {
            TextStylesScreen()
        }
        composable<DesignSystemSamples.Countdown> {
            CountdownScreen()
        }
        composable<DesignSystemSamples.AudioVisualizer> {
            AudioVisualizerScreen()
        }
        composable<DesignSystemSamples.SDSButton> {
            SdsButtonScreen()
        }
        composable<DesignSystemSamples.SDSBottomSheet> {
            SDSBottomSheetScreen()
        }
        composable<DesignSystemSamples.SDSControlE>() {
            SDSControlEScreen()
        }
    }
}