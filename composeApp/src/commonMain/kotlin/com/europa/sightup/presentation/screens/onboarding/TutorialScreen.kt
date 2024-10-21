package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.SightUPApp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.apple.AppleSignInButton
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform
import sightupkmpapp.composeapp.generated.resources.tutorial_four_content
import sightupkmpapp.composeapp.generated.resources.tutorial_four_title
import sightupkmpapp.composeapp.generated.resources.tutorial_one_content
import sightupkmpapp.composeapp.generated.resources.tutorial_one_title
import sightupkmpapp.composeapp.generated.resources.tutorial_primary_button
import sightupkmpapp.composeapp.generated.resources.tutorial_primary_button_last_step
import sightupkmpapp.composeapp.generated.resources.tutorial_secondary_button
import sightupkmpapp.composeapp.generated.resources.tutorial_secondary_button_last_step
import sightupkmpapp.composeapp.generated.resources.tutorial_three_content
import sightupkmpapp.composeapp.generated.resources.tutorial_three_title
import sightupkmpapp.composeapp.generated.resources.tutorial_two_content
import sightupkmpapp.composeapp.generated.resources.tutorial_two_title

@Composable
fun TutorialScreen(navController: NavController? = null) {
    var currentStep by remember { mutableStateOf(1) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val titles = listOf(
        stringResource(Res.string.tutorial_one_title),
        stringResource(Res.string.tutorial_two_title),
        stringResource(Res.string.tutorial_three_title),
        stringResource(Res.string.tutorial_four_title)
    )

    val descriptions = listOf(
        stringResource(Res.string.tutorial_one_content),
        stringResource(Res.string.tutorial_two_content),
        stringResource(Res.string.tutorial_three_content),
        stringResource(Res.string.tutorial_four_content)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            onClick = {
                if (currentStep > 1) {
                    currentStep--
                } else {
                    navController?.popBackStack()
                }
            },
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_xs,
                    top = SightUPTheme.spacing.spacing_xs
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.weight(ONE_FLOAT))
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(ONE_FLOAT))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    top = SightUPTheme.spacing.spacing_xs,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_lg,
                )
        ) {
            Text(
                text = titles[currentStep - 1],
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.h3
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_24))
            Text(
                text = descriptions[currentStep - 1],
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_32))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SDSButton(
                    text = if (currentStep >= 4) {
                        stringResource(Res.string.tutorial_secondary_button_last_step)
                    } else {
                        stringResource(Res.string.tutorial_secondary_button)
                    },
                    onClick = {
                        navController?.navigate(SightUPApp) // Go HOME app
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = if (currentStep >= 4) ButtonStyle.OUTLINED else ButtonStyle.TEXT
                )
                Spacer(modifier = Modifier.width(SightUPTheme.sizes.size_16))
                SDSButton(
                    text = if (currentStep >= 4) {
                        stringResource(Res.string.tutorial_primary_button_last_step)
                    } else {
                        stringResource(Res.string.tutorial_primary_button)
                    },
                    onClick = {
                        if (currentStep < 4) {
                            currentStep++
                        } else {
                            isBottomSheetVisible = true
                        }
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = ButtonStyle.PRIMARY
                )
            }
        }
    }

    var inputText by remember { mutableStateOf("") }

    if (isBottomSheetVisible) {
        SDSBottomSheet(
            isVisible = isBottomSheetVisible,
            onDismiss = { isBottomSheetVisible = false },
            fullHeight = true,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Login or Signup", style = SightUPTheme.textStyles.h4)
                    Spacer(modifier = Modifier.height(16.dp))
                    SDSInput(
                        value = inputText,
                        onValueChange = { newText -> inputText = newText },
                        label = "Email",
                        hint = "Email",
                        isError = false,
                        isEnabled = true,
                        fullWidth = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* Ação de continuar */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false // Desabilitado inicialmente
                    ) {
                        Text("Continue")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    GoogleButtonUiContainer(
                        modifier = Modifier.fillMaxWidth(),
                        onGoogleSignInResult = { googleAuthResult ->
                            val user = googleAuthResult?.idToken
                            println(user)

                            navController?.navigate(SightUPApp) // Go HOME app
                        }
                    ) {
                        GoogleSignInButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = { this.onClick() },
                            text = "Continue with Google"
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AppleButtonUiContainer(
                        onResult = { firebaseUserApple ->
                            showToast(
                                message = "Token Apple Success",
                                bottomPadding = 40,
                            )

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
        )
    }
}