package com.europa.sightup.presentation.designsystem.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Composable
fun SDSDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = SightUPTheme.sightUPColors.border_default,
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}