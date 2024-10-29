package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.clock

@Preview
@Composable
fun SDSBadgeTime(
    timeMinutes: Int = 1,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .semantics {
                contentDescription = if (timeMinutes > 1) "$timeMinutes minutes" else "$timeMinutes minute"
            }
            .clip(SightUPTheme.shapes.large)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.text_primary,
                shape = SightUPTheme.shapes.large
            )
            .padding(
                horizontal = SightUPTheme.spacing.spacing_sm,
                vertical = SightUPTheme.spacing.spacing_2xs
            )
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(SightUPTheme.sizes.size_16),
            painter = painterResource(Res.drawable.clock),
            contentDescription = null,
            tint = SightUPTheme.sightUPColors.text_primary
        )
        Spacer(Modifier.width(SightUPTheme.spacing.spacing_2xs))
        Text(
            modifier = Modifier,
            text = "$timeMinutes min",
            color = SightUPTheme.sightUPColors.text_primary,
            style = SightUPTheme.textStyles.caption
        )
    }
}

@Preview
@Composable
fun SDSBadgeTimeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = SightUPTheme.spacing.spacing_base,
            alignment = Alignment.CenterVertically
        )
    ) {
        SDSBadgeTime()
        SDSBadgeTime(2)
        SDSBadgeTime(4)
        SDSBadgeTime(10)
    }
}