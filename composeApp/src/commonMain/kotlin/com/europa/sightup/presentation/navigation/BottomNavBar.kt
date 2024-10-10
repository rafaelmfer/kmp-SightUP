package com.europa.sightup.presentation.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentSelectedScreen: Any
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentSelectedScreen == Home,
            onClick = { navController.navigateToRootScreen(Home) },
            alwaysShowLabel = true,
            label = { Text(text = "Home") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == Exercise,
            onClick = { navController.navigateToRootScreen(Exercise) },
            alwaysShowLabel = true,
            label = { Text(text = "Exercise") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == TestScreens.TestRoot,
            onClick = { navController.navigateToRootScreen(TestScreens.TestRoot) },
            alwaysShowLabel = true,
            label = { Text(text = "Test") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == Record,
            onClick = { navController.navigateToRootScreen(Record) },
            alwaysShowLabel = true,
            label = { Text(text = "Record") },
            icon = {  }
        )
        NavigationBarItem(
            selected = currentSelectedScreen == Account,
            onClick = { navController.navigateToRootScreen(Account) },
            alwaysShowLabel = true,
            label = { Text(text = "Account") },
            icon = {  }
        )
    }
}


private fun NavHostController.navigateToRootScreen(rootScreen: Any) {
    navigate(rootScreen) {
        graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}