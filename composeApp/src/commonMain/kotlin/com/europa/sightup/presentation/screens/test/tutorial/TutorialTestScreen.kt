package com.europa.sightup.presentation.screens.test.tutorial

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerView
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.platformspecific.audioplayer.CMPAudioPlayer
import com.europa.sightup.platformspecific.getLocalFilePathFor
import com.europa.sightup.platformspecific.getPlatform
import com.europa.sightup.presentation.designsystem.components.CardWithTestInstructions
import com.europa.sightup.presentation.designsystem.components.ModeSelectionCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSDialog
import com.europa.sightup.presentation.designsystem.components.SDSSwitchBoxContainer
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.StepProgressBar
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.test.active.ActiveTest
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.SightUPLineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ANDROID
import com.europa.sightup.utils.IOS
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.navigate
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close
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

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(currentStep) {
        scrollState.animateScrollTo(0)
    }

    val titles = listOf("Select Mode", "Distance set-up", "How to answer", "Ready to test!")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            SDSTopBar(
                modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_xs),
                title = test.title,
                iconRight = Res.drawable.close,
                iconRightVisible = true,
                onRightButtonClick = {
                    showDialog = true
                },
                iconLeftVisible = currentStep > 1,
                onLeftButtonClick = { tutorialViewModel.goBackStep() }
            )

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
                .padding(bottom = if (getPlatform().name == IOS) SightUPTheme.spacing.spacing_sm else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                        title = titles[currentStep - 1],
                        selectedMode = selectedMode,
                        onModeSelected = {
                            tutorialViewModel.updateSelectedMode(it)
                        },
                        onClick = { tutorialViewModel.advanceStep() },
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked }
                    )

                    2 -> SecondStep(
                        title = titles[currentStep - 1],
                        animation =
                        if (test.title.contains(ActiveTest.Astigmatism.name)) ActiveTest.Astigmatism.distanceLottie
                        else ActiveTest.VisualAcuity.distanceLottie,
                        onClick = { tutorialViewModel.advanceStep() },
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked }
                    )

                    3 -> ThirdStep(
                        title = titles[currentStep - 1],
                        selectedMode = selectedMode,
                        test = test,
                        onClick = { tutorialViewModel.advanceStep() },
                        isChecked = isAudioPaused,
                        onCheckedChanged = { checked -> isAudioPaused = checked },
                        video = when (selectedMode) {
                            TestModeEnum.Touch -> test.videoMode.touch
                            TestModeEnum.Voice -> test.videoMode.voice
                            TestModeEnum.SmartWatch -> test.videoMode.smartwatch
                        }
                    )

                    4 -> FourthStep(
                        title = titles[currentStep - 1],
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

    SDSDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = it },
        title = "Are you sure you want to exit?",
        onClose = null,
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_md)
            ) {
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                Text(
                    text = "If you cancel, you’ll need to restart the test.",
                    style = SightUPTheme.textStyles.body,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            }
        },
        onPrimaryClick = { showDialog = false },
        buttonPrimaryText = "Continue",
        onSecondaryClick = {
            navController.popBackStack<TestScreens.TestRoot>(inclusive = false)
        },
        buttonSecondaryText = "End",
    )
}

