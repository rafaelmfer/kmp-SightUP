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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.onboarding.setupProfile.ProfileStep
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.UIState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.welcome
import sightupkmpapp.composeapp.generated.resources.welcome_content
import sightupkmpapp.composeapp.generated.resources.welcome_later_button
import sightupkmpapp.composeapp.generated.resources.welcome_take_test_button

@Composable
fun WelcomeScreen(
    navController: NavController? = null,
    // TODO: Change this after testing
    createProfile: Boolean = false,
) {
    val viewModel = koinViewModel<WelcomeViewModel>()
    val userState by viewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    if (userState is UIState.Success) {
        val userResponse = (userState as UIState.Success).data

        if (userResponse.email != null && !createProfile) {
            ShowProfileSetup(viewModel, navController)
        }
        MainWelcomeScreen(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowProfileSetup(
    viewModel: WelcomeViewModel,
    navController: NavController? = null,
) {
    val bottomSheetState by viewModel.bottomSheetState.collectAsState()

    val step = when (viewModel.outerStep) {
        1 -> ProfileStep.Step1
        2 -> ProfileStep.Step2
        else -> ProfileStep.Step3
    }

    SDSBottomSheet(
        fullHeight = true,
        isDismissible = false,
        expanded = bottomSheetState,
        sheetContent = { step.content(viewModel) },
        title = step.title,
        iconLeft = step.iconLeft,
        onIconLeftClick = {
            if (viewModel.outerStep == 3) {
                if (viewModel.currentStep > 1) {
                    viewModel.currentStep--
                } else {
                    navController?.popBackStack()
                }
            }
        },
        iconRight = step.iconRight,
        iconRightVisible = step.iconRightVisible,
        onIconRightClick = { viewModel.hideBottomSheet() }
    )
}


@Composable
fun MainWelcomeScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(ONE_FLOAT))
        Image(
            painter = painterResource(Res.drawable.welcome),
            contentDescription = null,
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
                text = "Welcome to SightUP",
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.h3
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_16))
            Text(
                text = stringResource(Res.string.welcome_content),
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_32))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SDSButton(
                    text = stringResource(Res.string.welcome_later_button),
                    onClick = {
                        navController?.navigate(Home) {
                            // Navigate to Home and remove all previous screens from the backstack
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = ButtonStyle.OUTLINED
                )
                Spacer(modifier = Modifier.width(SightUPTheme.sizes.size_16))
                SDSButton(
                    text = stringResource(Res.string.welcome_take_test_button),
                    onClick = {
                        navController?.navigate(TestScreens.TestRoot) {
                            // Navigate to TestRoot and remove all previous screens from the backstack
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = ButtonStyle.PRIMARY
                )
            }
        }
    }
}
