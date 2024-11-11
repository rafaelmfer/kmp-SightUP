package com.europa.sightup.platformspecific

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.sqrt

@Composable
expect fun getScreenWidth(): Dp

@Composable
expect fun getScreenHeight(): Dp

@Composable
fun getScreenSizeInInches(): Float {
    // Get the width and height in Dp
    val widthDp: Dp = getScreenWidth()
    val heightDp: Dp = getScreenHeight()

    // Use LocalDensity to get the current device density
    val density = LocalDensity.current.density

    // Convert width and height from dp to pixels
    val widthPx = widthDp.value * density
    val heightPx = heightDp.value * density

    // Approximate DPI density
    val approximateDpi = 160 * density

    // Calculate the screen diagonal in inches
    val widthInches = widthPx / approximateDpi
    val heightInches = heightPx / approximateDpi

    return sqrt(widthInches * widthInches + heightInches * heightInches)
}