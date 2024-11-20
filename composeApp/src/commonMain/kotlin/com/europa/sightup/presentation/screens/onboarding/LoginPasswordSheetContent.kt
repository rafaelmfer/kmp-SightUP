package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSDivider
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.app_name
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.continue_string
import sightupkmpapp.composeapp.generated.resources.eye_hide
import sightupkmpapp.composeapp.generated.resources.eye_show
import sightupkmpapp.composeapp.generated.resources.forgot_password
import sightupkmpapp.composeapp.generated.resources.password
import sightupkmpapp.composeapp.generated.resources.sign_up_welcome

@Composable
fun LoginPasswordSheetContent(
    onContinueClicked: (String) -> Unit = {},
    errorMessage: String = "",
) {
    var password: String by remember { mutableStateOf("") }
    val isButtonEnabled = password.isNotEmpty()
    val isError = errorMessage.isNotEmpty()

    var showPassword by remember { mutableStateOf(false) }

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
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isError = isError,
            endIcon = {
                val iconColor = SightUPTheme.sightUPColors.text_primary
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            painter = painterResource(Res.drawable.eye_hide),
                            contentDescription = "Hide password",
                            tint = iconColor
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            painter = painterResource(Res.drawable.eye_show),
                            contentDescription = "Show password",
                            tint = iconColor
                        )
                    }
                }
            }
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