package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerView
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StepScreenWithAnimation(
    animationPath: String,
    instructionText: String,
    speed: Float = 1f,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animationPath).decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = speed,
        iterations = Compottie.IterateForever
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize().then(modifier),
    ) {
        if (composition == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Image(
                painter = rememberLottiePainter(
                    composition = composition,
                    progress = { progress },
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentDescription = "Animation"
            )
        }
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        CardWithTestInstructions(
            text = instructionText
        )
    }
}

@Composable
fun StepScreenWithVideo(
    videoPath: String,
    muteVideo: Boolean = false,
    instructionText: String,
    modifier: Modifier = Modifier,
) {
    var isVideoLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(150L)
        isVideoLoaded = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize().then(modifier),
    ) {
        if (isVideoLoaded) {

            VideoPlayerView(
                modifier = Modifier
                    .fillMaxSize(),
                url = videoPath,
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
                    isMute = muteVideo,
                    isPause = false,
                    isScreenResizeEnabled = false,
                    loop = true,
                    videoFitMode = ScreenResize.FILL,
                    loaderView = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .defaultMinSize(minHeight = 300.dp)
                                .background(SightUPTheme.sightUPColors.background_default),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                )
            )
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        CardWithTestInstructions(
            text = instructionText
        )
    }
}
