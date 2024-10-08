package com.europa.sightup.presentation.ui.theme.color

import androidx.compose.runtime.Immutable

@Immutable
data class SightUPOpacity(
    val opacity_lightest: Float,
    val opacity_light: Float,
    val opacity_medium: Float,
    val opacity_dark: Float,
    val opacity_darkest: Float
) {
    companion object {
        val default = SightUPOpacity(
            opacity_lightest = 0.04f,
            opacity_light = 0.08f,
            opacity_medium = 0.16f,
            opacity_dark = 0.32f,
            opacity_darkest = 0.48f,
        )
    }
}

val SightUPOpacity.opacity: SightUPOpacity
    get() = SightUPOpacity.default