package com.europa.sightup.presentation.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentSelectedScreen: RootScreen
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Home,
            onClick = { navController.navigateToRootScreen(RootScreen.Home) },
            alwaysShowLabel = true,
            label = { Text(text = "Home") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Exercise,
            onClick = { navController.navigateToRootScreen(RootScreen.Exercise) },
            alwaysShowLabel = true,
            label = { Text(text = "Exercise") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Test,
            onClick = { navController.navigateToRootScreen(RootScreen.Test) },
            alwaysShowLabel = true,
            label = { Text(text = "Test") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Record,
            onClick = { navController.navigateToRootScreen(RootScreen.Record) },
            alwaysShowLabel = true,
            label = { Text(text = "Record") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Account,
            onClick = { navController.navigateToRootScreen(RootScreen.Account) },
            alwaysShowLabel = true,
            label = { Text(text = "Account") },
            icon = {  }
        )
    }
}


private fun NavHostController.navigateToRootScreen(rootScreen: RootScreen) {
    navigate(rootScreen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}