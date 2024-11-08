package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.screens.onboarding.DisclaimerScreen
import com.europa.sightup.presentation.screens.onboarding.TutorialScreen
import com.europa.sightup.presentation.screens.onboarding.WelcomeScreen

fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    navigation<OnboardingScreens.OnboardingInit>(
        startDestination = OnboardingScreens.Disclaimer,
    ) {
        composable<OnboardingScreens.Disclaimer> {
            DisclaimerScreen(navController = navController)
        }
        composable<OnboardingScreens.Tutorial> {
            TutorialScreen(navController = navController)
        }

        composable<OnboardingScreens.WelcomeScreen> {
            WelcomeScreen(navController = navController)
        }
    }
}