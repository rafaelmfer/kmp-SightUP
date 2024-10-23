package com.europa.sightup.utils

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
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