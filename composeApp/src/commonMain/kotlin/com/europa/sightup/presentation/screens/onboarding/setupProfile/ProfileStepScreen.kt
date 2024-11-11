package com.europa.sightup.presentation.screens.onboarding.setupProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.StepProgressBar
import com.europa.sightup.presentation.screens.onboarding.WelcomeViewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_back
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.profile_body_one
import sightupkmpapp.composeapp.generated.resources.profile_setup_button
import sightupkmpapp.composeapp.generated.resources.profile_title
import sightupkmpapp.composeapp.generated.resources.welcome_later_button

sealed class ProfileStep(
    val title: String,
    val content: @Composable (WelcomeViewModel) -> Unit,
    val iconLeft: DrawableResource?,
    val iconRight: DrawableResource?,
    val iconRightVisible: Boolean = false,
) {
    data object Step1 : ProfileStep(
        title = "Create Account",
        content = { UserNameScreen(it) },
        iconLeft = null,
        iconRight = null,
        iconRightVisible = false
    )

    data object Step2 : ProfileStep(
        title = "Setup Profile",
        content = { ProfileBodyOne(it) },
        iconLeft = null,
        iconRight = null,
        iconRightVisible = false
    )

    data object Step3 : ProfileStep(
        title = "Setup Profile",
        content = { ProfileBodyTwo(it) },
        iconLeft = Res.drawable.arrow_back,
        iconRight = Res.drawable.close,
        iconRightVisible = true
    )
}

@Composable
fun ProfileBodyOne(
    viewModel: WelcomeViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_lg))
        Text(
            text = stringResource(Res.string.profile_title),
            style = SightUPTheme.textStyles.h5
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        Text(
            text = stringResource(Res.string.profile_body_one),
            style = SightUPTheme.textStyles.body
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xl))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
        ) {
            SDSButton(
                text = stringResource(Res.string.welcome_later_button),
                onClick = { viewModel.hideBottomSheet() },
                modifier = Modifier.weight(1f),
                buttonStyle = ButtonStyle.OUTLINED,
            )

            Spacer(Modifier.width(SightUPTheme.spacing.spacing_base))

            SDSButton(
                text = stringResource(Res.string.profile_setup_button),
                onClick = { viewModel.outerStep++ },
                modifier = Modifier.weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}

@Composable
fun ProfileBodyTwo(
    viewModel: WelcomeViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_lg))

        StepProgressBar(
            numberOfSteps = 5,
            currentStep = viewModel.currentStep,
        )

        when (viewModel.currentStep) {
            // Inside each composable a profile parameter is saved
            1 -> BirthDayScreen(
                viewModel = viewModel,
                onClickLeft = { viewModel.hideBottomSheet() },
                onClickRight = { viewModel.currentStep++ }
            )

            2 -> GenderScreen(
                viewModel = viewModel,
                onClickLeft = { viewModel.hideBottomSheet() },
                onClickRight = { viewModel.currentStep++ }
            )

            3 -> UnitScreen(
                viewModel = viewModel,
                onClickLeft = { viewModel.hideBottomSheet() },
                onClickRight = { viewModel.currentStep++ }
            )

            4 -> GoalScreen(
                viewModel = viewModel,
                onClickLeft = { viewModel.hideBottomSheet() },
                onClickRight = { viewModel.currentStep++ }
            )

            5 -> OftenScreen(
                viewModel = viewModel,
                onClickLeft = { viewModel.hideBottomSheet() },
                onClickRight = {
                    viewModel.hideBottomSheet()
                    viewModel.saveSetupProfile()
                }
            )
        }
    }
}