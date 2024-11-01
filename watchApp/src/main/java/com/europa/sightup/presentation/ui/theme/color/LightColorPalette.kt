package com.europa.sightup.presentation.ui.theme.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.wear.compose.material3.ColorScheme

val LightColorPalette = ColorScheme(
    primary = SightUPColorPalette.primary_600,
    onPrimary = SightUPColorPalette.white,
    primaryContainer = SightUPColorPalette.primary_200,
    onPrimaryContainer = SightUPColorPalette.primary_700,
    onSecondary = SightUPColorPalette.white,
    tertiary = SightUPColorPalette.neutral_600,
    onTertiary = SightUPColorPalette.white,
    tertiaryContainer = SightUPColorPalette.neutral_0,
    onTertiaryContainer = SightUPColorPalette.black,
    background = SightUPColorPalette.white,
    onBackground = SightUPColorPalette.black,
    onSurface = SightUPColorPalette.black,
    onSurfaceVariant = SightUPColorPalette.neutral_600,
    error = SightUPColorPalette.error_200,
    onError = SightUPColorPalette.white,
    errorContainer = SightUPColorPalette.error_100,
    onErrorContainer = SightUPColorPalette.error_300,
    outline = SightUPColorPalette.neutral_200,
    outlineVariant = SightUPContextColor.border_transparent,
)

internal val LocalColors = staticCompositionLocalOf { LightColorPalette }
