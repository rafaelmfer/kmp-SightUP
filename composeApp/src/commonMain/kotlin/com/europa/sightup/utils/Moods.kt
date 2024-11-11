package com.europa.sightup.utils

import org.jetbrains.compose.resources.DrawableResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.excelent
import sightupkmpapp.composeapp.generated.resources.good
import sightupkmpapp.composeapp.generated.resources.moderate
import sightupkmpapp.composeapp.generated.resources.poor
import sightupkmpapp.composeapp.generated.resources.very_poor

enum class Moods(val value: String, val icon: DrawableResource) {
    VERY_GOOD(value = "Very Good", icon = Res.drawable.excelent),
    GOOD(value = "Good", icon = Res.drawable.good),
    MODERATE(value = "Moderate", icon = Res.drawable.moderate),
    POOR(value = "Poor", icon = Res.drawable.poor),
    VERY_POOR(value = "Very Poor", icon = Res.drawable.very_poor);

    companion object {
        fun fromString(value: String): Moods {
            return entries.find { it.value.equals(value, ignoreCase = true) } ?: MODERATE
        }
    }
}