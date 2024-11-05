package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
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
            fullWidth = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
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

        TextButton(
            onClick = {
                // TODO: Implement forgot password
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                ),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = SightUPTheme.sightUPColors.text_primary,
                disabledContainerColor = SightUPTheme.sightUPColors.neutral_200,
                disabledContentColor = SightUPTheme.sightUPColors.neutral_500,
            ),
            shape = SightUPTheme.shapes.small,
            contentPadding = ButtonDefaults.ContentPadding,
        ) {
            Text(text = stringResource(Res.string.forgot_password), style = SightUPTheme.textStyles.button)
        }
    }
}