package com.europa.sightup.platformspecific.videoplayer

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
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import platform.AVFoundation.AVLayerVideoGravityResize
import platform.AVFoundation.AVLayerVideoGravityResizeAspect
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.AVQueuePlayer
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.muted
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.playbackLikelyToKeepUp
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVFoundation.seekToTime
import platform.AVKit.AVPlayerViewController
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIView
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun CMPPlayer(
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
    volume: Float,
) {
    val playerItem = remember { mutableStateOf<AVPlayerItem?>(null) }
    val player: AVQueuePlayer by remember { mutableStateOf(AVQueuePlayer(playerItem.value)) }
    val playerLayer by remember { mutableStateOf(AVPlayerLayer()) }

    // Set up player view controller
    val avPlayerViewController = remember { AVPlayerViewController() }
    avPlayerViewController.player = player
    avPlayerViewController.showsPlaybackControls = false
    avPlayerViewController.videoGravity = when (size) {
        ScreenResize.RESIZE -> AVLayerVideoGravityResize
        ScreenResize.FIT -> AVLayerVideoGravityResizeAspect
        ScreenResize.FILL -> AVLayerVideoGravityResizeAspectFill
    }

    val playerContainer = UIView().apply {
        layer.addSublayer(playerLayer)
    }
    player.muted = isMute

    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(isLoading) {
        bufferCallback(isLoading)
    }

    LaunchedEffect(url) {
        val urlObject = createUrl(url)
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

    androidx.compose.ui.viewinterop.UIKitView(
        factory = {
            playerContainer.addSubview(avPlayerViewController.view)
            avPlayerViewController.view.setFrame(playerContainer.frame)
            playerContainer
        },
        update = {
            MainScope().launch {
                if (isPause) {
                    player.pause()
                } else {
                    player.play()
                }
                UIApplication.sharedApplication.idleTimerDisabled = isPause.not()
                sliderTime?.let {
                    val time = CMTimeMakeWithSeconds(it.toDouble(), 1)
                    player.seekToTime(time)
                }

            }
        },
        modifier = modifier,
    )

    DisposableEffect(Unit) {
        val observerObject = object : NSObject() {
            @ObjCAction
            fun onPlayerItemDidPlayToEndTime() {
                player.currentItem?.let { item ->
                    player.seekToTime(CMTimeMakeWithSeconds(0.0, 1))
                    player.removeItem(item)
                    player.insertItem(item, afterItem = null)
                    player.play()
                    didEndVideo()
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
                    isLoading = player.currentItem?.playbackLikelyToKeepUp?.not() ?: false
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
            UIApplication.sharedApplication.idleTimerDisabled = false
            player.pause()
            player.replaceCurrentItemWithPlayerItem(null)
            NSNotificationCenter.defaultCenter().removeObserver(observerObject)
            player.removeTimeObserver(timeObserver)
        }
    }
}

private fun createUrl(url: String): NSURL? {
    return if (url.startsWith("http://") || url.startsWith("https://")) {
        NSURL.URLWithString(url)
    } else {
        NSURL.fileURLWithPath(url)
    }
}