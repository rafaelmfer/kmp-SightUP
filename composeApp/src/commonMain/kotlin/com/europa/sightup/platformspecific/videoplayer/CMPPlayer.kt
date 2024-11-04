package com.europa.sightup.platformspecific.videoplayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class ScreenResize {
    FIT, FILL, RESIZE
}

@Composable
expect fun CMPPlayer(
    modifier: Modifier,
    url: String,
    isPause: Boolean,
    isMute: Boolean,
    totalTime: ((Int) -> Unit),
    currentTime: ((Int) -> Unit),
    isSliding: Boolean,
    sliderTime: Int?,
    size: ScreenResize,
    bufferCallback: ((Boolean) -> Unit),
    didEndVideo: (() -> Unit),
    loop: Boolean,
    volume: Float
)