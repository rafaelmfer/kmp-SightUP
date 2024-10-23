package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActiveTestScreen(
    navController: NavController,
    test: TestResponse,
    testMode: String,
) {
    var testStarted by remember { mutableStateOf(false) }
    var currentMode by remember { mutableStateOf(testMode) }

    Scaffold(
        topBar = {
            SDSTopBar(
                title = " ",
                iconRight = Icons.Default.Close,
                iconRightVisible = true,
                onRightButtonClick = { navController.navigate(TestScreens.TestRoot) },
            )
        },
        bottomBar = {
            if (testStarted) {
                BottomBar(currentMode) { newMode -> currentMode = newMode }
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
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}

@Composable
fun TestContent(
    test: TestResponse,
    currentMode: String,
    navController: NavController,
    modifier: Modifier,
) {
    val distanceState = remember { mutableStateOf("35") }
    val perfectRange = (distanceState.value.toFloatOrNull() ?: 0f) in 30f..40f

    val camera = DistanceToCamera(distance = distanceState, aspectRatio = 3f / 4f, showCameraView = false)
    distanceState.value = camera.getDistanceToCamera.value
    val distance = distanceState.value.toFloatOrNull() ?: 0f

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        // TODO: create like a dialog to cover the whole screen with the warning text
        if (!perfectRange) {
            val distanceText = "${distance.toInt()} cm \n Please move your device until the distance is within range"
            DistanceMessageCard(text = distanceText, backgroundColor = SightUPTheme.sightUPColors.error_300, modifier = Modifier.zIndex(10f))
        }

        Column(
            modifier = Modifier
                .background(SightUPTheme.colors.background)
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (test.title.contains(VisionTestTypes.VisionAcuity.title)) {TestHeader(test)}

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = SightUPTheme.sightUPColors.neutral_100)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TestTypeContent(test)

                TestModeContent(
                    currentMode = currentMode,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun TestHeader(test: TestResponse) {
    Box(
        modifier = Modifier.fillMaxWidth().height(100.dp),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 36.dp),
            text = "Chart test for ${test.title}"
        )
    }
}

@Composable
fun TestTypeContent(test: TestResponse) {
    when  {
        test.title.contains(VisionTestTypes.VisionAcuity.title) -> {
            SDSControlE(
                upButtonOnClickResult = { showToast("Up button clicked", bottomPadding = 40) },
                leftButtonOnClickResult = { showToast("Left button clicked", bottomPadding = 40) },
                rightButtonOnClickResult = { showToast("Right button clicked", bottomPadding = 40) },
                downButtonOnClickResult = { showToast("Down button clicked", bottomPadding = 40) },
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
fun TestModeContent(
    currentMode: String,
    navController: NavController,
) {
    when (currentMode) {
        TestModeEnum.Touch.displayName -> {
            SDSButton(
                onClick = { navController.popBackStack() },
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
                onClick = { navController.popBackStack() },
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
fun BottomBar(
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
fun CountdownScreen(
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



