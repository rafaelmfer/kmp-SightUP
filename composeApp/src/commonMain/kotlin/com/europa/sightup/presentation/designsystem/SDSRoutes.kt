package com.europa.sightup.presentation.designsystem

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.europa.sightup.presentation.designsystem.screens.CountdownScreen
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
}

fun NavGraphBuilder.designSystemNavGraph(navController: NavHostController) {
    navigation<DesignSystemSamples.DesignSystemInit>(
        startDestination = DesignSystemSamples.Home,
    ) {
        composable<DesignSystemSamples.Home> {
            DesignSystemSamplesScreen(navController = navController)
        }
        composable<DesignSystemSamples.TextStyles> {
            TextStylesScreen()
        }
        composable<DesignSystemSamples.Countdown> {
            CountdownScreen()
        }
    }
}