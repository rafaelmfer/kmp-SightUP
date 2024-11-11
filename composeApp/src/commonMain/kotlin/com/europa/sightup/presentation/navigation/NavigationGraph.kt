package com.europa.sightup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.europa.sightup.platformspecific.getPlatform
import com.europa.sightup.presentation.designsystem.designSystemNavGraph
import com.europa.sightup.presentation.screens.home.HomeScreen
import com.europa.sightup.utils.PostsWithState

@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = OnboardingScreens.OnboardingInit
    ) {
        onboardingNavGraph(navController = navController)

        composable<Home> {
            HomeScreen(navController = navController)
        }
        exerciseNavGraph(navController = navController)
        testNavGraph(navController = navController)
        prescriptionsNavGraph(navController = navController)
        accountNavGraph(navController = navController)

        if (getPlatform().isDebug) {
            composable<HomeExample> {
                // TODO: Replace this screen
                PostsWithState()
            }
            designSystemNavGraph(navController)
        }
    }
}