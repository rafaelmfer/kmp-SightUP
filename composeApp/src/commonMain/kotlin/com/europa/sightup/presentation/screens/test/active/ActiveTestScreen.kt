package com.europa.sightup.presentation.screens.test.active

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.VisionTestTypes
import com.europa.sightup.platformspecific.createWearMessageReceiver
import com.europa.sightup.presentation.designsystem.components.AudioVisualizer
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.Countdown
import com.europa.sightup.presentation.designsystem.components.DistanceMessageCard
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSControlE
import com.europa.sightup.presentation.designsystem.components.SDSEyeClock
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.test.DistanceToCamera
import com.europa.sightup.presentation.screens.test.VoiceRecognition
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ActiveTestScreen(
    navController: NavController,
    test: TestResponse,
    testMode: String,
    eyeTested: String,
) {
    val viewModel = koinViewModel<ActiveTestViewModel>()

    LaunchedEffect(eyeTested) {
        viewModel.updateCurrentEye(eyeTested)
    }

    val voiceRecognition = koinInject<VoiceRecognition>()

    var testStarted by remember { mutableStateOf(false) }
    var currentMode by remember { mutableStateOf(testMode) }

    // Variables to store the distance user-camera
    val distanceState = remember { mutableStateOf("35") }
    val perfectRange = (distanceState.value.toFloatOrNull() ?: 0f) in 30f..40f

    val camera = DistanceToCamera(distance = distanceState, showCameraView = false)
    distanceState.value = camera.getDistanceToCamera.value
    val distance = distanceState.value.toFloatOrNull() ?: 0f

    // Move when the test has been completed move to the result screen
    val testState by viewModel.testState.collectAsState()

    LaunchedEffect(testState) {
        if (testState is ActiveTestViewModel.TestState.Completed) {
            val leftEyeResult = EyeTestRepository.leftEyeResult ?: ""
            val rightEyeResult = EyeTestRepository.rightEyeResult ?: ""

            navController.navigate(
                TestScreens.TestResult
                    (
                    appTest = true,
                    testId = test.taskId,
                    testTitle = test.title,
                    left = leftEyeResult,
                    right = rightEyeResult,
                )
            )
        }
    }

    Scaffold(
        topBar = {
            SDSTopBar(
                title = "",
                iconRight = Icons.Default.Close,
                iconRightVisible = true,
                onRightButtonClick = {
                    voiceRecognition.stopListening()
                    navController.navigate(TestScreens.TestRoot)
                },
                modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            )
        },
        bottomBar = {
            if (testStarted) {
                if (perfectRange) {
                    BottomModeBar(currentMode) { newMode -> currentMode = newMode }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SightUPTheme.sightUPColors.black.copy(0.3f))
                            .zIndex(10f)
                            .pointerInput(Unit) {}
                    ) {
                        BottomModeBar(currentMode) {   /* No-op to disable mode switching */ }
                    }
                }
            }
        },
        content = { paddingValues ->
            if (!testStarted) {
                CountdownScreen(onTestStart = { testStarted = true })
            } else {
                TestContent(
                    test = test,
                    currentMode = currentMode,
                    navController = navController,
                    perfectRange = perfectRange,
                    distance = distance,
                    viewModel = viewModel,
                    voiceRecognition = voiceRecognition,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}

@Composable
private fun TestContent(
    test: TestResponse,
    currentMode: String,
    navController: NavController,
    perfectRange: Boolean,
    distance: Float,
    viewModel: ActiveTestViewModel,
    voiceRecognition: VoiceRecognition,
    modifier: Modifier,
) {
    Box(
        modifier = Modifier.fillMaxSize().then(modifier),
        contentAlignment = Alignment.Center
    ) {
        // Warning sign if the device is not in the optimal range
        if (!perfectRange) {
            val distanceText = "You're ${distance.toInt()} cm away. Please move your device to the optimal range."
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(SightUPTheme.sightUPColors.black.copy(0.3f))
                    .zIndex(1f)
                    .pointerInput(Unit) {} // Block all touch interactions
            ) {
                DistanceMessageCard(
                    text = distanceText,
                    backgroundColor = SightUPTheme.sightUPColors.background_error,
                    textColor = SightUPTheme.sightUPColors.error_300,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        // Main content of each test
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SightUPTheme.colors.background)
                .zIndex(0f)
                .pointerInput(Unit) { // Prevent interaction if the overlay is active
                    if (!perfectRange) {
                        detectTapGestures {}
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Only to display the E-chart
            if (test.title.contains(VisionTestTypes.VisionAcuity.title)) {
                viewModel.currentEFormat?.let { VisualAcuityChart(it) }
            }

            // Controllers of the tests
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        if (test.title.contains(VisionTestTypes.Astigmatism.title))
                            SightUPTheme.sightUPColors.white
                        else SightUPTheme.sightUPColors.background_activate
                    )
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (test.title.contains(VisionTestTypes.Astigmatism.title))
                    Arrangement.SpaceEvenly else Arrangement.SpaceAround
            ) {

                LaunchedEffect(test) {
                    if (test.title.contains(VisionTestTypes.VisionAcuity.title)) {
                        viewModel.setActiveTest(ActiveTest.VisualAcuity)
                    } else if (test.title.contains(VisionTestTypes.Astigmatism.title)) {
                        viewModel.setActiveTest(ActiveTest.Astigmatism)
                    }
                }

                TestTypeContent(
                    test = test,
                    currentMode = currentMode,
                    voiceRecognition = voiceRecognition,
                    onClickChangeUI = { input ->
                        viewModel.onClickChangeUI(input, navController)
                    },
                    onTestButtonClick = {
                        viewModel.testButton(navController)
                    }
                )

                TestModeContent(
                    currentMode = currentMode,
                    onButtonClick = { viewModel.testButton(navController) },
                    textButton =
                    if (test.title.contains(VisionTestTypes.Astigmatism.title)) "All lines same" else "Cannot See"
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun VisualAcuityChart(currentEFormat: EChartIcon) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = currentEFormat,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 900, delayMillis = 100)) with
                        fadeOut(animationSpec = tween(durationMillis = 850, delayMillis = 0))
            }
        ) { icon ->
            Icon(
                painter = painterResource(resource = icon.resourceId),
                contentDescription = "Visual acuity chart",
                modifier = Modifier.graphicsLayer {
                    transformOrigin = TransformOrigin.Center
                }
            )
        }
    }
}

// This composable contains the controller with four buttons for the Visual Acuity Test
// and the circle with lines for the Astigmatism Test
@Composable
private fun TestTypeContent(
    test: TestResponse,
    currentMode: String,
    voiceRecognition: VoiceRecognition,
    onClickChangeUI: (Any) -> Unit,
    onTestButtonClick: () -> Unit,
) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    BindEffect(controller)

    val messageReceiver = remember { createWearMessageReceiver("/actions") }

    DisposableEffect(currentMode) {
        if (currentMode == TestModeEnum.SmartWatch.displayName) {
            when {
                test.title.contains(VisionTestTypes.VisionAcuity.title) -> {
                    messageReceiver.startListening { message ->
                        when (message) {
                            "up" -> onClickChangeUI(EChart.UP)
                            "down" -> onClickChangeUI(EChart.DOWN)
                            "left" -> onClickChangeUI(EChart.LEFT)
                            "right" -> onClickChangeUI(EChart.RIGHT)
                            "cannot see" -> onTestButtonClick()
                        }
                    }
                }

                test.title.contains(VisionTestTypes.Astigmatism.title) -> {
                    messageReceiver.startListening { message ->
                        val direction = message.toInt()
                        when (message) {
                            "1" -> {
                                onClickChangeUI(direction)
                            }

                            "2" -> {
                                onClickChangeUI(direction)
                            }

                            "3" -> {
                                onClickChangeUI(direction)
                            }

                            "4" -> {
                                onClickChangeUI(direction)
                            }

                            "5" -> {
                                onClickChangeUI(direction)
                            }

                            "6" -> {
                                onClickChangeUI(direction)
                            }

                            "7" -> {
                                onClickChangeUI(direction)
                            }

                            "8" -> {
                                onClickChangeUI(direction)
                            }

                            "9" -> {
                                onClickChangeUI(direction)
                            }

                            "10" -> {
                                onClickChangeUI(direction)
                            }

                            "11" -> {
                                onClickChangeUI(direction)
                            }

                            "12" -> {
                                onClickChangeUI(direction)
                            }

                            "all lines" -> onTestButtonClick()
                        }
                    }
                }
            }
        }

        onDispose {
            messageReceiver.stopListening()
        }
    }

    when {
        test.title.contains(VisionTestTypes.VisionAcuity.title) -> {
            SDSControlE(
                upButtonOnClickResult = { onClickChangeUI(EChart.UP) },
                leftButtonOnClickResult = { onClickChangeUI(EChart.LEFT) },
                rightButtonOnClickResult = { onClickChangeUI(EChart.RIGHT) },
                downButtonOnClickResult = { onClickChangeUI(EChart.DOWN) },
                modifier = Modifier.fillMaxWidth()
            )
            if (currentMode == TestModeEnum.Voice.displayName) {
                coroutineScope.launch {
                    controller.providePermission(Permission.RECORD_AUDIO)
                }
                voiceRecognition.startListening { spokenText ->
                    when {
                        spokenText.contains("down", ignoreCase = true) -> onClickChangeUI(EChart.DOWN)
                        spokenText.contains("left", ignoreCase = true) -> onClickChangeUI(EChart.LEFT)
                        spokenText.contains("right", ignoreCase = true) -> onClickChangeUI(EChart.RIGHT)
                        spokenText.contains("up", ignoreCase = true) -> onClickChangeUI(EChart.UP)
                        spokenText.contains("cannot see", ignoreCase = true) -> onTestButtonClick()
                    }
                }
            }
        }

        test.title.contains(VisionTestTypes.Astigmatism.title) -> {
            SDSEyeClock(
                buttonOneOnClick = { onClickChangeUI(1) },
                buttonTwoOnClick = { onClickChangeUI(2) },
                buttonThreeOnClick = { onClickChangeUI(3) },
                buttonFourOnClick = { onClickChangeUI(4) },
                buttonFiveOnClick = { onClickChangeUI(5) },
                buttonSixOnClick = { onClickChangeUI(6) },
                buttonSevenOnClick = { onClickChangeUI(7) },
                buttonEightOnClick = { onClickChangeUI(8) },
                buttonNineOnClick = { onClickChangeUI(9) },
                buttonTenOnClick = { onClickChangeUI(10) },
                buttonElevenOnClick = { onClickChangeUI(11) },
                buttonTwelveOnClick = { onClickChangeUI(12) }
            )

            if (currentMode == TestModeEnum.Voice.displayName) {
                coroutineScope.launch {
                    controller.providePermission(Permission.RECORD_AUDIO)
                }
                voiceRecognition.startListening { spokenText ->
                    when {
                        spokenText.contains("one", ignoreCase = true) -> {}
                        spokenText.contains("two", ignoreCase = true) -> {}
                        spokenText.contains("three", ignoreCase = true) -> {}
                        spokenText.contains("four", ignoreCase = true) -> {}
                        spokenText.contains("five", ignoreCase = true) -> {}
                        spokenText.contains("six", ignoreCase = true) -> {}
                        spokenText.contains("seven", ignoreCase = true) -> {}
                        spokenText.contains("eight", ignoreCase = true) -> {}
                        spokenText.contains("nine", ignoreCase = true) -> {}
                        spokenText.contains("ten", ignoreCase = true) || spokenText.contains("10", ignoreCase = true) -> {}
                        spokenText.contains("eleven", ignoreCase = true) || spokenText.contains(
                            "11",
                            ignoreCase = true
                        ) -> {
                        }

                        spokenText.contains("twelve", ignoreCase = true) || spokenText.contains(
                            "12",
                            ignoreCase = true
                        ) -> {
                        }

                        spokenText.contains("all lines", ignoreCase = true) -> onTestButtonClick()
                    }
                }
            }
        }
    }
}

