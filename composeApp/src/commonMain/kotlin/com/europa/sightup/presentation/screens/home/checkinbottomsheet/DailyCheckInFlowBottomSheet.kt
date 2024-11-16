package com.europa.sightup.presentation.screens.home.checkinbottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.europa.sightup.data.remote.request.assessment.DailyCheckInfoRequest
import com.europa.sightup.data.remote.request.assessment.DailyCheckRequest
import com.europa.sightup.platformspecific.getScreenSizeInInches
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.StepProgressBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.Moods
import com.europa.sightup.utils.applyIf
import com.europa.sightup.utils.getTodayDateString
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close

@Composable
fun DailyCheckInFlowBottomSheet(
    onCloseIconTopBar: () -> Unit,
    onComplete: (DailyCheckRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    var iconLeftVisible by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(1) }

    var visionStatus by remember { mutableStateOf(Moods.MODERATE.value) }
    val conditions = remember { mutableStateListOf<String>() }
    val causes = remember { mutableStateListOf<String>() }

    val scrollState = rememberScrollState()
    val screenSizeInInches = getScreenSizeInInches()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(SightUPTheme.sizes.size_12))
        SDSTopBar(
            modifier = Modifier
                .padding(horizontal = SightUPTheme.spacing.spacing_xs),
            title = "Daily Check-In",
            iconLeftVisible = iconLeftVisible,
            onLeftButtonClick = {
                if (currentStep > 1) {
                    currentStep--
                    if (currentStep == 1) {
                        iconLeftVisible = false
                    }
                }
            },
            iconRightVisible = true,
            iconRight = Res.drawable.close,
            onRightButtonClick = onCloseIconTopBar
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        StepProgressBar(
            modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
            numberOfSteps = 3,
            currentStep = currentStep
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .applyIf(screenSizeInInches < 5.0f) {
                    verticalScroll(scrollState)
                }
        ) {
            when (currentStep) {
                1 -> {
                    ScreenOneVisionStatus(
                        onClickNext = {
                            visionStatus = it
                            iconLeftVisible = true
                            currentStep = 2
                        },
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    )
                }

                2 -> {
                    ScreenTwoConditions(
                        onClickNext = { conditionsChosen ->
                            conditions.clear()
                            conditions.addAll(conditionsChosen)
                            currentStep = 3
                            print(conditions)
                        },
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    )
                }

                3 -> {
                    CauseScreen(
                        onClickNext = {
                            causes.clear()
                            causes.addAll(it)
                            onComplete(
                                DailyCheckRequest(
                                    email = "",
                                    dailyCheckDate = getTodayDateString("yyyy-MM-dd"),
                                    dailyCheckInfo = DailyCheckInfoRequest(
                                        visionStatus = visionStatus,
                                        condition = conditions,
                                        causes = causes,
                                        done = true
                                    ),
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    )
                }
            }
        }
    }
}