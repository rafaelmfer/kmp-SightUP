package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsInit
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsRoot
import com.europa.sightup.presentation.screens.prescription.PrescriptionScreen
import com.europa.sightup.presentation.screens.prescription.history.PrescriptionHistory

fun NavGraphBuilder.prescriptionsNavGraph(navController: NavHostController) {
    navigation<PrescriptionsInit>(
        startDestination = PrescriptionsRoot,
    ) {
        composable<PrescriptionsRoot> {
            PrescriptionScreen(navController = navController)
        }

        composable<PrescriptionsScreens.PrescriptionsHistory> {
            PrescriptionHistory(navController = navController)
        }
    }
}