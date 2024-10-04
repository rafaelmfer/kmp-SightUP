package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.presentation.navigation.LeafScreen

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Home Screen",
                style = MaterialTheme.typography.headlineMedium
            )
            Button(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .align(Alignment.Center),
                onClick = {
                    navController.navigate(LeafScreen.HomeExample.route)  // Navigate to the example screen
                }
            ) {
                Text("Go to Posts with States")
            }
        }
    }
}
