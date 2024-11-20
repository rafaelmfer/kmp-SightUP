package com.europa.sightup.presentation.screens.exercise.running

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.toRoute
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerView
import com.europa.sightup.platformspecific.audioplayer.CMPAudioPlayer
import com.europa.sightup.platformspecific.getLocalFilePathFor
import com.europa.sightup.presentation.designsystem.components.SDSBadgeTime
import com.europa.sightup.presentation.designsystem.components.SDSDialog
import com.europa.sightup.presentation.designsystem.components.SDSTimer
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.ExerciseScreens
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseDetails
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.clickableWithRipple
import com.europa.sightup.utils.goBackToExerciseHome
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionResult
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.audio_off
import sightupkmpapp.composeapp.generated.resources.audio_on
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.repeat
import sightupkmpapp.composeapp.generated.resources.voice_command_off
import sightupkmpapp.composeapp.generated.resources.voice_command_on

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExerciseRunningScreen(
    exerciseId: String = "",
    exerciseName: String = "",
    exerciseDuration: Int = 0,
    exerciseVideo: String = "",
    musicAudioGuidanceEnabled: Boolean = true,
    navController: NavController? = null,
) {
    var navigateTo by remember { mutableStateOf(false) }

    LaunchedEffect(navigateTo) {
        if (navigateTo) {
            delay(250L)
            navController?.goBackToExerciseHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_default),
    ) {
        var showDialog by remember { mutableStateOf(false) }
        var isPauseEverything by remember { mutableStateOf(true) }

        var isVoiceVideoMute by remember { mutableStateOf(!musicAudioGuidanceEnabled) }
        var isMusicPaused by remember { mutableStateOf(!musicAudioGuidanceEnabled) }

        var guideBoxVisibility by remember { mutableStateOf(true) }
        var controlAlpha by remember { mutableStateOf(true) }
        val alphaVideoView by animateFloatAsState(
            targetValue = if (controlAlpha) 0f else 1f,
            animationSpec = tween(500)
        )
        val alphaGuideBox by animateFloatAsState(
            targetValue = if (controlAlpha) 1f else 0f,
            animationSpec = tween(500),
            finishedListener = {
                guideBoxVisibility = false
                isPauseEverything = false
            }
        )

        SDSTopBar(
            modifier = Modifier
                .padding(horizontal = SightUPTheme.spacing.spacing_xs)
                .align(Alignment.TopCenter)
                .zIndex(1f),
            title = "",
            iconLeftVisible = guideBoxVisibility,
            onLeftButtonClick = {
                navController?.popBackStack<ExerciseDetails>(inclusive = false)
            },
            iconRightVisible = true,
            iconRight = Res.drawable.close,
            onRightButtonClick = {
                showDialog = true
            },
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                .alpha(alphaVideoView),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 84.dp),
                contentAlignment = Alignment.Center,
            ) {
                VideoPlayerView(
                    modifier = Modifier
                        .fillMaxSize(),
                    // Because Blink exercise video is too heavy to download, we use the local file
                    url = if (exerciseName.equals("Blink Relaxation", ignoreCase = true)) {
                        getLocalFilePathFor("blink_relaxation_exercise.mp4")
                    } else {
                        exerciseVideo
                    },
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
                        isMute = isVoiceVideoMute,
                        isPause = isPauseEverything,
                        isScreenResizeEnabled = false,
                        loop = false,
                        videoFitMode = ScreenResize.FILL,
                        loaderView = {},
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    SightUPTheme.spacing.spacing_lg,
                    Alignment.CenterHorizontally
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(SightUPTheme.sizes.size_56)
                        .clip(SightUPTheme.shapes.extraLarge)
                        .clickableWithRipple { isMusicPaused = !isMusicPaused }
                ) {
                    Icon(
                        painter = painterResource(if (isMusicPaused) Res.drawable.audio_off else Res.drawable.audio_on),
                        contentDescription = "Pause",
                        modifier = Modifier
                            .size(SightUPTheme.sizes.size_32)
                    )
                }
                val arguments = navController?.currentBackStackEntry?.toRoute<ExerciseScreens.ExerciseRunning>()
                SDSTimer(
                    seconds = exerciseDuration - 1,
                    isRunning = !isPauseEverything,
                    onTimerFinish = {
                        navController?.navigate(
                            ExerciseScreens.ExerciseFinish(
                                exerciseId = exerciseId,
                                category = arguments?.category ?: "",
                                exerciseName = exerciseName,
                                finishTitle = arguments?.finishTitle ?: "",
                                advice = arguments?.advice ?: "",
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(SightUPTheme.sizes.size_56)
                        .clip(SightUPTheme.shapes.extraLarge)
                        .clickableWithRipple { isVoiceVideoMute = !isVoiceVideoMute }
                ) {
                    Icon(
                        painter = painterResource(if (isVoiceVideoMute) Res.drawable.voice_command_off else Res.drawable.voice_command_on),
                        contentDescription = "Pause",
                        modifier = Modifier
                            .size(SightUPTheme.sizes.size_32)
                    )
                }
            }
        }

        if (guideBoxVisibility) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp)
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    .background(SightUPTheme.sightUPColors.background_default)
                    .alpha(alphaGuideBox)
                    .clickableWithRipple(indication = null) {
                        controlAlpha = false
                    },
            ) {

                var guideText by remember { mutableStateOf("") }
                var repetitions by remember { mutableStateOf("") }
                var guideAnimation by remember { mutableStateOf("") }
                when (exerciseName) {
                    "Circular Motion" -> {
                        guideAnimation = "files/circular_motion_intro.json"
                        guideText = "Begin by moving your eyes clockwise. When you hear the bell, switch direction."
                        repetitions = "5"
                    }

                    "Blink Relaxation" -> {
                        guideAnimation = "files/blink_relaxation_intro.json"
                        guideText = "Take a deep breath and keep blinking gently."
                        repetitions = "5"
                    }

                    "Color Game" -> {
                        guideAnimation = "files/coordination_color_game_intro.json"
                        guideText = "Follow the moving colors on your screen with your eyes."
                        repetitions = ""
                    }

                    else -> {
                        guideAnimation = "files/distension_stretching_intro.json"
                        guideText = "Follow the arrows, and check the next step when the bell rings."
                        repetitions = "2"
                    }
                }

                val composition: LottieCompositionResult = rememberLottieComposition {
                    LottieCompositionSpec.JsonString(
                        Res.readBytes(guideAnimation).decodeToString()
                    )
                }

                val progress by animateLottieCompositionAsState(
                    composition = composition.value,
                    speed = 1.1f,
                    iterations = Compottie.IterateForever
                )

                Spacer(Modifier.weight(ONE_FLOAT))

                if (!composition.isComplete) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(60.dp)
                            .size(SightUPTheme.sizes.size_56)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                AnimatedVisibility(
                    visible = composition.isComplete,
                    modifier = Modifier.padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Image(
                        painter = rememberLottiePainter(
                            composition = composition.value,
                            progress = { progress },
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SightUPTheme.spacing.spacing_xl),
                    )
                }
                Spacer(Modifier.height(SightUPTheme.sizes.size_20))
                Text(
                    text = guideText,
                    style = SightUPTheme.textStyles.h4,
                    textAlign = TextAlign.Center,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier,
                )
                Spacer(Modifier.weight(ONE_FLOAT))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                ) {
                    SDSBadgeTime(
                        timeSeconds = exerciseDuration,
                        modifier = Modifier,
                    )
                    if (repetitions.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .clip(SightUPTheme.shapes.extraLarge)
                                .border(
                                    width = SightUPBorder.Width.sm,
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    shape = SightUPTheme.shapes.extraLarge
                                )
                                .padding(
                                    horizontal = SightUPTheme.spacing.spacing_xs,
                                    vertical = SightUPTheme.spacing.spacing_2xs
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(SightUPTheme.sizes.size_16),
                                painter = painterResource(Res.drawable.repeat),
                                contentDescription = null,
                                tint = SightUPTheme.sightUPColors.text_primary
                            )
                            Spacer(Modifier.width(SightUPTheme.spacing.spacing_2xs))
                            Text(
                                modifier = Modifier,
                                text = "$repetitions reps",
                                color = SightUPTheme.sightUPColors.text_primary,
                                style = SightUPTheme.textStyles.caption
                            )
                        }
                    }
                }

                Spacer(Modifier.weight(ONE_FLOAT))
                Text(
                    text = "Tap to continue",
                    style = SightUPTheme.textStyles.body.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
                Spacer(Modifier.height(SightUPTheme.sizes.size_32))
            }
        }

        if (!isPauseEverything) {
            CMPAudioPlayer(
                modifier = Modifier.height(0.dp),
                url = getLocalFilePathFor("${exerciseName.lowercase().replace(" ", "_")}_music.mp3"),
                isPause = isMusicPaused,
                totalTime = { },
                currentTime = { },
                isSliding = false,
                sliderTime = null,
                isRepeat = false,
                loadingState = { },
                didEndAudio = { }
            )
        }

        SDSDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = it },
            title = "Exit exercise?",
            onClose = null,
            content = { _ ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SightUPTheme.spacing.spacing_md)
                ) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                    Text(
                        text = " If you exit, you’ll need to restart the exercise.",
                        style = SightUPTheme.textStyles.body,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                }
            },
            onPrimaryClick = { showDialog = false},
            buttonPrimaryText = "Continue",
            onSecondaryClick = {
                navigateTo = true
            },
            buttonSecondaryText = "Exit",
        )
    }
}