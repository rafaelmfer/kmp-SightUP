package com.europa.sightup.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.OutlinedButton
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TextButton
import androidx.wear.compose.material3.TextButtonColors
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

enum class ButtonStyle {
    PRIMARY,
    TEXT,
    OUTLINED,
}

@Composable
fun SDSButtonWear(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.PRIMARY,
    textStyle: TextStyle = SightUPTheme.textStyles.button,
    shape: Shape = RoundedCornerShape(8.dp),
    contentPadding: PaddingValues = PaddingValues(SightUPTheme.spacing.spacing_xs),
    enabled: Boolean = true,
) {
    when (buttonStyle) {
        ButtonStyle.PRIMARY -> Button(
            onClick = onClick,
            modifier = modifier
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                ),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = SightUPTheme.sightUPColors.primary_600,
                contentColor = SightUPTheme.sightUPColors.white,
                disabledContainerColor = SightUPTheme.sightUPColors.neutral_200,
                disabledContentColor = SightUPTheme.sightUPColors.neutral_500,
            ),
            border = BorderStroke(
                width = SightUPBorder.Width.none,
                color = if (enabled) SightUPTheme.sightUPColors.white else SightUPTheme.sightUPColors.neutral_400
            ),
            shape = shape,
            contentPadding = contentPadding,
        ) {
            Text(
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        ButtonStyle.TEXT -> TextButton(
            onClick = onClick,
            modifier = modifier
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                ),
            enabled = enabled,
            colors = TextButtonColors(
                containerColor = SightUPTheme.sightUPColors.white,
                contentColor = SightUPTheme.sightUPColors.primary_600,
                disabledContainerColor = SightUPTheme.sightUPColors.neutral_200,
                disabledContentColor = SightUPTheme.sightUPColors.neutral_500,
            ),
            border = BorderStroke(
                width = SightUPBorder.Width.none,
                color = if (enabled) SightUPTheme.sightUPColors.white else SightUPTheme.sightUPColors.neutral_400
            ),

            ) {
            Text(
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        ButtonStyle.OUTLINED -> OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .clip(shape)
                .background(SightUPTheme.sightUPColors.white)
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                ),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = SightUPTheme.sightUPColors.background_default,
                contentColor = SightUPTheme.sightUPColors.primary_600,
                disabledContainerColor = SightUPTheme.sightUPColors.neutral_200,
                disabledContentColor = SightUPTheme.sightUPColors.neutral_500,
            ),
            border = BorderStroke(
                width = SightUPBorder.Width.sm,
                color = if (enabled) SightUPTheme.sightUPColors.primary_600 else SightUPTheme.sightUPColors.neutral_400
            ),
            shape = shape,
            contentPadding = contentPadding,
        ) {
            Text(
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}