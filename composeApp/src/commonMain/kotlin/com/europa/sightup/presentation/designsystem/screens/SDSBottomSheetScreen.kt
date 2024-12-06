@file:OptIn(ExperimentalMaterial3Api::class)

package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.designsystem.components.hideBottomSheetWithAnimation
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast

@Composable
fun SDSBottomSheetScreen() {
    /**
     * @param BottomSheetEnum.HIDE starts the bottom sheet hidden when open the screen.
     */
    var fullBottomSheetState by remember { mutableStateOf(BottomSheetEnum.HIDE) }
    var bottomSheetState by remember { mutableStateOf(BottomSheetEnum.HIDE) }
    var isFullBottomSheetVisibleAnim by remember { mutableStateOf(BottomSheetEnum.HIDE) }

    // Buttons that change the BottomSheetsStates to open
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SDSButton(
            onClick = {
                bottomSheetState = BottomSheetEnum.SHOW
            },
            text = "Not FullHeight BottomSheet"
        )

        SDSButton(
            onClick = { fullBottomSheetState = BottomSheetEnum.SHOW },
            text = "FullBottomSheet"
        )

        SDSButton(
            onClick = { isFullBottomSheetVisibleAnim = BottomSheetEnum.SHOW },
            text = "FullBottomSheet animation"
        )
    }

    SDSBottomSheet(
        isDismissible = false,
        expanded = bottomSheetState,
        onExpandedChange = {
            bottomSheetState = it
        },
        title = "Bottom Sheet",
        iconRightVisible = true,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SightUPTheme.spacing.spacing_md)
            ) {
                Text(
                    text = "Bottom Sheet Content",
                    style = SightUPTheme.textStyles.h5
                )
                Text(text = "Bottom Sheet that adapts to the content, not full height")
                Text(text = "This bottom sheet can't be dismissed by dragging it down.")
                Text(text = "Click Continue to close it. It'll close without animation.")
                Text(text = "By default, closing it by clicking outside or in the close button in the top right corner. It'll close with animation.")
                SDSButton(
                    text = "Continue",
                    onClick = {
                        bottomSheetState = BottomSheetEnum.HIDE
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
    )

    SDSBottomSheet(
        isDismissible = true,
        expanded = fullBottomSheetState,
        onExpandedChange = {
            fullBottomSheetState = it
        },
        onDismiss = {
            showToast(
                "Bottom Sheet Dismissed",
                bottomPadding = 40
            )
        },
        fullHeight = true,
        title = "Full Bottom Sheet",
        iconRightVisible = true,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Bottom Sheet Content",
                    style = SightUPTheme.textStyles.h5
                )
                Text(text = "Full height")
                Text(text = "Click Continue to close it. It'll close without animation.")
                Text(text = "Closing it by clicking outside or in the close button in the top right corner. It'll close with animation.")
                SDSButton(
                    text = "Continue",
                    onClick = {
                        fullBottomSheetState = BottomSheetEnum.HIDE
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
    )

    /**
     * In case you want to create a new screen with a bottom sheet, that you will trigger using a button from another screen,
     * you can use the following code. You will need to pass the visibility state and the function to change it as parameters.
     * This way, you can control the bottom sheet from another screen.
     */

    NewBottomSheetScreenControlledByExternal(
        isFullBottomSheetVisible = isFullBottomSheetVisibleAnim,
        onBottomSheetVisibilityChange = { newState ->
            isFullBottomSheetVisibleAnim = newState
        }
    )
}

@Composable
fun NewBottomSheetScreenControlledByExternal(
    isFullBottomSheetVisible: BottomSheetEnum,
    onBottomSheetVisibilityChange: (BottomSheetEnum) -> Unit,
) {
    /**
     * If you want to have the animation when you dismiss the bottom sheet, you need to use the scope to handle it,
     * and the sheetState to control the bottom sheet.
     */

    /**
     * @param scope: CoroutineScope to handle the animations of the bottom sheet.
     */
    val scope = rememberCoroutineScope()

    /**
     * @param sheetState: ModalBottomSheetState to control the bottom sheet.
     */
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }, // If you want to dismiss the bottom sheet by dragging it down, set it to true.
    )

    SDSBottomSheet(
        isDismissible = true,
        expanded = isFullBottomSheetVisible,
        onExpandedChange = {
            onBottomSheetVisibilityChange(it)
        },
        sheetState = sheetState,
        fullHeight = true,
        title = "Full Bottom Sheet",
        iconRightVisible = true,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Bottom Sheet Content",
                    style = SightUPTheme.textStyles.h5
                )
                Text(text = "Full height")
                Text(text = "Click Continue to close it. It'll close WITH animation, because the scope and the sheetState are being used.")
                Text(text = "Closing it by clicking outside or in the close button in the top right corner. It'll close with animation.")
                SDSButton(
                    text = "Continue",
                    onClick = {
                        scope.hideBottomSheetWithAnimation(
                            sheetState = sheetState,
                            onBottomSheetVisibilityChange = onBottomSheetVisibilityChange,
                            onFinish = {
                                showToast(
                                    "Bottom Sheet Hided with Animation",
                                    bottomPadding = 40
                                )
                            })
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
    )
}