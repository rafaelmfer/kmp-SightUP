package com.europa.sightup.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    title: String,
    description: String,
    subtitle: String,
    list: List<String>,
    buttonText: String,
    // Add the parameter onButton click
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var showSheet by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(onClick = { showSheet = true }) {
            Text(text = "Show Bottom Sheet")
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(SightUPTheme.sizes.size_20)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top=SightUPTheme.spacing.spacing_lg,
                        end=SightUPTheme.spacing.spacing_side_margin,
                        start=SightUPTheme.spacing.spacing_side_margin,
                        bottom=52.dp)
                    .fillMaxWidth(),
            ) {
                Text(text = title, style = SightUPTheme.textStyles.h1)
                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))

                Text(text = description, style = SightUPTheme.textStyles.body)
                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

                Text(text = subtitle, style = SightUPTheme.textStyles.small)
                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))

                list.forEach { item ->
                    Text(text = "• $item", style = SightUPTheme.textStyles.caption)
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = SightUPTheme.spacing.spacing_md),
                    shape = SightUPTheme.shapes.small,
                    onClick = { showSheet = false }
                ) {
                    Text(text = buttonText, style = SightUPTheme.textStyles.small)
                }
            }
        }
    }
}
