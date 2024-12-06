package com.europa.sightup.platformspecific.audioplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVQueuePlayer
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.playbackLikelyToKeepUp
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVFoundation.seekToTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSURL
import platform.darwin.NSObject


fun createUrl(url: String): NSURL? {
    return if (url.startsWith("http://") || url.startsWith("https://")) {
        NSURL.URLWithString(url)
    } else {
        NSURL.fileURLWithPath(url)
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
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
    val playerItem = remember { mutableStateOf<AVPlayerItem?>(null) }
    val player = remember { AVQueuePlayer() }
    var repeatStatus by remember { mutableStateOf(isRepeat) }

    // Load the audio item when the URL changes
    LaunchedEffect(url) {
        playerItem.value = createUrl(url)?.let { AVPlayerItem(uRL = it) }
        player.replaceCurrentItemWithPlayerItem(playerItem.value)

        if (isPause) {
            player.pause()
        } else {
            player.play()
        }
    }

    // Update repeat status when it changes
    LaunchedEffect(isRepeat) {
        repeatStatus = isRepeat
    }

    // Handle play/pause and seeking when isPause or sliderTime changes
    LaunchedEffect(isPause, sliderTime) {
        if (isPause) {
            player.pause()
        } else {
            player.play()
        }
        sliderTime?.let {
            player.seekToTime(CMTimeMakeWithSeconds(it.toDouble(), 1))
        }
    }

    DisposableEffect(Unit) {
        // Observer for when the audio item finishes playing
        val observerObject = object : NSObject() {
            @ObjCAction
            fun onPlayerItemDidPlayToEndTime() {
                if (repeatStatus) {
                    player.currentItem?.let { item ->
                        player.seekToTime(CMTimeMakeWithSeconds(0.0, 1))
                        player.removeItem(item)
                        player.insertItem(item, afterItem = null)
                        player.play()
                    }
                } else {
                    didEndAudio()
                }
            }
        }

        // Periodic time observer to update current and total time
        val timeObserver = player.addPeriodicTimeObserverForInterval(
            CMTimeMakeWithSeconds(1.0, 1),
            null
        ) {
            if (!isSliding) {
                val duration = player.currentItem?.duration?.let { CMTimeGetSeconds(it) } ?: 0.0
                val current = CMTimeGetSeconds(player.currentTime())
                currentTime(current.toInt())
                totalTime(duration.toInt())
                loadingState(player.currentItem?.playbackLikelyToKeepUp?.not() ?: false)
            }
        }

        // Add observer for player item end notification
        NSNotificationCenter.defaultCenter().addObserver(
            observerObject,
            NSSelectorFromString("onPlayerItemDidPlayToEndTime"),
            AVPlayerItemDidPlayToEndTimeNotification,
            player.currentItem
        )

        onDispose {
            player.pause()
            player.replaceCurrentItemWithPlayerItem(null)
            NSNotificationCenter.defaultCenter().removeObserver(observerObject)
            player.removeTimeObserver(timeObserver)
        }
    }
}