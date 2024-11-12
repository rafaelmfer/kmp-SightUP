package com.europa.sightup.presentation.screens.exercise.evaluation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerView
import com.europa.sightup.platformspecific.getPlatform
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSListButtonsSelectable
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ANDROID
import com.europa.sightup.utils.Moods
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.save

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EvaluateExercise(
    onSaveClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var choiceChosen by remember { mutableStateOf("") }

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/self_assessment.json").decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 2f,
        iterations = Compottie.IterateForever
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (getPlatform().name == ANDROID) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(Color.Transparent),
            ) {
                VideoPlayerView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f, false)
                        .fillMaxSize(),
                    url = "https://firebasestorage.googleapis.com/v0/b/sightup-3b463.firebasestorage.app/o/Part%231.mp4?alt=media&token=850778b7-ff80-44ff-8a58-d28aa2a86acf",
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
                        isMute = true,
                        isPause = false,
                        isScreenResizeEnabled = false,
                        loop = true,
                        videoFitMode = ScreenResize.FILL,
                        didEndVideo = { },
                        loaderView = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Transparent),
                            ) {
                            }
                        }
                    )
                )
            }
        } else {
            Image(
                painter = rememberLottiePainter(
                    composition = composition,
                    progress = { progress },
                ),
                modifier = Modifier
                    .size(240.dp)
                    .background(Color.Transparent),
                contentDescription = null
            )
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_12))
        Text(
            text = "How are your eyes now?",
            style = SightUPTheme.textStyles.h4,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_4))
        Text(
            text = "Let’s us know how are you feeling after this  exercise section and will be able to suggest new exercises based on your experience.",
            style = SightUPTheme.textStyles.body,
            textAlign = TextAlign.Center,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        SDSListButtonsSelectable(
            items = listOf(
                "Very good! Sharp as a hawk!",
                "Good! Clear and focused!",
                "Just fine.",
                "A bit tired.",
                "Very tired. Ready for a nap!"
            ),
            onSelectedChange = {
                when (it) {
                    "Very good! Sharp as a hawk!" -> choiceChosen = Moods.VERY_GOOD.value
                    "Good! Clear and focused!" -> choiceChosen = Moods.GOOD.value
                    "Just fine." -> choiceChosen = Moods.MODERATE.value
                    "A bit tired." -> choiceChosen = Moods.POOR.value
                    "Very tired. Ready for a nap!" -> choiceChosen = Moods.VERY_POOR.value
                }
            },
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        SDSButton(
            text = stringResource(Res.string.save),
            onClick = {
                onSaveClicked(choiceChosen)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}