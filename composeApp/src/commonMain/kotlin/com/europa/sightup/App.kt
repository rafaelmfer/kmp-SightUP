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
import com.europa.sightup.presentation.navigation.Account
import com.europa.sightup.presentation.navigation.BottomNavBar
import com.europa.sightup.presentation.navigation.Exercise
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.NavigationGraph
import com.europa.sightup.presentation.navigation.Onboarding
import com.europa.sightup.presentation.navigation.Record
import com.europa.sightup.presentation.navigation.Test


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

// Helper functions ---------------

@Stable
@Composable
private fun NavHostController.currentScreenAsState(): State<Any> {
    val selectedItem = remember { mutableStateOf<Any>(Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                Home.serializer().descriptor.serialName -> selectedItem.value = Home
                Exercise.serializer().descriptor.serialName -> selectedItem.value = Exercise
                Test.serializer().descriptor.serialName -> selectedItem.value = Test
                Record.serializer().descriptor.serialName -> selectedItem.value = Record
                Account.serializer().descriptor.serialName -> selectedItem.value = Account
                Onboarding.serializer().descriptor.serialName -> selectedItem.value = Onboarding
            }
        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}


