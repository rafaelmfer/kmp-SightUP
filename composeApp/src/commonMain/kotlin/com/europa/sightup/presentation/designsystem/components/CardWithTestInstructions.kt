package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.SightUPLineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun CardWithTestInstructions(
    text: String,
    icon: ImageVector? = null,
    iconDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = SightUPTheme.sightUPColors.border_card, shape = RoundedCornerShape(8.dp))
            .padding(SightUPTheme.spacing.spacing_base),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            lineHeight = SightUPLineHeight.default.lineHeight_xs,
            modifier = Modifier.weight(1f)
        )

        if (icon != null) {
            Icon(
                icon,
                contentDescription = iconDescription,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}