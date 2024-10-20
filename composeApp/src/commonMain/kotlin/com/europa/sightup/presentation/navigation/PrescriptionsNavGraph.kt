package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsInit
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsRoot
import com.europa.sightup.presentation.screens.prescription.PrescriptionScreen

fun NavGraphBuilder.prescriptionsNavGraph(navController: NavHostController) {
    navigation<PrescriptionsInit>(
        startDestination = PrescriptionsRoot,
    ) {
        composable<PrescriptionsRoot> {
            PrescriptionScreen(navController = navController)
        }
    }
}