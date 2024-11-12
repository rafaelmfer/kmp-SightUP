package com.europa.sightup.platformspecific.audioplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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

@OptIn(ExperimentalForeignApi::class)
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
    val playerItem = remember { mutableStateOf<AVPlayerItem?>(null) }
    val player: AVQueuePlayer by remember { mutableStateOf(AVQueuePlayer(playerItem.value)) }
    var repeatStatus by remember { mutableStateOf(isRepeat) }

    LaunchedEffect(url) {
        val urlObject = NSURL.URLWithString(url)
        val newItem = urlObject?.let { AVPlayerItem(uRL = it) }
        playerItem.value = newItem
        playerItem.value?.let {
            player.replaceCurrentItemWithPlayerItem(it)
        }
        if (isPause) {
            player.pause()
        } else {
            player.play()
        }
    }
    LaunchedEffect(isRepeat) {
        repeatStatus = isRepeat
    }

    LaunchedEffect(isPause, sliderTime) {
        MainScope().launch {
            if (isPause) {
                player.pause()
            } else {
                player.play()
            }
            sliderTime?.let {
                val time = CMTimeMakeWithSeconds(it.toDouble(), 1)
                player.seekToTime(time)
            }
        }
    }

    DisposableEffect(Unit) {
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

        val timeObserver = player.addPeriodicTimeObserverForInterval(
            CMTimeMakeWithSeconds(1.0, 1),
            null
        ) { _ ->
            if (!isSliding) {
                MainScope().launch {
                    val duration = player.currentItem?.duration?.let { CMTimeGetSeconds(it) } ?: 0.0
                    val current = CMTimeGetSeconds(player.currentTime())
                    currentTime(current.toInt())
                    totalTime(duration.toInt())
                    loadingState(player.currentItem?.playbackLikelyToKeepUp?.not() ?: false)
                }
            }
        }

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