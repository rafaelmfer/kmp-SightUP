package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.europa.sightup.platformspecific.LandscapeOrientation
import com.europa.sightup.platformspecific.videoplayer.CMPPlayer
import com.europa.sightup.platformspecific.videoplayer.ScreenResize
import com.europa.sightup.presentation.designsystem.components.data.PlayerConfig
import kotlinx.coroutines.delay

@Composable
fun SDSVideoPlayerPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff91b1fb)),
        contentAlignment = Alignment.Center,
    ) {
        SDSVideoPlayerView(
            modifier = Modifier
                .fillMaxSize(),
            url = "https://firebasestorage.googleapis.com/v0/b/sightup-3b463.firebasestorage.app/o/logo_animation.mp4?alt=media&token=8403dfa9-ebe8-4423-b5a7-015a947fbf91",
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
                videoFitMode = ScreenResize.FIT,
                loaderView = {},
            )
        )
    }
}



@Composable
fun SDSVideoPlayerView(
    modifier: Modifier = Modifier, // Modifier for the composable
    url: String, // URL of the video
    playerConfig: PlayerConfig = PlayerConfig(), // Configuration for the player
) {
    var isPause by remember { mutableStateOf(false) } // State for pausing/resuming video
    var showControls by remember { mutableStateOf(true) } // State for showing/hiding controls
    var isSeekbarSliding = false // Flag for indicating if the seek bar is being slid
    var isFullScreen by remember { mutableStateOf(false) }

    playerConfig.isPause?.let {
        isPause = it
    }
    LaunchedEffect(url) {
        if (playerConfig.isPause == null) {
            isPause = false
        }
    }

    // Auto-hide controls if enabled
    if (playerConfig.isAutoHideControlEnabled) {
        LaunchedEffect(showControls) {
            if (showControls) {
                delay(timeMillis = (playerConfig.controlHideIntervalSeconds * 1000).toLong()) // Delay hiding controls
                if (isSeekbarSliding.not()) {
                    showControls = false // Hide controls if seek bar is not being slid
                }
            }
        }
    }

    LandscapeOrientation(isFullScreen) {
        // Video player with control
        VideoPlayerWithControl(
            modifier = if (isFullScreen) {
                Modifier.fillMaxSize()
            } else {
                modifier
            },
            url = url, // URL of the video
            playerConfig = playerConfig, // Player configuration
            isPause = isPause, // Flag indicating if the video is paused
            onPauseToggle = {
                playerConfig.pauseCallback?.invoke(isPause.not())
                isPause = isPause.not()
            }, // Toggle pause/resume
            onShowControlsToggle = {
                showControls = showControls.not()
            }, // Toggle show/hide controls
        )
    }
}

@Composable
private fun VideoPlayerWithControl(
    modifier: Modifier,
    url: String, // URL of the video
    playerConfig: PlayerConfig, // Configuration for the player
    isPause: Boolean, // Flag indicating if the video is paused
    onPauseToggle: (() -> Unit), // Callback for toggling pause/resume
    onShowControlsToggle: (() -> Unit), // Callback for toggling show/hide controls
) {
    var totalTime by remember { mutableStateOf(0) } // Total duration of the video
    var currentTime by remember { mutableStateOf(0) } // Current playback time
    var isSliding by remember { mutableStateOf(false) } // Flag indicating if the seek bar is being slid
    var sliderTime: Int? by remember { mutableStateOf(null) } // Time indicated by the seek bar
    var isMute by remember { mutableStateOf(false) } // Flag indicating if the audio is muted
    var showSpeedSelection by remember { mutableStateOf(false) } // Selected playback speed
    var screenSize by remember { mutableStateOf(playerConfig.videoFitMode) } // Selected playback speed
    var isBuffering by remember { mutableStateOf(true) }

    playerConfig.isMute?.let {
        isMute = it
    }

    LaunchedEffect(isBuffering) {
        playerConfig.bufferCallback?.invoke(isBuffering)
    }
    LaunchedEffect(totalTime) {
        if (totalTime > 0) {
            playerConfig.totalTimeInSeconds?.invoke(totalTime)
            playerConfig.startTimeInSeconds?.let {
                isSliding = true
                sliderTime = it
                isSliding = false
                playerConfig.startTimeInSeconds = null
            }
        }
    }
    LaunchedEffect(currentTime) {
        playerConfig.currentTimeInSeconds?.invoke(currentTime)
    }

    // Container for the video player and control components
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures { _ ->
                    onShowControlsToggle() // Toggle show/hide controls on tap
                    showSpeedSelection = false
                }
            }
    ) {
        // Video player component
        CMPPlayer(
            modifier = modifier,
            url = url,
            isPause = isPause,
            isMute = isMute,
            totalTime = { totalTime = it }, // Update total time of the video
            currentTime = {
                if (isSliding.not()) {
                    currentTime = it // Update current playback time
                    sliderTime = null // Reset slider time if not sliding
                }
            },
            isSliding = isSliding, // Pass seek bar sliding state
            sliderTime = sliderTime, // Pass seek bar slider time
            size = screenSize,
            bufferCallback = { isBuffering = it },
            didEndVideo = {
                playerConfig.didEndVideo?.invoke()
                if (playerConfig.loop.not()) {
                    onPauseToggle()
                }
            },
            loop = playerConfig.loop,
            volume = 0f
        )

        if (isBuffering) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                if (playerConfig.loaderView != null) {
                    playerConfig.loaderView?.invoke()
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center).size(playerConfig.pauseResumeIconSize),
                    )
                }
            }
        }
    }
}