package com.europa.sightup.presentation.components

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

sealed interface DesignSystemSamples {

    @Serializable
    data object DesignSystemInit : DesignSystemSamples

    @Serializable
    data object Home : DesignSystemSamples

    @Serializable
    data object TextStyles : DesignSystemSamples
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
    }
}