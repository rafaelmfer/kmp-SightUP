@file:OptIn(ExperimentalMaterial3Api::class)

package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.applyIf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close

@Composable
fun SDSBottomSheet(
    modifier: Modifier = Modifier,
    /**
     * @param fullHeight: Whether the bottom sheet should take the full height of the screen or not.
     */
    fullHeight: Boolean = false,
    /**
     * @param isDismissible: Whether the bottom sheet can be dismissed by dragging it down or not.
     */
    isDismissible: Boolean = true,
    /**
     * @param expanded: Expanded state of the bottom sheet. Tells if the bottom sheet should be shown or not in the screen.
     */
    expanded: BottomSheetEnum = BottomSheetEnum.HIDE,
    /**
     * @param onExpandedChange: Callback to be called when the bottom sheet is expanded or collapsed.
     * Useful to get and update the current state of the bottom sheet.
     */
    onExpandedChange: (BottomSheetEnum) -> Unit = { },
    /**
     * @param sheetState: SheetState to control the bottom sheet. If not provided, a new one will be created.
     * If you want more control over the sheetState, you can create it yourself and pass it here.
     */
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { isDismissible },
    ),
    /**
     * @param sheetContent: Content of the bottom sheet.
     */
    sheetContent: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit,
    /**
     * @param title: Title of the bottom sheet. If not provided, the top bar will not be shown.
     */
    title: String? = null,
    /**
     * @param iconLeft: Icon to the left of the title. If not provided, the icon will not be shown.
     */
    iconLeft: Any? = null,
    /**
     * @param iconRight: Icon to the right of the title.
     */
    iconRight: Any? = Res.drawable.close,
    /**
     * @param iconRightVisible: Whether the icon right is visible or not.
     */
    iconRightVisible: Boolean = false,
    onIconLeftClick: () -> Unit = {},
    onIconRightClick: (() -> Unit)? = null,
    onDismiss: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var openBottomSheet by remember(expanded) { mutableStateOf(expanded) }

    fun toggleBottomSheet(toggle: BottomSheetEnum) {
        openBottomSheet = toggle
        onExpandedChange(openBottomSheet)
    }

    fun hideBottomSheetWithAnimation() {
        scope.hideBottomSheetWithAnimation(sheetState, ::toggleBottomSheet)
    }

    LaunchedEffect(expanded) {
        toggleBottomSheet(expanded)
    }

    if (openBottomSheet == BottomSheetEnum.SHOW) {
        ModalBottomSheet(
            modifier = Modifier
                .padding(top = SightUPTheme.spacing.spacing_xl)
                .displayCutoutPadding()
                .then(modifier),
            onDismissRequest = {
                toggleBottomSheet(BottomSheetEnum.HIDE)
                onDismiss()
            },
            properties = ModalBottomSheetProperties(shouldDismissOnBackPress = isDismissible),
            sheetState = sheetState,
            shape = RoundedCornerShape(
                topStart = SightUPBorder.Radius.xl,
                topEnd = SightUPBorder.Radius.xl,
                bottomStart = SightUPBorder.Radius.none,
                bottomEnd = SightUPBorder.Radius.none
            ),
            containerColor = SightUPTheme.sightUPColors.background_light,
            dragHandle = null,
            content = {
                if (title != null) {
                    Column(
                        modifier = Modifier
                            .padding(
                                top = SightUPTheme.spacing.spacing_sm,
                            )
                    ) {
                        SDSTopBar(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(
                                    start = SightUPTheme.spacing.spacing_sm,
                                    end = SightUPTheme.spacing.spacing_sm,
                                ),
                            title = title,
                            iconLeftVisible = iconLeft != null,
                            iconLeft = iconLeft,
                            onLeftButtonClick = {
                                onIconLeftClick()
                            },
                            iconRightVisible = iconRightVisible,
                            iconRight = iconRight,
                            onRightButtonClick = {
                                onIconRightClick?.invoke() ?: hideBottomSheetWithAnimation()
                            },
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .applyIf(fullHeight) { fillMaxHeight() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    sheetContent(onDismiss)
                }
            }
        )
    }
}

fun CoroutineScope.hideBottomSheetWithAnimation(
    sheetState: SheetState,
    onBottomSheetVisibilityChange: (BottomSheetEnum) -> Unit,
    onFinish: (() -> Unit)? = null,
) {
    this.launch {
        sheetState.hide()
    }.invokeOnCompletion {
        if (!sheetState.isVisible) {
            onBottomSheetVisibilityChange(BottomSheetEnum.HIDE)
            onFinish?.invoke()
        }
    }
}