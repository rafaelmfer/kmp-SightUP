package com.europa.sightup.presentation.ui.theme.color

import androidx.compose.runtime.Immutable

@Immutable
data class SightUPOpacity(
    /**
     * @property opacity_lightest is a Float property that represents the lightest opacity value.
     * The value is set to 0.04f.
     */
    val opacity_lightest: Float,
    /**
     * @property opacity_light is a Float property that represents the lighter opacity value.
     * The value is set to 0.08f.
     */
    val opacity_light: Float,
    /**
     * @property opacity_medium is a Float property that represents the medium opacity value.
     * The value is set to 0.16f.
     */
    val opacity_medium: Float,
    /**
     * @property opacity_dark is a Float property that represents the dark opacity value.
     * The value is set to 0.32f.
     */
    val opacity_dark: Float,
    /**
     * @property opacity_darkest is a Float property that represents the darkest opacity value.
     * The value is set to 0.48f.
     */
    val opacity_darkest: Float
) {
    companion object {
        val default = SightUPOpacity(
            opacity_lightest = 0.04F,
            opacity_light = 0.08F,
            opacity_medium = 0.16F,
            opacity_dark = 0.32F,
            opacity_darkest = 0.48F,
        )
    }
}

val SightUPOpacity.opacity: SightUPOpacity
    get() = SightUPOpacity.default