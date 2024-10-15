package com.europa.sightup.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import sightupkmpapp.composeapp.generated.resources.Res
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform


@Composable
fun ModeSelectionCard(mode: Mode, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) SightUPTheme.colors.onBackground else SightUPTheme.colors.outline
    val icon = when (mode) {
        Mode.Touch -> Res.drawable.compose_multiplatform
        Mode.Voice -> Res.drawable.compose_multiplatform
        Mode.SmartWatch -> Res.drawable.compose_multiplatform
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(SightUPTheme.spacing.spacing_base),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = mode.displayName,
            modifier = Modifier.size(48.dp)
                .padding(end = SightUPTheme.spacing.spacing_base),
        )
        Column {
            Text(
                text = mode.displayName,
                style = SightUPTheme.textStyles.large
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mode.description,
                style = SightUPTheme.textStyles.small
                    .copy(color = SightUPTheme.colors.tertiary)
            )
        }

    }
}

// Enum for modes
enum class Mode(val displayName: String, val description: String) {
    Touch("Touch mode", "Use mobile screen"),
    Voice("Voice mode", "Use voice commands"),
    SmartWatch("Smartwatch mode", "Use smartwatch screen by connecting via Bluetooth. You need a pairing process.")
}