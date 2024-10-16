package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.applyIf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDSBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    fullHeight: Boolean = false,
    onDismiss: () -> Unit,
    sheetContent: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(isVisible) {
        if (isVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        modifier = Modifier
            .padding(top = SightUPTheme.spacing.spacing_xl)
            .displayCutoutPadding()
            .then(modifier),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = SightUPBorder.Radius.xl,
            topEnd = SightUPBorder.Radius.xl,
            bottomStart = SightUPBorder.Radius.none,
            bottomEnd = SightUPBorder.Radius.none
        ),

        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .applyIf(fullHeight) { fillMaxHeight() }
                    .padding(SightUPTheme.spacing.spacing_base),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                sheetContent()
            }
        }
    )
}