@Composable
private fun FirstStep(
    title: String,
    selectedMode: TestModeEnum,
    onModeSelected: (TestModeEnum) -> Unit,
    onClick: () -> Unit,
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
) {
    val modes = listOf(TestModeEnum.Touch, TestModeEnum.Voice, TestModeEnum.SmartWatch)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = SightUPTheme.textStyles.h2
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

        Text(
            text = stringResource(Res.string.test_mode_subtitle),
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            lineHeight = SightUPLineHeight.default.lineHeight_xs,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        modes.forEach { mode ->
            ModeSelectionCard(
                mode = mode,
                isSelected = selectedMode == mode,
                onClick = { onModeSelected(mode) }
            )
            Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        }

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        Spacer(Modifier.weight(ONE_FLOAT))
        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        SDSButton(
            text = "Next",
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = SightUPTheme.spacing.spacing_md),
            textStyle = SightUPTheme.textStyles.button,
        )
    }

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
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SecondStep(
    title: String = "",
    onClick: () -> Unit,
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
    animation: String,
) {
    var showCamera by remember { mutableStateOf(false) }
    var hideLoading by remember { mutableStateOf(false) }

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animation).decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 1.2f,
        iterations = Compottie.IterateForever,
    )

    LaunchedEffect(composition) {
        delay(500L)
        hideLoading = true
    }

    if (showCamera) {
        SetDistanceScreen(onClick = onClick)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = SightUPTheme.textStyles.h2,
                color = SightUPTheme.sightUPColors.text_primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SightUPTheme.sightUPColors.background_default),
                contentAlignment = Alignment.Center
            ) {
                if (getPlatform().name == ANDROID) {
                    androidx.compose.animation.AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth(),
                        visible = hideLoading,
                        enter = fadeIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically),
                    ) {
                        Image(
                            painter = rememberLottiePainter(
                                composition = composition,
                                progress = { progress },
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    if (!hideLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(vertical = 120.dp)
                                    .size(SightUPTheme.sizes.size_56)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                } else {
                    Image(
                        painter = rememberLottiePainter(
                            composition = composition,
                            progress = { progress },
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
            CardWithTestInstructions(
                text = stringResource(Res.string.test_distance_instruction)
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
            Spacer(modifier = Modifier.weight(ONE_FLOAT))
            SDSSwitchBoxContainer(
                text = "audio support",
                isChecked = isChecked,
                onCheckedChanged = onCheckedChanged,
            )
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))
            SDSButton(
                text = "Set Distance",
                onClick = { showCamera = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SightUPTheme.spacing.spacing_md),
                textStyle = SightUPTheme.textStyles.button,
            )
        }

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
    }
}

@Composable
private fun ThirdStep(
    title: String = "",
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
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = SightUPTheme.textStyles.h2,
            color = SightUPTheme.sightUPColors.text_primary,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(SightUPTheme.sightUPColors.background_default),
            contentAlignment = Alignment.Center
        ) {
            VideoPlayerView(
                modifier = Modifier
                    .fillMaxSize(),
                url = getLocalFilePathFor(video),
                playerConfig = PlayerConfig(
                    isPauseResumeEnabled = false,
                    isSeekBarVisible = false,
                    isDurationVisible = false,
                    isAutoHideControlEnabled = true,
                    isFastForwardBackwardEnabled = false,
                    isMuteControlEnabled = false,
                    isSpeedControlEnabled = false,
                    isFullScreenEnabled = false,
                    isScreenLockEnabled = false,
                    isMute = !isChecked,
                    isPause = false,
                    isScreenResizeEnabled = false,
                    loop = true,
                    videoFitMode = ScreenResize.FILL,
                    loaderView = {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .defaultMinSize(minHeight = 300.dp)
//                                .background(SightUPTheme.sightUPColors.background_default),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            CircularProgressIndicator()
//                        }
                    },
                )
            )
        }

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        CardWithTestInstructions(text = modeText)

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
        Spacer(Modifier.weight(ONE_FLOAT))

        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        SDSButton(
            text = "Next",
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = SightUPTheme.spacing.spacing_md),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}

@Composable
private fun FourthStep(
    title: String = "",
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
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = SightUPTheme.textStyles.h2,
            color = SightUPTheme.sightUPColors.text_primary,
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .background(SightUPTheme.sightUPColors.background_default),
            contentAlignment = Alignment.Center
        ) {
            VideoPlayerView(
                modifier = Modifier
                    .fillMaxSize(),
                url = getLocalFilePathFor(video),
                playerConfig = PlayerConfig(
                    isPauseResumeEnabled = false,
                    isSeekBarVisible = false,
                    isDurationVisible = false,
                    isAutoHideControlEnabled = true,
                    isFastForwardBackwardEnabled = false,
                    isMuteControlEnabled = false,
                    isSpeedControlEnabled = false,
                    isFullScreenEnabled = false,
                    isScreenLockEnabled = false,
                    isMute = !isChecked,
                    isPause = false,
                    isScreenResizeEnabled = false,
                    loop = true,
                    videoFitMode = ScreenResize.FILL,
                    loaderView = {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .defaultMinSize(minHeight = 300.dp)
//                                .background(SightUPTheme.sightUPColors.background_default),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            CircularProgressIndicator()
//                        }
                    },
                )
            )
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        CardWithTestInstructions(text = instructionText)

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
        Spacer(Modifier.weight(ONE_FLOAT))

        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
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
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = SightUPTheme.spacing.spacing_md),
            textStyle = SightUPTheme.textStyles.button,
        )
    }
}