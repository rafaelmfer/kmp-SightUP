package com.europa.sightup.presentation.screens.test

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.components.StepProgressBar
import com.europa.sightup.presentation.designsystem.components.Mode
import com.europa.sightup.presentation.designsystem.components.ModeSelectionCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.StepScreenWithAnimation
import com.europa.sightup.presentation.designsystem.components.SwitchAudio
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.lineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.test_mode_subtitle

@Composable
fun ExecutionTestScreen(
    navController: NavController,
    test: TestResponse,
) {
    val numberOfSteps = 4
    var currentStep by remember { mutableStateOf(1) }
    var selectedMode by remember { mutableStateOf(Mode.Touch) }

    val scrollState = rememberScrollState()
    // Reset scroll position whenever 'currentStep' changes
    LaunchedEffect(currentStep) {
        scrollState.animateScrollTo(0)
    }

    val titles = listOf("Select Mode", "Distance set-up", "How to answer", "Ready to test!")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
    ) {
        Column {
            if (currentStep > 1) {
                SDSTopBar(
                    title = test.title,
                    iconRight = Res.drawable.close,
                    iconRightVisible = true,
                    onRightButtonClick = { navController.navigate(TestScreens.TestRoot) },
                    iconLeftVisible = true,
                    onLeftButtonClick = { currentStep-- },
                    modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_md, bottom = SightUPTheme.spacing.spacing_lg)
                )
            } else {
                SDSTopBar(
                    title = test.title,
                    iconRight = Res.drawable.close,
                    iconRightVisible = true,
                    onRightButtonClick = { navController.navigate(TestScreens.TestRoot) },
                    modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_md, bottom = SightUPTheme.spacing.spacing_lg)
                )
            }

            StepProgressBar(
                numberOfSteps = numberOfSteps,
                currentStep = currentStep,
                modifier = Modifier.padding(bottom = SightUPTheme.spacing.spacing_md)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Heading text
            Text(
                text = titles[currentStep - 1],
                style = SightUPTheme.textStyles.h1
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            AnimatedContent(targetState = currentStep) { targetState ->
                when (targetState) {
                    1 -> FirstStep(
                        selectedMode,
                        onModeSelected = { selectedMode = it },
                        onClick = { currentStep++ })

                    2 -> SecondStep(
                        onClick = { currentStep++ }
                    )

                    3 -> ThirdStep(
                        selectedMode = selectedMode,
                        test = test,
                        onClick = { currentStep++ }
                    )

                    4 -> FourthStep(
                        onClick = { currentStep = 1 },
                    )
                }
            }
        }
    }
}

@Composable
fun FirstStep(
    selectedMode: Mode,
    onModeSelected: (Mode) -> Unit,
    onClick: () -> Unit,
) {
    val modes = listOf(Mode.Touch, Mode.Voice, Mode.SmartWatch)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.test_mode_subtitle),
                style = SightUPTheme.textStyles.body,
                lineHeight = SightUPTheme.lineHeight.lineHeight_md,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

            modes.forEach { mode ->
                ModeSelectionCard(
                    mode = mode,
                    isSelected = selectedMode == mode,
                    onClick = { onModeSelected(mode) }
                )
                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
            }
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
        SwitchAudio()
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        SDSButton(
            text = "Next",
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(bottom = SightUPTheme.spacing.spacing_base),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}

@Composable
fun SecondStep(
    onClick: () -> Unit,
) {
    var showCamera by remember { mutableStateOf(false) }

    if (showCamera) {
        SetDistanceScreen(onClick = onClick)
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            StepScreenWithAnimation(
                animationPath = "files/animation_delete_me.json",
                instructionText = "Place yourself and your phone parallel and set the distance to 30cm.",
                speed = 0.8f,
                backgroundColor = SightUPTheme.sightUPColors.neutral_100,
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
            SwitchAudio()
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

            SDSButton(
                text = "Set Distance",
                onClick = { showCamera = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SightUPTheme.spacing.spacing_base),
                textStyle = SightUPTheme.textStyles.button,
            )
        }
    }
}

@Composable
fun ThirdStep(
    test: TestResponse,
    selectedMode: Mode,
    onClick: () -> Unit,
) {
    val modeText = when (selectedMode) {
        Mode.Touch -> test.testMode.touch
        Mode.Voice -> test.testMode.voice
        Mode.SmartWatch -> test.testMode.smartwatch
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StepScreenWithAnimation(
            animationPath = "files/animation_delete_me.json",
            instructionText = modeText,
            speed = 0.8f,
            backgroundColor = SightUPTheme.sightUPColors.neutral_100,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
        SwitchAudio()
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        SDSButton(
            text = "Next",
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(bottom = SightUPTheme.spacing.spacing_base),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}

@Composable
fun FourthStep(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StepScreenWithAnimation(
            animationPath = "files/animation_delete_me.json",
            instructionText = "Start with your right eye. Take off your glasses and cover your left eye.",
            speed = 0.8f,
            backgroundColor = SightUPTheme.sightUPColors.neutral_100,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
        SwitchAudio()
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        SDSButton(
            text = "Start",
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(bottom = SightUPTheme.spacing.spacing_base),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}
