package com.europa.sightup.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.screens.onboarding.DisclaimerScreen
import com.europa.sightup.presentation.screens.onboarding.TutorialScreen
import com.europa.sightup.presentation.screens.onboarding.WelcomeScreen
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight

fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    navigation<OnboardingScreens.OnboardingInit>(
        startDestination = OnboardingScreens.Disclaimer,
    ) {
        composable<OnboardingScreens.Disclaimer>(
            enterTransition = { fadeIn() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            DisclaimerScreen(navController = navController)
        }
        composable<OnboardingScreens.Tutorial>(
            enterTransition = { slideInFromRight() },
            exitTransition = { fadeOut() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            TutorialScreen(navController = navController)
        }

        composable<OnboardingScreens.WelcomeScreen>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            WelcomeScreen(
                navController = navController,
            )
        }
    }
}