package com.europa.sightup.presentation.screens.test.tutorial

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.platformspecific.audioplayer.CMPAudioPlayer
import com.europa.sightup.platformspecific.getLocalFilePathFor
import com.europa.sightup.presentation.designsystem.components.ModeSelectionCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSSwitchBoxContainer
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.StepProgressBar
import com.europa.sightup.presentation.designsystem.components.StepScreenWithAnimation
import com.europa.sightup.presentation.designsystem.components.StepScreenWithVideo
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.test.active.ActiveTest
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.SightUPLineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.navigate
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.test_distance_instruction
import sightupkmpapp.composeapp.generated.resources.test_left_eye
import sightupkmpapp.composeapp.generated.resources.test_mode_subtitle
import sightupkmpapp.composeapp.generated.resources.test_right_eye

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

    var isAudioPaused by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(currentStep) {
        scrollState.animateScrollTo(0)
    }

    val titles = listOf("Select Mode", "Distance set-up", "How to answer", "Ready to test!")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            if (currentStep > 1) {
                SDSTopBar(
                    modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_xs),
                    title = test.title,
                    iconRight = Icons.Default.Close,
                    iconRightVisible = true,
                    onRightButtonClick = { navController.popBackStack<TestScreens.TestRoot>(inclusive = false) },
                    iconLeftVisible = true,
                    onLeftButtonClick = { tutorialViewModel.goBackStep() },
                )
            } else {
                SDSTopBar(
                    modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_xs),
                    title = test.title,
                    iconRight = Icons.Default.Close,
                    iconRightVisible = true,
                    onRightButtonClick = {
                        navController.popBackStack<TestScreens.TestRoot>(inclusive = false)
                    },
                )
            }

            StepProgressBar(
                numberOfSteps = numberOfSteps,
                currentStep = currentStep,
                modifier = Modifier
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    .padding(top = SightUPTheme.spacing.spacing_md, bottom = SightUPTheme.spacing.spacing_lg),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Heading text
            Text(
                text = titles[currentStep - 1],
                style = SightUPTheme.textStyles.h2
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInFromRight() togetherWith slideOutToLeft()
                    } else {
                        slideInFromLeft() togetherWith slideOutToRight()
                    }
                }
            ) { targetState ->
                when (targetState) {
                    1 -> FirstStep(
                        selectedMode,
                        onModeSelected = {
                            tutorialViewModel.updateSelectedMode(it)
                        },
                        onClick = { tutorialViewModel.advanceStep() },
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked }
                    )

                    2 -> SecondStep(
                        animation =
                        if (test.title.contains(ActiveTest.Astigmatism.name)) ActiveTest.Astigmatism.distanceLottie
                        else ActiveTest.VisualAcuity.distanceLottie,
                        onClick = { tutorialViewModel.advanceStep() },
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked }
                    )

                    3 -> ThirdStep(
                        selectedMode = selectedMode,
                        test = test,
                        onClick = { tutorialViewModel.advanceStep() },
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked },
                        video = when (selectedMode) {
                            TestModeEnum.Touch -> test.videoMode.touch.replace("test_videos/", "test_videos%2F")
                            TestModeEnum.Voice -> test.videoMode.voice.replace("test_videos/", "test_videos%2F")
                            TestModeEnum.SmartWatch -> test.videoMode.smartwatch.replace("test_videos/", "test_videos%2F")
                        }
                    )

                    4 -> FourthStep(
                        viewModel = tutorialViewModel,
                        navController = navController,
                        test = test,
                        selectedMode = selectedMode,
                        eyeTested = eyeTested,
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked },
                        video =
                        if (eyeTested == "right") test.videoEyes.right.replace("test_videos/", "test_videos%2F")
                        else test.videoEyes.left.replace("test_videos/", "test_videos%2F"),
                        instructionText = if (eyeTested == "right") stringResource(Res.string.test_right_eye)
                        else stringResource(Res.string.test_left_eye)
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
                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
            }
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
        CMPAudioPlayer(
            modifier = Modifier.height(0.dp),
            url = getLocalFilePathFor("select_test_mode.m4a"),
            isPause = !isChecked,
            totalTime = { },
            currentTime = { },
            isSliding = false,
            sliderTime = null,
            isRepeat = true,
            loadingState = { },
            didEndAudio = { }
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

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
                instructionText = stringResource(Res.string.test_distance_instruction),
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
            SDSSwitchBoxContainer(
                text = "audio support",
                isChecked = isChecked,
                onCheckedChanged = onCheckedChanged,
            )

            CMPAudioPlayer(
                modifier = Modifier.height(0.dp),
                url = getLocalFilePathFor("test_distance.m4a"),
                isPause = !isChecked,
                totalTime = { },
                currentTime = { },
                isSliding = false,
                sliderTime = null,
                isRepeat = true,
                loadingState = { },
                didEndAudio = { }
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

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
    video: String,
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
        StepScreenWithVideo(
            videoPath = video,
            muteVideo = !isChecked,
            instructionText = modeText,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

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
    video: String,
    instructionText: String,
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StepScreenWithVideo(
            videoPath = video,
            muteVideo = !isChecked,
            instructionText = instructionText
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

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
