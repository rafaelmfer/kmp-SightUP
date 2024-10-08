package com.europa.sightup.presentation.components

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

sealed interface DesignSystemSamples {

    @Serializable
    data object Home

    @Serializable
    data object TextStyles
}

fun NavGraphBuilder.designSystemNavGraph(navController: NavHostController) {
    navigation<DesignSystemSamples>(
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