// This composable show the right UI according to the test mode selected
@Composable
private fun TestModeContent(
    currentMode: String,
    onButtonClick: () -> Unit = {},
    textButton: String,
) {
    when (currentMode) {
        TestModeEnum.Touch.displayName,
        TestModeEnum.SmartWatch.displayName,
            -> {
            SDSButton(
                onClick = { onButtonClick() },
                text = textButton,
                buttonStyle = ButtonStyle.OUTLINED,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
            )
        }

        TestModeEnum.Voice.displayName -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = textButton,
                    style = SightUPTheme.textStyles.large,
                )
                AudioVisualizer(
                    modifier = Modifier.size(62.dp, 48.dp),
                    barMaxHeight = 46.dp,
                    barMinHeight = 12.dp,
                    barWidth = 6.dp,
                    barColor = SightUPTheme.sightUPColors.neutral_900
                )
            }
        }
    }
}

@Composable
private fun BottomModeBar(
    currentMode: String,
    onModeSelected: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = SightUPTheme.spacing.spacing_sm,
                horizontal = SightUPTheme.spacing.spacing_side_margin
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TestModeEnum.entries.forEach { mode ->
            val isSelected = mode.displayName == currentMode
            val iconColor = if (isSelected)
                SightUPTheme.sightUPColors.primary_600
            else SightUPTheme.sightUPColors.neutral_900

            IconButton(
                onClick = { onModeSelected(mode.displayName) },
            ) {
                Icon(
                    painter = painterResource(mode.iconResource),
                    contentDescription = mode.displayName,
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
private fun CountdownScreen(
    onTestStart: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Countdown(onCountdownFinished = { onTestStart() }, startsIn = 3)
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_2xl))
        Text(
            text = "Now let's get started!",
            textAlign = TextAlign.Center,
            style = SightUPTheme.textStyles.h3,
        )
    }
}