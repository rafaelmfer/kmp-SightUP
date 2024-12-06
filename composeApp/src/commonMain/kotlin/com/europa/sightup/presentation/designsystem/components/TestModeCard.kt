package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.clickableWithRipple
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.smartwatch_mode
import sightupkmpapp.composeapp.generated.resources.touch_mode
import sightupkmpapp.composeapp.generated.resources.voice_mode

@Composable
fun ModeSelectionCard(mode: TestModeEnum, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) SightUPTheme.sightUPColors.background_default else SightUPTheme.colors.outline
    val iconColor = if (isSelected) SightUPTheme.sightUPColors.primary_700 else SightUPTheme.sightUPColors.neutral_400
    val backgroundColor =
        if (isSelected) SightUPTheme.sightUPColors.primary_200 else SightUPTheme.sightUPColors.background_default
    val textColor = if (isSelected) SightUPTheme.sightUPColors.primary_700 else SightUPTheme.sightUPColors.black

    val icon = when (mode) {
        TestModeEnum.Touch -> TestModeEnum.Touch.iconResource
        TestModeEnum.Voice -> TestModeEnum.Voice.iconResource
        TestModeEnum.SmartWatch -> TestModeEnum.SmartWatch.iconResource
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = SightUPBorder.Width.sm,
                color = borderColor,
                shape = SightUPTheme.shapes.small
            )
            .clip(SightUPTheme.shapes.small)
            .background(backgroundColor)
            .clickableWithRipple(onClick = onClick)
            .padding(SightUPTheme.spacing.spacing_base),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = mode.displayName,
            modifier = Modifier
                .padding(
                    top = SightUPTheme.spacing.spacing_xs,
                    bottom = SightUPTheme.spacing.spacing_xs,
                    end = SightUPTheme.spacing.spacing_base
                )
                .size(48.dp),
            tint = iconColor,
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
                .padding(
                    top = SightUPTheme.spacing.spacing_xs,
                    bottom = SightUPTheme.spacing.spacing_xs,
                )
        ) {
            Text(
                text = mode.displayName,
                style = SightUPTheme.textStyles.large,
                color = textColor,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mode.description,
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Serializable
enum class TestModeEnum(
    val displayName: String,
    val description: String,
    val iconResource: DrawableResource,
) {
    Touch(
        "Touch mode",
        "Use mobile screen",
        Res.drawable.touch_mode,
    ),
    Voice(
        "Voice mode",
        "Use voice commands",
        Res.drawable.voice_mode,
    ),
    SmartWatch(
        "Smartwatch mode",
        "Use smartwatch screen by connecting via Bluetooth. You need a pairing process.",
        Res.drawable.smartwatch_mode,
    )
}