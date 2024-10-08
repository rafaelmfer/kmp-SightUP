package com.europa.sightup.presentation.ui.theme.layout

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme

/**
 * @Immutable data class Spacing is a Kotlin data class with the @Immutable annotation.
 * This annotation tells the Kotlin compiler that the class is immutable, which means that its
 * properties are read-only and cannot be changed after instantiation.
 * This allows the compiler to optimize the code and reduce memory usage.
 *
 * This class should only be used in Padding and Margin
 */
@Immutable
data class SightUPSpacing(
    val spacing_none: Dp = 0.dp,
    val spacing_2xs: Dp = 4.dp,
    val spacing_xs: Dp = 8.dp,
    val spacing_sm: Dp = 12.dp,
    val spacing_base: Dp = 16.dp,
    val spacing_side_margin: Dp = 20.dp,
    val spacing_md: Dp = 24.dp,
    val spacing_lg: Dp = 32.dp,
    val spacing_xl: Dp = 40.dp,
    val spacing_2xl: Dp = 80.dp
)

val SightUPTheme.spacing: SightUPSpacing
    @Composable
    get() = if (isSystemInDarkTheme()) SightUPSpacing() else SightUPSpacing()
