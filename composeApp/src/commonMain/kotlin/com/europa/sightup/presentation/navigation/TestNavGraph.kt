package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens.TestActive
import com.europa.sightup.presentation.navigation.TestScreens.TestIndividual
import com.europa.sightup.presentation.navigation.TestScreens.TestInit
import com.europa.sightup.presentation.navigation.TestScreens.TestRoot
import com.europa.sightup.presentation.navigation.TestScreens.TestTutorial
import com.europa.sightup.presentation.screens.test.ActiveTestScreen
import com.europa.sightup.presentation.screens.test.IndividualTestScreen
import com.europa.sightup.presentation.screens.test.TestScreenWithState
import com.europa.sightup.presentation.screens.test.TutorialTestScreen
import com.europa.sightup.utils.getObjectFromArgs

fun NavGraphBuilder.testNavGraph(navController: NavHostController) {
    navigation<TestInit>(startDestination = TestRoot) {

        composable<TestRoot> {
            TestScreenWithState(navController = navController)
        }

        composable(
            route = "$TestIndividual/{${TestIndividual().testResponse}}"
        ) { navBackStackEntry ->
            val testResponse = navBackStackEntry.getObjectFromArgs<TestResponse>(TestIndividual().testResponse)
            testResponse?.let { IndividualTestScreen(navController = navController, test = testResponse) }
        }

        composable(
            route = "$TestTutorial/{${TestTutorial().testResponse}}"
        ) {
            val testResponse = it.getObjectFromArgs<TestResponse>(TestTutorial().testResponse)
            testResponse?.let { TutorialTestScreen(navController = navController, test = testResponse) }
        }

        composable(
            route = "${TestActive}/{${TestActive().testResponse}}/{testMode}"
        ) { it ->
            val testResponse = it.getObjectFromArgs<TestResponse>(TestActive().testResponse)
            val testMode = it.arguments?.getString("testMode") ?: TestModeEnum.Touch.displayName
            testResponse?.let {
                ActiveTestScreen(
                    navController = navController,
                    test = testResponse,
                    testMode = testMode
                )
            }
        }
    }
}