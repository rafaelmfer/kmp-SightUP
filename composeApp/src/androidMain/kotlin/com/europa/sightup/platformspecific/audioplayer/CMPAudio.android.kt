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
    didEndAudio: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(url, isRepeat, context)
    var repeatStatus by remember { mutableStateOf(isRepeat) }

    LaunchedEffect(isRepeat) {
        repeatStatus = isRepeat
        exoPlayer.repeatMode = if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    LaunchedEffect(exoPlayer) {
        while (isActive) {
            currentTime(exoPlayer.currentPosition.toInt().coerceAtLeast(0))
            delay(1000L)
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> loadingState(true)
                    Player.STATE_READY -> loadingState(false)
                    Player.STATE_ENDED -> if (!repeatStatus) didEndAudio()
                    Player.STATE_IDLE -> Unit
                }
            }

            override fun onEvents(player: Player, events: Player.Events) {
                if (!isSliding) {
                    totalTime(player.duration.toInt().coerceAtLeast(0))
                    currentTime(player.currentPosition.toInt().coerceAtLeast(0))
                }
            }
        }

        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(isPause) {
        exoPlayer.playWhenReady = !isPause
    }

    sliderTime?.let {
        LaunchedEffect(it) {
            exoPlayer.seekTo(it.toLong())
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun rememberExoPlayer(url: String, isRepeat: Boolean, context: Context): ExoPlayer {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

    LaunchedEffect(url) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        val dataSourceFactory = DefaultDataSource.Factory(context)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.seekTo(0, 0L)
    }

    return exoPlayer
}