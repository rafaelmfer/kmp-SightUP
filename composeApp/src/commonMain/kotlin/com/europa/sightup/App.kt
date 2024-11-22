package com.europa.sightup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerView
import com.europa.sightup.platformspecific.getLocalFilePathFor
import com.europa.sightup.presentation.AppNavHost
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
data object SightUPApp

@Serializable
data object AfterSplashScreen

@Composable
fun Init() {
    val navController = rememberNavController()
    InitNavGraph(navController = navController)

    GoogleAuthProvider.create(
        credentials = GoogleAuthCredentials(
            serverId = BuildConfigKMP.WEB_CLIENT_ID
        )
    )
}

@Composable
fun InitNavGraph(
    navController: NavHostController,
) {
    SightUPTheme {
        NavHost(
            navController = navController,
            startDestination = AfterSplashScreen
        ) {
            composable<AfterSplashScreen>(
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 500))
                }
            ) {
                AfterSplashScreen(navController = navController)
            }
            composable<SightUPApp> {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AfterSplashScreen(navController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }
    var isPlayerVisible by remember { mutableStateOf(true) }
    val fadeOutDuration = 500
    var isPause by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(8000L)
        isVisible = true
        isPause = true

        delay((fadeOutDuration - 200).toLong())
        isPlayerVisible = false
        navController.navigate(SightUPApp) {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_default),
        contentAlignment = Alignment.Center,
    ) {
        if (isPlayerVisible) {
            VideoPlayerView(
                modifier = Modifier
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    .fillMaxWidth()
                    .aspectRatio(1f, true)
                    .fillMaxSize(),
                url = getLocalFilePathFor("logo_animation.mp4"),
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
                    isPause = isPause,
                    isScreenResizeEnabled = false,
                    loop = false,
                    videoFitMode = ScreenResize.FILL,
                    didEndVideo = { },
                    loaderView = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SightUPTheme.sightUPColors.background_default)
                        ) {
                        }
                    }
                )
            )
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = fadeOutDuration)),
            exit = fadeOut(animationSpec = tween(durationMillis = fadeOutDuration))
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(SightUPTheme.sightUPColors.background_default),
                contentAlignment = Alignment.Center
            ) {
            }
        }
    }
}

