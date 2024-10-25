package com.europa.sightup.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.europa.sightup.presentation.navigation.AccountScreens.AccountRoot
import com.europa.sightup.presentation.navigation.BottomNavBar
import com.europa.sightup.presentation.navigation.BottomNavItem
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.NavigationGraph
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsRoot
import com.europa.sightup.presentation.navigation.TestScreens.TestRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.account
import sightupkmpapp.composeapp.generated.resources.exercise
import sightupkmpapp.composeapp.generated.resources.home
import sightupkmpapp.composeapp.generated.resources.prescriptions
import sightupkmpapp.composeapp.generated.resources.vision

@Preview
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val bottomNavItems = listOf(
        BottomNavItem(screen = Home, label = "Home", icon = Res.drawable.home),
        BottomNavItem(screen = ExerciseRoot, label = "Exercises", icon = Res.drawable.exercise),
        BottomNavItem(screen = TestRoot, label = "Vision Tests", icon = Res.drawable.vision),
        BottomNavItem(screen = PrescriptionsRoot, label = "Prescriptions", icon = Res.drawable.prescriptions),
        BottomNavItem(screen = AccountRoot, label = "Account", icon = Res.drawable.account)
    )

    val firstLevelScreens = listOf(
        Home,
        ExerciseRoot,
        TestRoot,
        PrescriptionsRoot,
        AccountRoot
    )

    val showBottomBar = firstLevelScreens.any { screen ->
        currentBackStackEntry?.destination?.route == screen::class.qualifiedName
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                currentBackStackEntry?.destination?.route?.let {
                    BottomNavBar(
                        navController = navController,
                        currentSelectedScreen = it,
                        listBottomScreens = bottomNavItems
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavigationGraph(navController = navController)
        }
    }
}