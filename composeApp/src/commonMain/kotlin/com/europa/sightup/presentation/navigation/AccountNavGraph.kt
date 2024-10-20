package com.europa.sightup.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.europa.sightup.presentation.navigation.AccountScreens.AccountInit
import com.europa.sightup.presentation.navigation.AccountScreens.AccountRoot

fun NavGraphBuilder.accountNavGraph(navController: NavHostController) {
    navigation<AccountInit>(
        startDestination = AccountRoot,
    ) {
        composable<AccountRoot> {
            AccountScreen(navController = navController)
        }
    }
}

@Composable
fun AccountScreen(navController: NavHostController? = null) {
    Scaffold {
        Text(
            modifier = Modifier
                .fillMaxSize(),
            text = "Account Screen"
        )
    }
}