package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.color.SightUPColorPalette
import com.europa.sightup.presentation.ui.theme.color.SightUPContextColor
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun SDSLocationBadge(
    appTested: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = remember(appTested) {
        if (appTested) {
            SightUPContextColor.background_activate
        } else {
            SightUPContextColor.background_purple
        }
    }

    val textColor = remember(appTested) {
        if (appTested) {
            SightUPColorPalette.info_300
        } else {
            SightUPColorPalette.purple_300
        }
    }

    Text(
        modifier = Modifier
            .clip(SightUPTheme.shapes.extraLarge)
            .background(backgroundColor)
            .padding(
                horizontal = SightUPTheme.spacing.spacing_sm,
                vertical = 6.dp
            )
            .then(modifier),
        text = if (appTested) "SightUP" else "Clinic",
        color = textColor,
        style = SightUPTheme.textStyles.caption.copy(
            fontWeight = FontWeight.Bold
        )
    )
}