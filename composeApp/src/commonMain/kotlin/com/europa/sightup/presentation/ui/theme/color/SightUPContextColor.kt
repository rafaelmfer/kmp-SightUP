package com.europa.sightup.presentation.ui.theme.color

object SightUPContextColor {
    val border_transparent = SightUPColorPalette.black.copy(alpha = SightUPOpacity.default.opacity_light)
    val background_overlay_dark = SightUPColorPalette.black.copy(alpha = SightUPOpacity.default.opacity_dark)

    val divider = SightUPColorPalette.black.copy(alpha = SightUPOpacity.default.opacity_light)

    val text_primary = SightUPColorPalette.neutral_900
    val text_secondary = SightUPColorPalette.neutral_0
    val text_tertiary = SightUPColorPalette.neutral_400
    val text_disabled = SightUPColorPalette.neutral_500

    val border_default = SightUPColorPalette.neutral_200
    val border_disabled = SightUPColorPalette.neutral_400
}
