package com.europa.sightup.presentation.screens.test.tutorial

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.europa.sightup.presentation.designsystem.components.ModeSelectionCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.StepScreenWithAnimation
import com.europa.sightup.presentation.designsystem.components.SwitchAudio
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.lineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.navigate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.test_mode_subtitle

@Composable
fun TutorialTestScreen(
    navController: NavController,
    test: TestResponse,
) {
    val tutorialViewModel = koinViewModel<TutorialTestViewModel>()

    val currentStep = tutorialViewModel.currentStep
    val eyeTested = tutorialViewModel.eyeTested
    val selectedMode = tutorialViewModel.selectedMode

    val numberOfSteps = 4
    val scrollState = rememberScrollState()

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
                    iconRight = Icons.Default.Close,
                    iconRightVisible = true,
                    onRightButtonClick = { navController.navigate(TestScreens.TestRoot) },
                    iconLeftVisible = true,
                    onLeftButtonClick = { tutorialViewModel.goBackStep() },
                )
            } else {
                SDSTopBar(
                    title = test.title,
                    iconRight = Icons.Default.Close,
                    iconRightVisible = true,
                    onRightButtonClick = {
                        navController.navigate(TestScreens.TestRoot)
                    },
                )
            }

            StepProgressBar(
                numberOfSteps = numberOfSteps,
                currentStep = currentStep,
                modifier = Modifier
                    .padding(bottom = SightUPTheme.spacing.spacing_base, top = SightUPTheme.spacing.spacing_base),
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
                        onModeSelected = {
                            //selectedMode = it
                            tutorialViewModel.updateSelectedMode(it)
                        },
                        onClick = { tutorialViewModel.advanceStep() })

                    2 -> SecondStep(
                        onClick = { tutorialViewModel.advanceStep() }
                    )

                    3 -> ThirdStep(
                        selectedMode = selectedMode,
                        test = test,
                        onClick = { tutorialViewModel.advanceStep() }
                    )

                    4 -> FourthStep(
                        navController = navController,
                        test = test,
                        selectedMode = selectedMode,
                        eyeTested = eyeTested,
                        onEyeTestedChange = { tutorialViewModel.updateEyeTested(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FirstStep(
    selectedMode: TestModeEnum,
    onModeSelected: (TestModeEnum) -> Unit,
    onClick: () -> Unit,
) {
    val modes = listOf(TestModeEnum.Touch, TestModeEnum.Voice, TestModeEnum.SmartWatch)

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
private fun SecondStep(
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
                instructionText = "Place yourself and your phone parallel and set the distance to 30 cm.",
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
private fun ThirdStep(
    test: TestResponse,
    selectedMode: TestModeEnum,
    onClick: () -> Unit,
) {
    val modeText = when (selectedMode) {
        TestModeEnum.Touch -> test.testMode.touch
        TestModeEnum.Voice -> test.testMode.voice
        TestModeEnum.SmartWatch -> test.testMode.smartwatch
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
private fun FourthStep(
    navController: NavController,
    test: TestResponse,
    selectedMode: TestModeEnum,
    eyeTested: String,
    onEyeTestedChange: (String) -> Unit,
) {
    val (animationPath, instructionText) = if (eyeTested == "right") {
        "files/animation_delete_me.json" to "Start with your right eye. If you wear glasses, please take them off and cover your left eye."
    } else {
        "files/animation_delete_me.json" to "Now it's time to test your left eye. Please, cover your right eye."
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StepScreenWithAnimation(
            animationPath = animationPath,
            instructionText = instructionText,
            speed = 0.8f,
            backgroundColor = SightUPTheme.sightUPColors.neutral_100,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
        SwitchAudio()
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        SDSButton(
            text = "Start",
            onClick = {
                val nextEye = if (eyeTested == "right") "left" else "right"
                onEyeTestedChange(nextEye)
                navController.navigate(
                    route = TestScreens.TestActive.toString(),
                    objectToSerialize = test,
                    objectToSerialize2 = selectedMode.displayName,
                    objectToSerialize3 = eyeTested
                )
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = SightUPTheme.spacing.spacing_base),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}
