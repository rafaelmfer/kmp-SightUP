package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.navigation.AccountScreens.AccountInit
import com.europa.sightup.presentation.navigation.AccountScreens.AccountRoot
import com.europa.sightup.presentation.screens.account.AccountScreen

fun NavGraphBuilder.accountNavGraph(navController: NavHostController) {
    navigation<AccountInit>(
        startDestination = AccountRoot,
    ) {
        composable<AccountRoot> {
            AccountScreen(navController = navController)
        }
    }
}