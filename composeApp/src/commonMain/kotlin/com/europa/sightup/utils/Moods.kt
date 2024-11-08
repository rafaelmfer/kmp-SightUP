package com.europa.sightup.utils

enum class Moods(val value: String) {
    VERY_GOOD("very good"),
    GOOD("good"),
    MODERATE("moderate"),
    POOR("poor"),
    VERY_POOR("very poor");

    companion object {
        fun fromString(value: String): Moods {
            return entries.find { it.value.equals(value, ignoreCase = true) } ?: MODERATE
        }
    }
}