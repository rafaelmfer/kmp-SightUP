package com.europa.sightup.utils

enum class Moods(val value: String) {
    VERY_GOOD("very good"),
    GOOD("good"),
    MODERATE("moderate"),
    POOR("poor"),
    VERY_POOR("very poor");

    companion object {
        fun fromValue(value: String): Moods {
            return when {
                value.equals("very good", ignoreCase = true) -> VERY_GOOD
                value.equals("good", ignoreCase = true) -> GOOD
                value.equals("moderate", ignoreCase = true) -> MODERATE
                value.equals("poor", ignoreCase = true) -> POOR
                value.equals("very poor", ignoreCase = true) -> VERY_POOR
                else -> MODERATE
            }
        }
    }
}