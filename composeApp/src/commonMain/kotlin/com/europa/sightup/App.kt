package com.europa.sightup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.europa.sightup.presentation.navigation.BottomNavBar
import com.europa.sightup.presentation.navigation.NavigationGraph
import com.europa.sightup.presentation.navigation.RootScreen

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val currentSelectedScreen by navController.currentScreenAsState()

    Scaffold(
        //topBar = {},
        bottomBar = {
            BottomNavBar(navController = navController, currentSelectedScreen = currentSelectedScreen)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // The screens will be drawn inside this
            NavigationGraph(navController = navController)
        }
    }
}

// Helper functions

@Stable
@Composable
private fun NavHostController.currentScreenAsState(): State<RootScreen> {
    // TODO: Fix the listener to mark the current tab selected
    val selectedItem = remember { mutableStateOf<RootScreen>(RootScreen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                RootScreen.Home.route -> selectedItem.value = RootScreen.Home
                RootScreen.Exercise.route -> selectedItem.value = RootScreen.Exercise
                RootScreen.Test.route -> selectedItem.value = RootScreen.Test
                RootScreen.Record.route -> selectedItem.value = RootScreen.Record
                RootScreen.Account.route -> selectedItem.value = RootScreen.Account
                RootScreen.Onboarding.route -> selectedItem.value = RootScreen.Onboarding
            }
        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}


