package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform
import sightupkmpapp.composeapp.generated.resources.smartwatch_mode
import sightupkmpapp.composeapp.generated.resources.touch_mode
import sightupkmpapp.composeapp.generated.resources.voice_mode

@Composable
fun ModeSelectionCard(mode: TestModeEnum, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) SightUPTheme.colors.onBackground else SightUPTheme.colors.outline
    val icon = when (mode) {
        TestModeEnum.Touch -> Res.drawable.compose_multiplatform
        TestModeEnum.Voice -> Res.drawable.compose_multiplatform
        TestModeEnum.SmartWatch -> Res.drawable.compose_multiplatform
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
            .padding(SightUPTheme.spacing.spacing_base)
            .height(72.dp),
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
                style = SightUPTheme.textStyles.button
                    .copy(color = SightUPTheme.colors.tertiary)
            )
        }
    }
}

@Serializable
enum class TestModeEnum(val displayName: String, val description: String, val iconResource: DrawableResource) {
    Touch(
        "Touch mode",
        "Use mobile screen",
        Res.drawable.touch_mode
    ),
    Voice(
        "Voice mode",
        "Use voice commands",
        Res.drawable.voice_mode
    ),
    SmartWatch(
        "Smartwatch mode",
        "Use smartwatch screen by connecting via Bluetooth. You need a pairing process.",
        Res.drawable.smartwatch_mode
    )
}

