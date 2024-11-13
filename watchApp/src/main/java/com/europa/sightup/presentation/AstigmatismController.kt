package com.europa.sightup.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.foundation.rotary.RotaryScrollableDefaults
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.TimeText
import androidx.wear.compose.material3.VerticalPageIndicator
import androidx.wear.compose.material3.VerticalPagerScaffold
import androidx.wear.tooling.preview.devices.WearDevices
import com.europa.sightup.components.ButtonStyle
import com.europa.sightup.components.SDSButtonWear
import com.europa.sightup.components.SDSControlEyeClockWear
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.utils.WearMessageHelper

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ControlAstigmatismScreen() {
    val context = LocalContext.current
    val activity = LocalContext.current as WearMainActivity
    val wearMessageHelper = remember { WearMessageHelper(context) }

    val MESSAGE_PATH_ACTIONS = "/actions"

    val scope = rememberCoroutineScope()

    val pageCount = 2
    val pagerState = rememberPagerState { pageCount }
    var showTimeText by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }
            .collect { isScrolling ->
                showTimeText = isScrolling
            }
    }

    ScreenScaffold(
        timeText = {
            AnimatedVisibility(
                visible = showTimeText,
                enter = fadeIn(),
                exit = fadeOut(animationSpec = tween(durationMillis = 500, delayMillis = 1500))
            ) {
                TimeText { time() }
            }
        }
    ) {
        VerticalPagerScaffold(
            rotaryScrollableBehavior = RotaryScrollableDefaults.snapBehavior(pagerState),
            modifier = Modifier,
            pagerState = pagerState,
            pageIndicator = {
                VerticalPageIndicator(
                    modifier = Modifier,
                    pagerState = pagerState,
                    selectedColor = SightUPTheme.sightUPColors.primary_600,
                    unselectedColor = SightUPTheme.sightUPColors.neutral_500,
                    backgroundColor = Color.Transparent,
                )
            }
        ) { page ->

            when (page) {
                0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SDSControlEyeClockWear(
                            buttonOneOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 1.toString(), scope)
                                activity.show("Line One")
                            },
                            buttonTwoOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 2.toString(), scope)
                                activity.show("Line Two")
                            },
                            buttonThreeOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 3.toString(), scope)
                                activity.show("Line Three")
                            },
                            buttonFourOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 4.toString(), scope)
                                activity.show("Line Four")
                            },
                            buttonFiveOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 5.toString(), scope)
                                activity.show("Line Five")
                            },
                            buttonSixOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 6.toString(), scope)
                                activity.show("Line Six")
                            },
                            buttonSevenOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 7.toString(), scope)
                                activity.show("Line Seven")
                            },
                            buttonEightOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 8.toString(), scope)
                                activity.show("Line Eight")
                            },
                            buttonNineOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 9.toString(), scope)
                                activity.show("Line Nine")
                            },
                            buttonTenOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 10.toString(), scope)
                                activity.show("Line Ten")
                            },
                            buttonElevenOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 11.toString(), scope)
                                activity.show("Line Eleven")
                            },
                            buttonTwelveOnClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 12.toString(), scope)
                                activity.show("Line Twelve")
                            },
                        )

                        SDSButtonWear(
                            buttonStyle = ButtonStyle.OUTLINED,
                            text = "All lines same",
                            onClick = {
                                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "all lines", scope)
                                activity.show("All lines")
                            },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 40.dp),
                        )

                    }
                }

                1 -> {
                    ModesSelectorPage()
                }
            }
        }
    }
}
