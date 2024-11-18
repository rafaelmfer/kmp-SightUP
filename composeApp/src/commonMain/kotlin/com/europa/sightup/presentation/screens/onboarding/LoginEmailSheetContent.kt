package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSDivider
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.apple.AppleSignInButton
import kotlinx.coroutines.launch
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.apple
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.continue_string
import sightupkmpapp.composeapp.generated.resources.email
import sightupkmpapp.composeapp.generated.resources.google

@Composable
fun LoginEmailSheetContent(
    onContinueClicked: (String) -> Unit = {},
    onGoogleClicked: (String) -> Unit = {},
    onAppleClicked: (String) -> Unit = {},
    errorMessage: String = "",
) {
    val scope = rememberCoroutineScope()

    var inputText: String by remember { mutableStateOf("") }
    val isButtonEnabled = inputText.isNotEmpty()
    val isError = errorMessage.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(SightUPTheme.sizes.size_16))
        SDSInput(
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = stringResource(Res.string.email),
            hint = stringResource(Res.string.email),
            fullWidth = true,
            isError = isError,
        )
        if (isError) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.close),
                    contentDescription = null,
                    tint = SightUPTheme.sightUPColors.error_300,
                    modifier = Modifier.size(SightUPTheme.sizes.size_16)
                )
                Spacer(Modifier.width(SightUPTheme.sizes.size_8))
                Text(
                    text = errorMessage,
                    color = SightUPTheme.sightUPColors.error_300,
                    style = SightUPTheme.textStyles.body2,
                    modifier = Modifier
                )
            }
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        SDSButton(
            text = stringResource(Res.string.continue_string),
            onClick = {
                onContinueClicked(inputText)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        SDSDivider()
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))

        GoogleButtonUiContainerFirebase(
            modifier = Modifier.fillMaxWidth(),
            onResult = { result ->
                scope.launch {
                    result.getOrNull()?.getIdToken(false)?.let {
                        onGoogleClicked(it)
                    }
                }
            },
            linkAccount = false
        ) {
            OutlinedButton(
                onClick = { this.onClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(
                        minHeight = SightUPTheme.sizes.size_48
                    ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = SightUPTheme.sightUPColors.background_default,
                ),
                border = BorderStroke(
                    width = SightUPBorder.Width.sm,
                    color = SightUPTheme.sightUPColors.background_button
                ),
                shape = SightUPTheme.shapes.small,
            ) {
                Image(
                    painter = painterResource(Res.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(SightUPTheme.sizes.size_12))
                Text(
                    text = "Continue with Google",
                    style = SightUPTheme.textStyles.button,
                    color = SightUPTheme.sightUPColors.primary_700
                )
            }
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        AppleButtonUiContainer(
            onResult = { result ->
                showToast(
                    message = "Not implemented yet",
                    bottomPadding = 40,
                )
                // TODO: Implement Apple Sign In if we have time
//                scope.launch {
//                    result.getOrNull()?.getIdToken(false)?.let { onAppleClicked(it) }
//                }
            },
            linkAccount = false
        ) {
            AppleSignInButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { this.onClick() },
                text = "Continue with Apple"
            )
            OutlinedButton(
                onClick = { this.onClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(
                        minHeight = SightUPTheme.sizes.size_48
                    ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = SightUPTheme.sightUPColors.background_default,
                ),
                border = BorderStroke(
                    width = SightUPBorder.Width.sm,
                    color = SightUPTheme.sightUPColors.background_button
                ),
                shape = SightUPTheme.shapes.small,
            ) {
                Image(
                    painter = painterResource(Res.drawable.apple),
                    contentDescription = "Apple",
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(SightUPTheme.sizes.size_12))
                Text(
                    text = "Continue with Apple",
                    style = SightUPTheme.textStyles.button,
                    color = SightUPTheme.sightUPColors.primary_700
                )
            }
        }
    }
}