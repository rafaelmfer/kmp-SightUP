package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.clickableWithRipple
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SDSListButtonsSelectable(items: List<String>, onSelectedChange: (String) -> Unit) {
    var selectedItem by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs),
    ) {
        for (item in items) {
            SDSButtonSelectable(
                itemText = item,
                isSelected = selectedItem == item,
                onItemSelected = {
                    selectedItem = item
                    onSelectedChange(item)
                }
            )
        }
    }
}

@Composable
fun SDSButtonSelectable(
    itemText: String,
    isSelected: Boolean,
    onItemSelected: () -> Unit,
) {
    val backgroundColor =
        if (isSelected) SightUPTheme.sightUPColors.primary_200 else SightUPTheme.sightUPColors.background_default

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.small)
            .clickableWithRipple {
                onItemSelected()
            }
            .background(backgroundColor)
            .defaultMinSize(minHeight = SightUPTheme.sizes.size_40),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = itemText,
            style = SightUPTheme.textStyles.body,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) SightUPTheme.sightUPColors.primary_700 else SightUPTheme.sightUPColors.text_primary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun SDSListButtonsSelectablePreview() {
    val items = listOf("One", "Two", "Three", "Four", "Five")
    SDSListButtonsSelectable(
        items = items,
        onSelectedChange = {
            showToast(
                it,
                bottomPadding = 40
            )
        }
    )
}