package com.europa.sightup.platformspecific.videoplayer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.exoplayer.ExoPlayer

fun getExoPlayerLifecycleObserver(
    exoPlayer: ExoPlayer,
    isPause: Boolean,
    wasAppInBackground: Boolean,
    setWasAppInBackground: (Boolean) -> Unit
): LifecycleEventObserver {
    return LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> handleOnResume(exoPlayer, isPause, wasAppInBackground, setWasAppInBackground)
            Lifecycle.Event.ON_PAUSE -> handleOnPause(exoPlayer, setWasAppInBackground)
            Lifecycle.Event.ON_STOP -> handleOnStop(exoPlayer, setWasAppInBackground)
            Lifecycle.Event.ON_DESTROY -> handleOnDestroy(exoPlayer)
            else -> { /* No-op */ }
        }
    }
}

private fun handleOnResume(
    exoPlayer: ExoPlayer,
    isPause: Boolean,
    wasAppInBackground: Boolean,
    setWasAppInBackground: (Boolean) -> Unit
) {
    if (wasAppInBackground) {
        exoPlayer.playWhenReady = !isPause
    }
    setWasAppInBackground(false)
}

private fun handleOnPause(
    exoPlayer: ExoPlayer,
    setWasAppInBackground: (Boolean) -> Unit
) {
    exoPlayer.playWhenReady = false
    setWasAppInBackground(true)
}

private fun handleOnStop(
    exoPlayer: ExoPlayer,
    setWasAppInBackground: (Boolean) -> Unit
) {
    exoPlayer.playWhenReady = false
    setWasAppInBackground(true)
}

private fun handleOnDestroy(
    exoPlayer: ExoPlayer
) {
    exoPlayer.release()
}