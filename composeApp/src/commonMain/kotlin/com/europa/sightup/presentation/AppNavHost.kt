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
import com.europa.sightup.presentation.navigation.AccountScreens.AccountRoot
import com.europa.sightup.presentation.navigation.BottomNavBar
import com.europa.sightup.presentation.navigation.BottomNavItem
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.NavigationGraph
import com.europa.sightup.presentation.navigation.PrescriptionsScreens.PrescriptionsRoot
import com.europa.sightup.presentation.navigation.TestScreens.TestRoot
import kotlinx.serialization.ExperimentalSerializationApi
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
    val currentSelectedScreen by navController.currentScreenAsState()

    val bottomNavItems = listOf(
        BottomNavItem(screen = Home, label = "Home", icon = Res.drawable.home),
        BottomNavItem(screen = ExerciseRoot, label = "Exercise", icon = Res.drawable.exercise),
        BottomNavItem(screen = TestRoot, label = "Vision Test", icon = Res.drawable.vision),
        BottomNavItem(screen = PrescriptionsRoot, label = "Prescriptions", icon = Res.drawable.prescriptions),
        BottomNavItem(screen = AccountRoot, label = "Account", icon = Res.drawable.account)
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentSelectedScreen = currentSelectedScreen,
                listBottomScreens = bottomNavItems
            )
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
                ExerciseRoot.serializer().descriptor.serialName -> selectedItem.value = ExerciseRoot
                TestRoot.serializer().descriptor.serialName -> selectedItem.value = TestRoot
                PrescriptionsRoot.serializer().descriptor.serialName -> selectedItem.value = PrescriptionsRoot
                AccountRoot.serializer().descriptor.serialName -> selectedItem.value = AccountRoot
            }
        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}