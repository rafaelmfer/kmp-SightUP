package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsInit
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsRoot
import com.europa.sightup.presentation.screens.prescription.PrescriptionScreen
import com.europa.sightup.presentation.screens.prescription.history.PrescriptionHistory
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight

fun NavGraphBuilder.prescriptionsNavGraph(navController: NavHostController) {
    navigation<PrescriptionsInit>(
        startDestination = PrescriptionsRoot,
    ) {
        composable<PrescriptionsRoot>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            PrescriptionScreen(navController = navController)
        }

        composable<PrescriptionsScreens.PrescriptionsHistory>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            PrescriptionHistory(navController = navController)
        }
    }
}