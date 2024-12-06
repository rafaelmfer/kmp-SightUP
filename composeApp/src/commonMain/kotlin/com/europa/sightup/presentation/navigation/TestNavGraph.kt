package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens.TestActive
import com.europa.sightup.presentation.navigation.TestScreens.TestIndividual
import com.europa.sightup.presentation.navigation.TestScreens.TestInit
import com.europa.sightup.presentation.navigation.TestScreens.TestResult
import com.europa.sightup.presentation.navigation.TestScreens.TestRoot
import com.europa.sightup.presentation.navigation.TestScreens.TestTutorial
import com.europa.sightup.presentation.screens.test.IndividualTestScreen
import com.europa.sightup.presentation.screens.test.active.ActiveTestScreen
import com.europa.sightup.presentation.screens.test.result.TestResultScreen
import com.europa.sightup.presentation.screens.test.root.TestScreenWithState
import com.europa.sightup.presentation.screens.test.tutorial.TutorialTestScreen
import com.europa.sightup.utils.getObjectFromArgs
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight

fun NavGraphBuilder.testNavGraph(navController: NavHostController) {
    navigation<TestInit>(startDestination = TestRoot) {

        composable<TestRoot>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            TestScreenWithState(navController = navController)
        }

        composable(
            route = "$TestIndividual/{${TestIndividual().testResponse}}",
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { navBackStackEntry ->
            val testResponse = navBackStackEntry.getObjectFromArgs<TestResponse>(TestIndividual().testResponse)
            testResponse?.let { IndividualTestScreen(navController = navController, test = testResponse) }
        }

        composable(
            route = "$TestTutorial/{${TestTutorial().testResponse}}",
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            val testResponse = it.getObjectFromArgs<TestResponse>(TestTutorial().testResponse)
            testResponse?.let { TutorialTestScreen(navController = navController, test = testResponse) }
        }

        composable(
            route = "${TestActive}/{${TestActive().testResponse}}/{testMode}/{eyeTested}",
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            val testResponse = it.getObjectFromArgs<TestResponse>(TestActive().testResponse)
            val testMode = it.arguments?.getString("testMode") ?: TestModeEnum.Touch.displayName
            val eyeTested = it.arguments?.getString("eyeTested") ?: ""
            testResponse?.let {
                ActiveTestScreen(
                    navController = navController,
                    test = testResponse,
                    testMode = testMode,
                    eyeTested = eyeTested
                )
            }
        }

        composable<TestResult>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            val testResult = it.toRoute<TestResult>()

            TestResultScreen(
                appTest = testResult.appTest,
                testId = testResult.testId,
                testTitle = testResult.testTitle,
                left = testResult.left,
                right = testResult.right,
                navController = navController
            )
        }
    }
}