package com.europa.sightup.presentation.screens.test

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.europa.sightup.presentation.components.TitleBar
import com.europa.sightup.presentation.designsystem.components.Mode
import com.europa.sightup.presentation.designsystem.components.ModeSelectionCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.StepScreenWithAnimation
import com.europa.sightup.presentation.designsystem.components.SwitchAudio
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.test_mode_subtitle

@Composable
fun ExecutionTestScreen(
    navController: NavController,
    test: TestResponse,
    ) {
    var currentStep by remember { mutableStateOf(1) }
    var selectedMode by remember { mutableStateOf(Mode.Touch) }

    val scrollState = rememberScrollState()
    // Reset scroll position whenever 'currentStep' changes
    LaunchedEffect(currentStep) {
        scrollState.animateScrollTo(0)
    }

    val numberOfSteps = 4

    val titles = listOf("Select Mode", "Distance set-up", "How to answer", "Ready to test!")
    val btnText = listOf("Next", "Set Distance", "Next" , "Start")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
        ){
                Column {
                    if (currentStep > 1) {
                        TitleBar(
                            title = test.title,
                            rightIcon = Icons.Default.Close,
                            rightButton = true,
                            onRightButtonClick = { navController.navigate(TestScreens.TestRoot) },
                            leftIcon = Icons.Default.ArrowBack,
                            leftButton = true,
                            onLeftButtonClick = { currentStep-- },
                            modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_md, bottom = SightUPTheme.spacing.spacing_lg)
                        )
                    } else {
                        TitleBar(
                            title = test.title,
                            rightIcon = Icons.Default.Close,
                            rightButton = true,
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

                    AnimatedContent(targetState = currentStep) {
                            targetState ->
                        when (targetState) {
                            1 -> FirstStep(selectedMode, onModeSelected = { selectedMode = it })
                            2 -> SecondStep()
                            3 -> ThirdStep(selectedMode = selectedMode, test = test)
                            4 -> FourthStep()
                        }
                    }

                    Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xl))
                    SwitchAudio()

                    Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xl))
                    SDSButton(
                        text = btnText[currentStep - 1],
                        onClick = {
                            // Increment the current step, looping back to 1 after reaching the max
                            currentStep = if (currentStep < numberOfSteps) currentStep + 1 else 1
                        },
                        modifier = Modifier.fillMaxWidth().padding(bottom = SightUPTheme.spacing.spacing_base),
                        textStyle = SightUPTheme.textStyles.button,
                    )
                }
            }
        }

@Composable
fun FirstStep(
    selectedMode: Mode,
    onModeSelected: (Mode) -> Unit
) {
    val modes = listOf(Mode.Touch, Mode.Voice, Mode.SmartWatch)

    Column {
        Text(
            text = stringResource(Res.string.test_mode_subtitle),
            style = SightUPTheme.textStyles.body,
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
}

@Composable
fun SecondStep(){
    StepScreenWithAnimation(
        animationPath = "drawable/Animation-DELETE_ME.json",
        instructionText = "Place yourself and your phone parallel and set the distance to 30cm.",
        speed = 0.8f,
        backgroundColor = SightUPTheme.sightUPColors.neutral_100

    )
}

@Composable
fun ThirdStep(test: TestResponse, selectedMode: Mode) {
    val modeText = when (selectedMode) {
        Mode.Touch -> test.testMode.touch
        Mode.Voice -> test.testMode.voice
        Mode.SmartWatch -> test.testMode.smartwatch
    }

    StepScreenWithAnimation(
        animationPath = "drawable/Animation-DELETE_ME.json",
        instructionText = modeText,
        speed = 0.8f,
        backgroundColor = SightUPTheme.sightUPColors.neutral_100
    )
}

@Composable
fun FourthStep(){
    StepScreenWithAnimation(
        animationPath = "drawable/Animation-DELETE_ME.json",
        instructionText = "Start with your right eye. Take off your glasses and cover your left eye.",
        speed = 0.8f,
        backgroundColor = SightUPTheme.sightUPColors.neutral_100
    )
}
