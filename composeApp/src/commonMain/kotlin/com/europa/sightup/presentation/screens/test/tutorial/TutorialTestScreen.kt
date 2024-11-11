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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.ModeSelectionCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSSwitchBoxContainer
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.StepProgressBar
import com.europa.sightup.presentation.designsystem.components.StepScreenWithAnimation
import com.europa.sightup.presentation.designsystem.components.StepScreenWithImage
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.SightUPLineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.navigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.left_eye
import sightupkmpapp.composeapp.generated.resources.right_eye
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
                    .padding(vertical = SightUPTheme.spacing.spacing_lg),
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
                style = SightUPTheme.textStyles.h2
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            AnimatedContent(targetState = currentStep) { targetState ->
                when (targetState) {
                    1 -> FirstStep(
                        selectedMode,
                        onModeSelected = {
                            tutorialViewModel.updateSelectedMode(it)
                        },
                        onClick = { tutorialViewModel.advanceStep() })

                    2 -> SecondStep(
                        animation =
                        if (test.title.contains("Astigmatism")) "files/distance_astigmatism.json"
                        else "files/distance_visual_acuity.json",
                        onClick = { tutorialViewModel.advanceStep() }
                    )

                    3 -> ThirdStep(
                        selectedMode = selectedMode,
                        test = test,
                        onClick = { tutorialViewModel.advanceStep() },
                        animation =
                        if (test.title.contains("Astigmatism")) selectedMode.astigmatismAnimation
                        else selectedMode.visualAcuityAnimation
                    )

                    4 -> FourthStep(
                        viewModel = tutorialViewModel,
                        navController = navController,
                        test = test,
                        selectedMode = selectedMode,
                        eyeTested = eyeTested,
                        animationPath =
                        if (eyeTested == "right") Res.drawable.right_eye
                        else Res.drawable.left_eye,
                        instructionText = if (eyeTested == "right") "Start with your right eye. If you wear glasses, please take them off and cover your left eye."
                        else "Now it's time to test your left eye. Please, cover your right eye."
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
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
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
                color = SightUPTheme.sightUPColors.text_primary,
                lineHeight = SightUPLineHeight.default.lineHeight_xs,
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
        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
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
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
    animation: String,
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
                animationPath = animation,
                instructionText = "Place yourself and your phone parallel and set the distance to 30 cm.",
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
            SDSSwitchBoxContainer(
                text = "audio support",
                isChecked = isChecked,
                onCheckedChanged = onCheckedChanged,
            )
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
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
    animation: String,
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
            animationPath = animation,
            instructionText = modeText,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
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
    viewModel: TutorialTestViewModel,
    navController: NavController,
    test: TestResponse,
    selectedMode: TestModeEnum,
    eyeTested: String,
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
    animationPath: DrawableResource,
    instructionText: String,
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StepScreenWithImage(
            image = animationPath,
            instructionText = instructionText,
        )
        // TODO: later change back to this
//        StepScreenWithAnimation(
//            animationPath = animationPath,
//            instructionText = instructionText,
//        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        SDSButton(
            text = "Start",
            onClick = {
                navController.navigate(
                    route = TestScreens.TestActive.toString(),
                    objectToSerialize = test,
                    objectToSerialize2 = selectedMode.displayName,
                    objectToSerialize3 = eyeTested,
                )
                scope.launch {
                    delay(500)
                    viewModel.updateEyeTested("left")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = SightUPTheme.spacing.spacing_base),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}
