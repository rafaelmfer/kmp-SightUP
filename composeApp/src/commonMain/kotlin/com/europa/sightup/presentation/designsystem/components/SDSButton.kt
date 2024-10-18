package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ButtonStyle {
    PRIMARY,
    TEXT,
    OUTLINED,
}

@Composable
fun SDSButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.PRIMARY,
    textStyle: TextStyle = SightUPTheme.textStyles.body,
    shape: Shape = SightUPTheme.shapes.small,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
) {
    when (buttonStyle) {
        ButtonStyle.PRIMARY -> Button(
            onClick = onClick,
            modifier = modifier,
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
            Text(text = text, style = textStyle)
        }

        ButtonStyle.TEXT -> TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors(
                containerColor = SightUPTheme.sightUPColors.white,
                contentColor = SightUPTheme.sightUPColors.primary_600,
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
            Text(text = text, style = textStyle)
        }

        ButtonStyle.OUTLINED -> OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = SightUPTheme.sightUPColors.white,
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
            Text(text = text, style = textStyle)
        }
    }
}

@Preview
@Composable
fun SdsButtonScreen() {
    Column(
        modifier = Modifier
            .padding(SightUPTheme.spacing.spacing_base)
            .border(SightUPBorder.Width.sm, SightUPTheme.sightUPColors.neutral_300)
            .padding(SightUPTheme.spacing.spacing_base)
    ) {
        SDSButton(
            text = "Primary Button",
            onClick = {
                showToast(
                    message = "Button clicked",
                    bottomPadding = 100,
                )
            },
            modifier = Modifier,
            buttonStyle = ButtonStyle.PRIMARY,
        )

        SDSButton(
            text = "Text Button",
            onClick = {
                showToast(
                    message = "Button clicked",
                    bottomPadding = 100,
                )
            },
            modifier = Modifier,
            buttonStyle = ButtonStyle.TEXT,
        )

        SDSButton(
            text = "Outlined Button",
            onClick = {
                showToast(
                    message = "Button clicked",
                    bottomPadding = 100,
                )
            },
            modifier = Modifier,
            buttonStyle = ButtonStyle.OUTLINED,
        )
    }
}