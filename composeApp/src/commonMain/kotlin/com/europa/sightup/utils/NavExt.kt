package com.europa.sightup.utils

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> NavController.navigate(route: String?, objectToSerialize: T) {
    val jsonString = Json.encodeToString(objectToSerialize).encodeForUrl()
    navigate("$route/$jsonString")
}

inline fun <reified T> NavBackStackEntry.getObjectFromArgs(key: String?): T? {
    val jsonString = arguments?.getString(key) ?: return null
    return Json.decodeFromString<T>(jsonString)
}