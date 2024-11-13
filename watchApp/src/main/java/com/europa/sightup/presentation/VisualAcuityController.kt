package com.europa.sightup.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.europa.sightup.components.SDSControlEWear
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.WearMessageHelper

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun VisualAcuityScreen() {
    val context = LocalContext.current
    val wearMessageHelper = remember { WearMessageHelper(context) }
    val scope = rememberCoroutineScope()

    val MESSAGE_PATH_ACTIONS = "/actions"

    val pageCount = 2
    val pagerState = rememberPagerState { pageCount }
    var showTimeText by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }
            .collect { isScrolling ->
                showTimeText = isScrolling
            }
    }

    val scrollState = rememberScrollState()
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .background(SightUPTheme.sightUPColors.primary_200),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SDSControlEWear(
                            upButtonOnClickResult = { wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "up", scope) },
                            downButtonOnClickResult = {
                                wearMessageHelper.sendWearMessage(
                                    MESSAGE_PATH_ACTIONS,
                                    "down",
                                    scope
                                )
                            },
                            leftButtonOnClickResult = {
                                wearMessageHelper.sendWearMessage(
                                    MESSAGE_PATH_ACTIONS,
                                    "left",
                                    scope
                                )
                            },
                            rightButtonOnClickResult = {
                                wearMessageHelper.sendWearMessage(
                                    MESSAGE_PATH_ACTIONS,
                                    "right",
                                    scope
                                )
                            },
                        )
                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
                        SDSButtonWear(
                            text = "Cannot See",
                            buttonStyle = ButtonStyle.OUTLINED,
                            onClick = { wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "cannot see", scope) },
                            modifier = Modifier
                                .defaultMinSize(minHeight = 32.dp),
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