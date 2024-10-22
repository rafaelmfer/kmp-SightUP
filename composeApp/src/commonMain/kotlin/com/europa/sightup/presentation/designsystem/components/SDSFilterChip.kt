package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SDSFilterChip(
    text: String = "Text Chip",
    isSelected: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    FilterChip(
        selected = isSelected,
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        label = {
            Text(
                text = text,
                style = SightUPTheme.textStyles.body2,
            )
        },
        leadingIcon = null,
        trailingIcon = null,
        elevation = FilterChipDefaults.filterChipElevation(),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = SightUPTheme.sightUPColors.white,
            selectedContainerColor = SightUPTheme.sightUPColors.primary_600,
            disabledContainerColor = SightUPTheme.sightUPColors.neutral_200,
            disabledSelectedContainerColor = SightUPTheme.sightUPColors.neutral_200,
            labelColor = SightUPTheme.sightUPColors.text_primary,
            selectedLabelColor = SightUPTheme.sightUPColors.text_secondary,
            disabledLabelColor = SightUPTheme.sightUPColors.text_disabled,
        ),
        shape = SightUPTheme.shapes.large,
        border = FilterChipDefaults.filterChipBorder(
            enabled = isEnabled,
            selected = isSelected,
            borderColor = SightUPTheme.sightUPColors.border_default,
            selectedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            disabledSelectedBorderColor = Color.Transparent,
            borderWidth = SightUPBorder.Width.sm,
            selectedBorderWidth = SightUPBorder.Width.none,
        ),
        interactionSource = interactionSource
    )
}

@Preview
@Composable
fun SDSFilterChipScreen() {
    val isSelected = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = SightUPTheme.spacing.spacing_base,
            alignment = Alignment.CenterVertically
        )
    ) {
        SDSFilterChip(
            text = "Filter Chip",
            isSelected = false,
            isEnabled = true,
            onClick = {
            }
        )
        SDSFilterChip(
            text = "Filter Chip",
            isSelected = true,
            isEnabled = true,
            onClick = {
            }
        )
        SDSFilterChip(
            text = "Filter Chip",
            isSelected = false,
            isEnabled = false,
            onClick = {
            }
        )

        SDSFilterChip(
            text = "Filter Chip Interactive",
            isSelected = isSelected.value,
            isEnabled = true,
            onClick = {
                isSelected.value = !isSelected.value
                showToast(
                    "Filter Chip clicked",
                    bottomPadding = 40
                )
            }
        )
    }
}