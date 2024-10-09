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
    /**
     * @property spacing_none is a Dp property that represents the none spacing value.
     * The value is set to 0dp.
     */
    val spacing_none: Dp = 0.dp,
    /**
     * @property spacing_2xs is a Dp property that represents the small spacing value.
     * The value is set to 4dp.
     */
    val spacing_2xs: Dp = 4.dp,
    /**
     * @property spacing_xs is a Dp property that represents the small spacing value.
     * The value is set to 8dp.
     */
    val spacing_xs: Dp = 8.dp,
    /**
     * @property spacing_sm is a Dp property that represents the small spacing value.
     * The value is set to 12dp.
     */
    val spacing_sm: Dp = 12.dp,
    /**
     * @property spacing_base is a Dp property that represents the base spacing value.
     * The value is set to 16dp.
     */
    val spacing_base: Dp = 16.dp,
    /**
     * @property spacing_side_margin is a Dp property that represents the side margin spacing value.
     * The value is set to 20dp.
     */
    val spacing_side_margin: Dp = 20.dp,
    /**
     * @property spacing_md is a Dp property that represents the medium spacing value.
     * The value is set to 24dp.
     */
    val spacing_md: Dp = 24.dp,
    /**
     * @property spacing_lg is a Dp property that represents the large spacing value.
     * The value is set to 32dp.
     */
    val spacing_lg: Dp = 32.dp,
    /**
     * @property spacing_xl is a Dp property that represents the extra large spacing value.
     * The value is set to 40dp.
     */
    val spacing_xl: Dp = 40.dp,
    /**
     * @property spacing_2xl is a Dp property that represents the extra extra large spacing value.
     * The value is set to 80dp.
     */
    val spacing_2xl: Dp = 80.dp
)

val SightUPTheme.spacing: SightUPSpacing
    @Composable
    get() = if (isSystemInDarkTheme()) SightUPSpacing() else SightUPSpacing()
