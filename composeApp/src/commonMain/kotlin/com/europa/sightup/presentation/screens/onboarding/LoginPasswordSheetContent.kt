package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSDivider
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.app_name
import sightupkmpapp.composeapp.generated.resources.continue_string
import sightupkmpapp.composeapp.generated.resources.forgot_password
import sightupkmpapp.composeapp.generated.resources.password
import sightupkmpapp.composeapp.generated.resources.sign_up_welcome

@Composable
fun LoginPasswordSheetContent(
    navController: NavController? = null,
    onContinueClicked: (String) -> Unit,
) {
    var password: String by remember { mutableStateOf("") }
    val isButtonEnabled = password.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(SightUPTheme.sizes.size_16))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.app_name),
            style = SightUPTheme.textStyles.h3,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.sign_up_welcome),
            style = SightUPTheme.textStyles.subtitle,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        SDSInput(
            value = password,
            onValueChange = { newText -> password = newText },
            label = stringResource(Res.string.password),
            hint = stringResource(Res.string.password),
            fullWidth = true
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        SDSButton(
            text = stringResource(Res.string.continue_string),
            onClick = {
                onContinueClicked(password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        SDSDivider()
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        SDSButton(
            text = stringResource(Res.string.forgot_password),
            onClick = {
                // TODO: Implement forgot password
            },
            modifier = Modifier.fillMaxWidth(),
            buttonStyle = ButtonStyle.TEXT
        )
    }
}