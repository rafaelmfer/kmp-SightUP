package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.europa.sightup.presentation.screens.test.ExecutionTestScreen
import com.europa.sightup.presentation.screens.test.IndividualTestScreen
import com.europa.sightup.presentation.screens.test.TestScreenWithState

fun NavGraphBuilder.testNavGraph(navController: NavHostController) {
    navigation<TestScreens.TestInit>( startDestination = TestScreens.TestRoot){

        composable<TestScreens.TestRoot> {
            TestScreenWithState(navController = navController)
        }

        composable<TestScreens.TestIndividual> {
            val arguments = it.toRoute<TestScreens.TestIndividual>()
            IndividualTestScreen(navController = navController, taskId = arguments.id)
        }

        composable<TestScreens.TestExecution> {
            val arguments = it.toRoute<TestScreens.TestExecution>()
            ExecutionTestScreen(navController = navController, taskId = arguments.id)
        }
    }
}