package com.europa.sightup.utils

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> NavController.navigate(route: String?, objectToSerialize: T) {
    val jsonString = Json.encodeToString(objectToSerialize).encodeForUrl()
    navigate("$route/$jsonString")
}

inline fun <reified T> NavController.navigate(route: String?, objectToSerialize: T, objectToSerialize2: String) {
    val jsonString = Json.encodeToString(objectToSerialize).encodeForUrl()
    val jsonString2 = objectToSerialize2.encodeForUrl()
    navigate("$route/$jsonString/$jsonString2")
}

inline fun <reified T> NavController.navigate(
    route: String?,
    objectToSerialize: T,
    objectToSerialize2: String,
    objectToSerialize3: String,
) {
    val jsonString = Json.encodeToString(objectToSerialize).encodeForUrl()
    val jsonString2 = objectToSerialize2.encodeForUrl()
    val jsonString3 = objectToSerialize3.encodeForUrl()
    navigate("$route/$jsonString/$jsonString2/$jsonString3")
}

inline fun <reified T> NavBackStackEntry.getObjectFromArgs(key: String?): T? {
    val jsonString = arguments?.getString(key) ?: return null
    return Json.decodeFromString<T>(jsonString)
}

fun NavHostController.navigateToRootScreen(rootScreen: Any) {
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

fun NavController?.goBackToExerciseHome() {
    this?.popBackStack<ExerciseRoot>(inclusive = false)
}



// Transitions
fun slideOutToRight() = slideOutHorizontally(
    targetOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(durationMillis = 500)
)

fun slideOutToLeft() = slideOutHorizontally(
    targetOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(durationMillis = 500)
)

fun slideInFromLeft() = slideInHorizontally(
    initialOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(durationMillis = 500)
)

fun slideInFromRight() = slideInHorizontally(
    initialOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(durationMillis = 500)
)