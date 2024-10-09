package com.europa.sightup.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.europa.sightup.presentation.navigation.Account
import com.europa.sightup.presentation.navigation.BottomNavBar
import com.europa.sightup.presentation.navigation.Exercise
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.NavigationGraph
import com.europa.sightup.presentation.navigation.Record
import com.europa.sightup.presentation.navigation.Test
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentSelectedScreen by navController.currentScreenAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, currentSelectedScreen = currentSelectedScreen)
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // The screens will be drawn inside this
            NavigationGraph(navController = navController)
        }
    }
}

// Helper functions ---------------
@OptIn(ExperimentalSerializationApi::class)
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
            }
        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}