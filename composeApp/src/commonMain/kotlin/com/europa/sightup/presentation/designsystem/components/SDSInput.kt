package com.europa.sightup.presentation.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.applyIf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDSInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    hint: String = "Placeholder",
    fullWidth: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor =
        animateColorAsState(
            targetValue = when {
                isError -> SightUPTheme.sightUPColors.error_300
                isFocused -> SightUPTheme.sightUPColors.primary_600
                !isEnabled -> SightUPTheme.sightUPColors.border_default
                else -> SightUPTheme.sightUPColors.border_default
            },
            animationSpec = tween(durationMillis = 150),
        )

    val backgroundColor =
        animateColorAsState(
            targetValue = when {
                isError -> SightUPTheme.sightUPColors.neutral_0
                !isEnabled -> SightUPTheme.sightUPColors.neutral_200
                else -> SightUPTheme.sightUPColors.neutral_0
            },
            animationSpec = tween(durationMillis = 150),
        )

    val labelColor = when {
        isError -> SightUPTheme.sightUPColors.text_primary
        !isEnabled -> SightUPTheme.sightUPColors.text_disabled
        else -> SightUPTheme.sightUPColors.text_primary
    }

    val textColor = when {
        !isEnabled -> SightUPTheme.sightUPColors.text_disabled
        else -> SightUPTheme.sightUPColors.text_primary
    }
    val hintColor = when {
        !isEnabled -> SightUPTheme.sightUPColors.text_disabled
        else -> SightUPTheme.sightUPColors.text_tertiary
    }

    Column(
        modifier = Modifier
            .applyIf(fullWidth) { fillMaxWidth() }
            .width(120.dp)
            .then(modifier)
    ) {
        Text(
            modifier = Modifier,
            text = label,
            color = labelColor,
            style = SightUPTheme.textStyles.body2
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))
        BasicTextField(
            modifier = Modifier
                .applyIf(fullWidth) { fillMaxWidth() },
            value = value,
            onValueChange = onValueChange,
            enabled = isEnabled,
            interactionSource = interactionSource,
            textStyle = SightUPTheme.textStyles.body.copy(color = textColor),
            cursorBrush = SolidColor(textColor),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = isEnabled,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                color = hintColor,
                                style = SightUPTheme.textStyles.body2
                            )
                        }
                    },
                    contentPadding = contentPadding,
                    container = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(SightUPTheme.shapes.small)
                                .border(
                                    width = if (isFocused) SightUPBorder.Width.md else SightUPBorder.Width.sm,
                                    color = borderColor.value,
                                    shape = SightUPTheme.shapes.small
                                )
                                .background(backgroundColor.value)
                        )
                    }
                )
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions
        )
    }
}