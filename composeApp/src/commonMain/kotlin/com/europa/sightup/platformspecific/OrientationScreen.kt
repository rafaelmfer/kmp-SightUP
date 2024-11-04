package com.europa.sightup.platformspecific

import androidx.compose.runtime.Composable

@Composable
expect fun LandscapeOrientation(
    isLandscape: Boolean,
    content: @Composable () -> Unit
)