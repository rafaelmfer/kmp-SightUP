package com.europa.sightup.presentation.screens.exercise.running

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.toRoute
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerView
import com.europa.sightup.platformspecific.audioplayer.CMPAudioPlayer
import com.europa.sightup.platformspecific.getLocalFilePathFor
import com.europa.sightup.presentation.designsystem.components.SDSTimer
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.ExerciseScreens
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.clickableWithRipple
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.audio_off
import sightupkmpapp.composeapp.generated.resources.audio_on
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.voice_command_off
import sightupkmpapp.composeapp.generated.resources.voice_command_on

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

        var isMuteVoiceVideo by remember { mutableStateOf(false) }
        var isPauseMusic by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, bottom = 80.dp),
            contentAlignment = Alignment.Center,
        ) {
            VideoPlayerView(
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
                    isMute = isMuteVoiceVideo,
                    isPause = false,
                    isScreenResizeEnabled = false,
                    loop = false,
                    videoFitMode = ScreenResize.FILL,
                    loaderView = {},
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                SightUPTheme.spacing.spacing_lg,
                Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_56)
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickableWithRipple { isPauseMusic = !isPauseMusic }
            ) {
                Icon(
                    painter = painterResource(if (isPauseMusic) Res.drawable.audio_off else Res.drawable.audio_on),
                    contentDescription = "Pause",
                    modifier = Modifier
                        .size(SightUPTheme.sizes.size_32)
                )
            }
            val arguments = navController?.currentBackStackEntry?.toRoute<ExerciseScreens.ExerciseRunning>()
            SDSTimer(
                seconds = exerciseDuration,
                onTimerFinish = {
                    navController?.navigate(
                        ExerciseScreens.ExerciseFinish(
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

            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_56)
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickableWithRipple { isMuteVoiceVideo = !isMuteVoiceVideo }
            ) {
                Icon(
                    painter = painterResource(if (isMuteVoiceVideo) Res.drawable.voice_command_off else Res.drawable.voice_command_on),
                    contentDescription = "Pause",
                    modifier = Modifier
                        .size(SightUPTheme.sizes.size_32)
                )
            }
        }

        CMPAudioPlayer(
            modifier = Modifier.height(0.dp),
            url = getLocalFilePathFor("${exerciseName.lowercase().replace(" ", "_")}_music.mp3"),
            isPause = isPauseMusic,
            totalTime = { },
            currentTime = { },
            isSliding = false,
            sliderTime = null,
            isRepeat = false,
            loadingState = { },
            didEndAudio = { }
        )
    }
}