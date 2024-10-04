package com.europa.sightup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.screens.exercise.ExerciseScreen
import com.europa.sightup.presentation.screens.home.HomeScreen
import com.europa.sightup.utils.PostsWithState


/** This file will define the navigation graph (structure) for the app. **/
@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = RootScreen.Home.route
    ){
        homeGraph(navController = navController)
        exerciseGraph(navController = navController)
        //testGraph(navController = navController)
        //recordGraph(navController = navController)
        //accountGraph(navController = navController)
        //onboardingGraph(navController = navController)
    }
}

// Home Nested Graph
/** Inside each nested graph we will call the screens we want to show. **/
private fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        route = RootScreen.Home.route,
        startDestination = LeafScreen.Home.route
    ) {
        composable(route = LeafScreen.Home.route) {

            /** We pass the navController, to allow the screen to navigate to other screens. */
            HomeScreen(navController = navController)
        }

        composable(route = LeafScreen.HomeExample.route) {
            // TODO: Replace this screen
            PostsWithState()
        }
    }
}

// Exercise Nested Graph
private fun NavGraphBuilder.exerciseGraph(navController: NavController) {
    navigation(
        route = RootScreen.Exercise.route,
        startDestination = LeafScreen.Exercise.route
    ) {
        composable(route = LeafScreen.Exercise.route) {
            ExerciseScreen(navController = navController)
        }
    }
}

// Test Nested Graph

// Record Nested Graph

// Account Nested Graph

// Onboarding Nested Graph