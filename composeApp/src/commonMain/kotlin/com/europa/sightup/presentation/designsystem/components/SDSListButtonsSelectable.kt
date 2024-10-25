package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SDSListButtonsSelectable(items: List<String>, onSelectedChange: (String) -> Unit) {
    var selectedItem by remember { mutableStateOf<String?>(null) }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs),
        contentPadding = PaddingValues(0.dp),
    ) {
        items(items) { item ->
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
        if (isSelected) SightUPTheme.sightUPColors.primary_200 else SightUPTheme.sightUPColors.neutral_0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = SightUPTheme.shapes.small,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = SightUPTheme.spacing.spacing_xs),
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