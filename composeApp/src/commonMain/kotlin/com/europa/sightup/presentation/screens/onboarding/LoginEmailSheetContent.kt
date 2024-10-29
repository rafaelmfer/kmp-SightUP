package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSDivider
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.apple.AppleSignInButton
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import kotlinx.coroutines.launch
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.continue_string
import sightupkmpapp.composeapp.generated.resources.email

@Composable
fun LoginEmailSheetContent(
    navController: NavController? = null,
    onContinueClicked: (String) -> Unit = {},
    onGoogleClicked: (String) -> Unit = {},
    onAppleClicked: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var inputText: String by remember { mutableStateOf("") }
    val isButtonEnabled = inputText.isNotEmpty()

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
            fullWidth = true
        )
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
            GoogleSignInButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { this.onClick() },
                text = "Continue with Google"
            )
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
        }
    }
}