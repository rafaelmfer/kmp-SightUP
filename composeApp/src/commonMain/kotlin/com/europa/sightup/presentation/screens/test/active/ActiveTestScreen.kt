package com.europa.sightup.presentation.screens.test.active

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.VisionTestTypes
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
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
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

    var testStarted by remember { mutableStateOf(false) }
    var currentMode by remember { mutableStateOf(testMode) }


    // Distance to camera variables
    val distanceState = remember { mutableStateOf("35") }
    val perfectRange = (distanceState.value.toFloatOrNull() ?: 0f) in 30f..40f

    val camera = DistanceToCamera(distance = distanceState, aspectRatio = 3f / 4f, showCameraView = false)
    distanceState.value = camera.getDistanceToCamera.value
    val distance = distanceState.value.toFloatOrNull() ?: 0f

    Scaffold(
        topBar = {
            SDSTopBar(
                title = "",
                iconRight = Icons.Default.Close,
                iconRightVisible = true,
                onRightButtonClick = { navController.navigate(TestScreens.TestRoot) },
            )
        },
        bottomBar = {
            if (testStarted) {
                if (perfectRange) {
                    BottomBar(currentMode) { newMode -> currentMode = newMode }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SightUPTheme.sightUPColors.black.copy(0.3f))
                            .zIndex(10f)
                            .pointerInput(Unit) {}
                    ) {
                        BottomBar(currentMode) {   /* No-op to disable mode switching */ }
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
    modifier: Modifier,
) {

    Box(
        modifier = Modifier.fillMaxSize().then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (!perfectRange) {
            val distanceText = "You're ${distance.toInt()} cm away. \n Please move your device to the optimal range."
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
                    backgroundColor = SightUPTheme.sightUPColors.error_300,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        // Main content
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
            if (test.title.contains(VisionTestTypes.VisionAcuity.title)) {
                viewModel.currentEFormat?.let { TestHeader(it) }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(SightUPTheme.sightUPColors.neutral_100)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TestTypeContent(
                    test = test,
                    onClickChangeUI = { selectedDirection ->
                        viewModel.checkAnswerAndChangeE(selectedDirection, navController)
                    }
                )

                TestModeContent(
                    currentMode = currentMode,
                    onButtonClick = { viewModel.cannotSeeButton(navController) }
                )
            }
        }
    }
}

@Composable
private fun TestHeader(currentEFormat: DrawableResource) {
    Box(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(currentEFormat),
            contentDescription = "Visual acuity chart",
            modifier = Modifier.size(50.dp),
        )
    }
}

@Composable
private fun TestTypeContent(
    test: TestResponse,
    onClickChangeUI: (EChart) -> Unit,
) {
    when {
        test.title.contains(VisionTestTypes.VisionAcuity.title) -> {
            SDSControlE(
                upButtonOnClickResult = { onClickChangeUI(EChart.UP) },
                leftButtonOnClickResult = { onClickChangeUI(EChart.LEFT) },
                rightButtonOnClickResult = { onClickChangeUI(EChart.RIGHT) },
                downButtonOnClickResult = { onClickChangeUI(EChart.DOWN) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        test.title.contains(VisionTestTypes.Astigmatism.title) -> {
            SDSEyeClock(
                buttonOneOnClick = {
                    showToast(
                        "Button One Clicked",
                        bottomPadding = 40
                    )
                },
                buttonTwoOnClick = {
                    showToast(
                        "Button Two Clicked",
                        bottomPadding = 40
                    )
                },
                buttonThreeOnClick = {
                    showToast(
                        "Button Three Clicked",
                        bottomPadding = 40
                    )
                },
                buttonFourOnClick = {
                    showToast(
                        "Button Four Clicked",
                        bottomPadding = 40
                    )
                },
                buttonFiveOnClick = {
                    showToast(
                        "Button Five Clicked",
                        bottomPadding = 40
                    )
                },
                buttonSixOnClick = {
                    showToast(
                        "Button Six Clicked",
                        bottomPadding = 40
                    )
                },
                buttonSevenOnClick = {
                    showToast(
                        "Button Seven Clicked",
                        bottomPadding = 40
                    )
                },
                buttonEightOnClick = {
                    showToast(
                        "Button Eight Clicked",
                        bottomPadding = 40
                    )
                },
                buttonNineOnClick = {
                    showToast(
                        "Button Nine Clicked",
                        bottomPadding = 40
                    )
                },
                buttonTenOnClick = {
                    showToast(
                        "Button Ten Clicked",
                        bottomPadding = 40
                    )
                },
                buttonElevenOnClick = {
                    showToast(
                        "Button Eleven Clicked",
                        bottomPadding = 40
                    )
                },
                buttonTwelveOnClick = {
                    showToast(
                        "Button Twelve Clicked",
                        bottomPadding = 40
                    )
                })
        }
    }
}

@Composable
private fun TestModeContent(
    currentMode: String,
    onButtonClick: () -> Unit = {},
) {
    when (currentMode) {
        TestModeEnum.Touch.displayName -> {
            SDSButton(
                onClick = { onButtonClick() },
                text = "Cannot See",
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
                    text = "\"Cannot see\"",
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

        TestModeEnum.SmartWatch.displayName -> {
            SDSButton(
                onClick = { onButtonClick() },
                text = "Cannot See",
                buttonStyle = ButtonStyle.OUTLINED,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
            )
        }
    }
}

@Composable
private fun BottomBar(
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
