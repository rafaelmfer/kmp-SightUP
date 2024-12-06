package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close

@Composable
fun SDSDialog(
    showDialog: Boolean,
    onDismiss: (Boolean) -> Unit,
    title: String = "",
    onClose: (() -> Unit)? = null,
    content: @Composable (onDismiss: () -> Unit) -> Unit,
    onPrimaryClick: (() -> Unit)? = null,
    buttonPrimaryText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    buttonSecondaryText: String? = null,
    isDismissible: Boolean = true,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onClose?.invoke()
                onDismiss(false)
            },
            properties = DialogProperties(
                dismissOnBackPress = isDismissible,
                dismissOnClickOutside = isDismissible,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    .background(
                        color = SightUPTheme.sightUPColors.background_default,
                        shape = SightUPTheme.shapes.small
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (title.isNotBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = SightUPTheme.spacing.spacing_sm),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = title,
                                style = SightUPTheme.textStyles.h5,
                                color = SightUPTheme.sightUPColors.text_primary,
                                modifier = Modifier
                                    .padding(
                                        top = if (onClose != null) 0.dp else SightUPTheme.spacing.spacing_sm,
                                        start = SightUPTheme.spacing.spacing_md,
                                        end = if (onClose != null) SightUPTheme.spacing.spacing_base else SightUPTheme.spacing.spacing_md
                                    )
                                    .weight(ONE_FLOAT)
                            )

                            if (onClose != null) {
                                IconButton(
                                    onClick = {
                                        onClose()
                                        onDismiss(false)
                                    },
                                    modifier = Modifier
                                        .padding(
                                            end = SightUPTheme.spacing.spacing_sm
                                        )
                                        .wrapContentWidth()
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.close),
                                        contentDescription = "Close",
                                        modifier = Modifier.size(24.dp),
                                    )
                                }
                            }
                        }
                    }

                    content {
                        onDismiss(false)
                    }

                    if (buttonSecondaryText != null || onSecondaryClick != null || buttonPrimaryText != null || onPrimaryClick != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = SightUPTheme.spacing.spacing_md)
                                .padding(bottom = SightUPTheme.spacing.spacing_md),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (buttonSecondaryText != null && onSecondaryClick != null) {
                                SDSButton(
                                    text = buttonSecondaryText,
                                    onClick = {
                                        onSecondaryClick()
                                        onDismiss(false)
                                    },
                                    buttonStyle = ButtonStyle.OUTLINED,
                                    modifier = Modifier
                                )
                            }

                            Spacer(modifier = Modifier.weight(ONE_FLOAT))

                            if (buttonPrimaryText != null && onPrimaryClick != null) {
                                SDSButton(
                                    text = buttonPrimaryText,
                                    onClick = {
                                        onPrimaryClick()
                                    },
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SDSDialogExampleUse() {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialog = true }
        ) {
            Text("Show Dialog")
        }
    }

    SDSDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = it },
        title = "Example Dialog",
        onClose = { println("Dialog closed") },
        content = { onDismiss ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_md)
            ) {
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                Text("This is the dialog content.")
                Button(
                    onClick = {
                        println("Action from content!")
                        onDismiss()
                    }
                ) {
                    Text(text = "Close from Content")
                }
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            }
        },
        onPrimaryClick = { println("Primary clicked") },
        buttonPrimaryText = "Confirm",
        onSecondaryClick = { println("Secondary clicked") },
        buttonSecondaryText = "Cancel"
    )
}