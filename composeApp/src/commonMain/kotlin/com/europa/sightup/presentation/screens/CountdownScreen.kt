package com.europa.sightup.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close

@Composable
fun SDSCountdownScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CountdownScreen(
            onFinish = {
                showToast(
                    "new screen"
                )
            }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CountdownScreen(
    animationPath: String = "",
    titleHeader: String? = null,
    onLeftButtonHeaderClick: (() -> Unit)? = null,
    onRightButtonHeaderClick: (() -> Unit)? = null,
    onFinish: () -> Unit,
) {
    var isVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }
    var hasFinished by remember { mutableStateOf(false) }

    val compositionResult by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animationPath).decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = compositionResult,
        isPlaying = isPlaying
    )

    LaunchedEffect(Unit) {
        delay(1000L)
        isVisible = false
        isPlaying = true
    }

    LaunchedEffect(progress) {
        if (progress >= 0.99f && !hasFinished) {
            hasFinished = true
            onFinish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (titleHeader != null || onLeftButtonHeaderClick != null || onRightButtonHeaderClick != null) {
            SDSTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_sm)
                    .align(Alignment.TopCenter),
                title = titleHeader ?: "",
                iconLeftVisible = onLeftButtonHeaderClick != null,
                onLeftButtonClick = {
                    onLeftButtonHeaderClick?.invoke()
                },
                iconRightVisible = true,
                iconRight = Res.drawable.close,
                onRightButtonClick = {
                    onRightButtonHeaderClick?.invoke()
                }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            if (isVisible) {
                Text(
                    text = "Now let's get started!",
                    style = SightUPTheme.textStyles.h3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Image(
                    painter = rememberLottiePainter(
                        composition = compositionResult,
                        progress = { progress }
                    ),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    contentDescription = null
                )
            }
        }
    }
}