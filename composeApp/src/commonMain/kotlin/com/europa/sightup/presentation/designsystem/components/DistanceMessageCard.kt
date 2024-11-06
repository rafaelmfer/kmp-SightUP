package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun DistanceMessageCard(
    text: String,
    textColor: Color = SightUPTheme.sightUPColors.info_300,
    backgroundColor: Color = SightUPTheme.sightUPColors.background_info,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            .padding(top = SightUPTheme.spacing.spacing_lg)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .then(modifier),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(SightUPTheme.spacing.spacing_base),
            text = text,
            style = SightUPTheme.textStyles.large,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}