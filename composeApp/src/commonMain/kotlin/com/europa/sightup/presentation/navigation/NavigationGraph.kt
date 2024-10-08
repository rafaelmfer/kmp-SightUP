package com.europa.sightup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.europa.sightup.presentation.screens.exercise.ExerciseScreen
import com.europa.sightup.presentation.screens.home.HomeScreen
import com.europa.sightup.presentation.screens.test.TestScreenWithState
import com.europa.sightup.utils.PostsWithState

/** This file links the navigation routes with the corresponding UI screens **/
@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Home // RootScreen.Home
    ){
        composable<Home> {
            HomeScreen(navController = navController)
        }

        composable<HomeExample> {
            // TODO: Replace this screen
            PostsWithState()
        }

        composable<Exercise> {
            ExerciseScreen(navController = navController)
        }

        composable<Test> {
            TestScreenWithState(navController = navController)
        }
    }
}

