package com.europa.sightup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
<<<<<<< Updated upstream
import com.europa.sightup.data.remote.response.PostResponse
import com.europa.sightup.utils.PostsScreen
import com.europa.sightup.utils.PostsWithState
import com.europa.sightup.utils.UIState
import org.koin.compose.KoinApplication
=======
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.europa.sightup.ui.EyeExercise.EyeExerciseScreen
import com.europa.sightup.ui.Overview.OverviewScreen
import com.europa.sightup.ui.components.TabRow
>>>>>>> Stashed changes

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
<<<<<<< Updated upstream
//            App()
            PostsWithState()
=======
            Navigation()
>>>>>>> Stashed changes
        }
    }
}

@Preview(showSystemUi = true)
@Composable
<<<<<<< Updated upstream
fun AppAndroidPreview() {
    KoinApplication(application = {}) {
        App()
    }
}

@Preview(showSystemUi = true)
@Composable
fun PostsPreview() {
    KoinApplication(
        application = {
            // your preview config here
        }
    ) {
        val posts = listOf(
            PostResponse(userId = 1, id = 1, title = "Title", body = "Body"),
            PostResponse(userId = 2, id = 2, title = "Title 2", body = "Body 2"),
            PostResponse(userId = 3, id = 3, title = "Title 3", body = "Body 3"),
            PostResponse(userId = 4, id = 4, title = "Title 4", body = "Body 4")
        )
        PostsScreen(UIState.Success(posts))
    }
}

=======
fun Navigation() {
    MaterialTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = TabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        Scaffold(
            topBar = {
                TabRow(
                    allScreens = TabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigate(newScreen.route) { launchSingleTop = true }
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        composable(route = Overview.route) {
            OverviewScreen(String.toString())
        }
        composable(route = EyeExercise.route){
            EyeExerciseScreen(String.toString())
        }
}}



>>>>>>> Stashed changes
