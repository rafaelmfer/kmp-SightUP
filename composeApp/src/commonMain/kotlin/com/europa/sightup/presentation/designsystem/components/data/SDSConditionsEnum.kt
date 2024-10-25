package com.europa.sightup.presentation.designsystem.components.data

enum class SDSConditionsEnum {
    EYE_STRAIN, DRY_EYES, RED_EYES, IRRITATED_EYES, WATERY_EYES, ITCHY_EYES, NONE;

    companion object {
        fun fromString(value: String): SDSConditionsEnum {
            val formattedValue = value.replace(" ", "_").uppercase()
            return entries.firstOrNull {
                it.name.equals(formattedValue, ignoreCase = true)
            } ?: NONE
        }
    }
}