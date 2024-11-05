package com.europa.sightup.presentation.screens.exercise.running

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.europa.sightup.platformspecific.videoplayer.ScreenResize
import com.europa.sightup.presentation.designsystem.components.SDSTimer
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.SDSVideoPlayerView
import com.europa.sightup.presentation.designsystem.components.data.PlayerConfig
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseFinish
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRunning
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close

@Composable
fun ExerciseRunningScreen(
    exerciseId: String = "",
    exerciseName: String = "",
    exerciseDuration: Int = 0,
    exerciseVideo: String = "",
    navController: NavController? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_default),
    ) {
        SDSTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f),
            title = "",
            iconLeftVisible = false,
            iconRightVisible = true,
            iconRight = Res.drawable.close,
            onRightButtonClick = {
                // TODO: open Dialog to confirm exit and if confirmed, navigate to ExerciseRoot
                navController?.popBackStack<ExerciseRoot>(inclusive = false)
            },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, bottom = 80.dp),
            contentAlignment = Alignment.Center,
        ) {
            SDSVideoPlayerView(
                modifier = Modifier
                    .fillMaxSize(),
                url = exerciseVideo,
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
                    isMute = false,
                    isPause = false,
                    isScreenResizeEnabled = false,
                    loop = false,
                    videoFitMode = ScreenResize.RESIZE,
                    loaderView = {},
                )
            )
        }

        val arguments = navController?.currentBackStackEntry?.toRoute<ExerciseRunning>()
        SDSTimer(
            seconds = 0,
            minutes = exerciseDuration,
            onTimerFinish = {
                navController?.navigate(
                    ExerciseFinish(
                        exerciseId = exerciseId,
                        category = arguments?.category ?: "",
                        exerciseName = exerciseName,
                        finishTitle = arguments?.finishTitle ?: "",
                        advice = arguments?.advice ?: "",
                    )
                )
            },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}