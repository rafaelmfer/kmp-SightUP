package com.europa.sightup.platformspecific.audioplayer

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.concurrent.TimeUnit

@Composable
actual fun CMPAudioPlayer(
    modifier: Modifier,
    url: String,
    isPause: Boolean,
    totalTime: (Int) -> Unit,
    currentTime: (Int) -> Unit,
    isSliding: Boolean,
    sliderTime: Int?,
    isRepeat: Boolean,
    loadingState: (Boolean) -> Unit,
    didEndAudio: () -> Unit,
) {
    var repeatStatus by remember { mutableStateOf(isRepeat) }
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(url, context)

    // Update repeat mode based on isRepeat state
    LaunchedEffect(isRepeat) {
        repeatStatus = isRepeat
        exoPlayer.repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    // Update current time every second
    LaunchedEffect(exoPlayer) {
        while (isActive) {
            currentTime(TimeUnit.MILLISECONDS.toSeconds(exoPlayer.currentPosition).coerceAtLeast(0L).toInt())
            delay(1000)
        }
    }

    // Manage player listener and lifecycle
    DisposableEffect(key1 = exoPlayer) {
        val listener = createPlayerListener(totalTime, currentTime, loadingState, didEndAudio, repeatStatus, isSliding)

        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Control playback based on isPause state
    LaunchedEffect(isPause) {
        exoPlayer.playWhenReady = !isPause
    }

    // Seek to slider time if provided
    sliderTime?.let { time ->
        LaunchedEffect(time) {
            exoPlayer.seekTo(TimeUnit.SECONDS.toMillis(time.toLong()))
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun rememberExoPlayer(
    url: String,
    context: Context,
): ExoPlayer {
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

    // Prepare media source when URL changes
    LaunchedEffect(url) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        val dataSourceFactory = DefaultDataSource.Factory(context)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.seekTo(0)
    }

    return exoPlayer
}

private fun createPlayerListener(
    totalTime: (Int) -> Unit,
    currentTime: (Int) -> Unit,
    loadingState: (Boolean) -> Unit,
    didEndAudio: () -> Unit,
    repeatStatus: Boolean,
    isSliding: Boolean,
): Player.Listener {

    return object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            if (!isSliding) {
                totalTime(TimeUnit.MILLISECONDS.toSeconds(player.duration).coerceAtLeast(0L).toInt())
                currentTime(TimeUnit.MILLISECONDS.toSeconds(player.currentPosition).coerceAtLeast(0L).toInt())
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> loadingState(true)
                Player.STATE_READY -> loadingState(false)
                Player.STATE_ENDED -> if (!repeatStatus) {
                    didEndAudio()
                }

                Player.STATE_IDLE -> { /* No-op */
                }
            }
        }
    }
}