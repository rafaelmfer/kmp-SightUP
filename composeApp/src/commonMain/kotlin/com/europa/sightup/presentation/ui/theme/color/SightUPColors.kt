package com.europa.sightup.presentation.ui.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SightUPColors(
    // Unused colors, colors created just to fulfill the requirements of the Material Theme
    val border_transparent: Color = SightUPContextColor.border_transparent,
    val background_overlay_dark: Color = SightUPContextColor.background_overlay_dark,

    // SightUP Colors
    val black: Color = SightUPColorPalette.black,
    val white: Color = SightUPColorPalette.white,

    val primary_100: Color = SightUPColorPalette.primary_100,
    val primary_200: Color = SightUPColorPalette.primary_200,
    val primary_300: Color = SightUPColorPalette.primary_300,
    val primary_400: Color = SightUPColorPalette.primary_400,
    val primary_500: Color = SightUPColorPalette.primary_500,
    val primary_600: Color = SightUPColorPalette.primary_600,
    val primary_700: Color = SightUPColorPalette.primary_700,
    val primary_800: Color = SightUPColorPalette.primary_800,

    val neutral_0: Color = SightUPColorPalette.neutral_0,
    val neutral_100: Color = SightUPColorPalette.neutral_100,
    val neutral_200: Color = SightUPColorPalette.neutral_200,
    val neutral_300: Color = SightUPColorPalette.neutral_300,
    val neutral_400: Color = SightUPColorPalette.neutral_400,
    val neutral_500: Color = SightUPColorPalette.neutral_500,
    val neutral_600: Color = SightUPColorPalette.neutral_600,
    val neutral_700: Color = SightUPColorPalette.neutral_700,
    val neutral_800: Color = SightUPColorPalette.neutral_800,
    val neutral_900: Color = SightUPColorPalette.neutral_900,

    val success_100: Color = SightUPColorPalette.success_100,
    val success_200: Color = SightUPColorPalette.success_200,
    val success_300: Color = SightUPColorPalette.success_300,

    val error_100: Color = SightUPColorPalette.error_100,
    val error_200: Color = SightUPColorPalette.error_200,
    val error_300: Color = SightUPColorPalette.error_300,

    val warning_100: Color = SightUPColorPalette.warning_100,
    val warning_200: Color = SightUPColorPalette.warning_200,
    val warning_300: Color = SightUPColorPalette.warning_300,

    val info_100: Color = SightUPColorPalette.info_100,
    val info_200: Color = SightUPColorPalette.info_200,
    val info_300: Color = SightUPColorPalette.info_300,

    val divider: Color = SightUPContextColor.divider,

    val text_primary: Color = SightUPContextColor.text_primary,
    val text_secondary: Color = SightUPContextColor.text_secondary,
    val text_tertiary: Color = SightUPContextColor.text_tertiary,
    val text_disabled: Color = SightUPContextColor.text_disabled,

    val border_default: Color = SightUPContextColor.border_default,
    val border_disabled: Color = SightUPContextColor.border_disabled
)

internal val LocalSightUPColors = staticCompositionLocalOf { SightUPColors() }