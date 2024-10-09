package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.screens.onboarding.DisclaimerScreen

fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    navigation<OnboardingScreens.OnboardingInit>(
        startDestination = OnboardingScreens.Disclaimer,
    ) {
        composable<OnboardingScreens.Disclaimer> {
            DisclaimerScreen(navController = navController)
        }
    }
}