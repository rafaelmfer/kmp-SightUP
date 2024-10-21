package com.europa.sightup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.europa.sightup.presentation.screens.exercise.ExerciseScreen
import com.europa.sightup.presentation.screens.home.HomeScreen
import com.europa.sightup.presentation.screens.test.TestScreenWithState
import com.europa.sightup.utils.PostsWithState
import androidx.navigation.compose.navigation
import com.europa.sightup.presentation.screens.test.IndividualTestScreen
import androidx.navigation.toRoute
import com.europa.sightup.presentation.screens.prescription.PrescriptionScreen


/** This file links the navigation routes with the corresponding UI screens **/
@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<HomeExample> {
            // TODO: Replace this screen
            PostsWithState()
        }
        composable<Home> {
            HomeScreen(navController = navController)
        }
        exerciseNavGraph(navController = navController)
        testNavGraph(navController = navController)
        prescriptionsNavGraph(navController = navController)
        accountNavGraph(navController = navController)

        composable<ExerciseScreens.Prescription> {
            PrescriptionScreen(navController = navController)
        }

        testNavGraph(navController)
    }
}