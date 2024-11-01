package com.europa.sightup.presentation.screens.exercise.countdownscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import io.github.alexzhirkevich.compottie.Compottie
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
fun SDSExerciseCountdownScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ExerciseCountdownScreen(
            startColor = Color.LightGray,
            endColor = Color.Yellow,
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
fun ExerciseCountdownScreen(
    startColor: Color,
    endColor: Color,
    onFinish: () -> Unit,
) {
    var isVisible by remember { mutableStateOf(true) }

    val animationPath = "files/animation_delete_me.json"
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animationPath).decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = Compottie.IterateForever
    )

    LaunchedEffect(Unit) {
        delay(2000L)
        isVisible = false
    }

    LaunchedEffect(progress) {
        if (progress >= 0.99f) {
            onFinish()
        }
    }

    val backgroundColor = animateBackgroundColor(
        startColor = startColor,
        endColor = endColor,
        durationMillis = 5000
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        SDSTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            title = "Circular Motion",
            iconLeftVisible = true,
            iconRightVisible = true,
            iconRight = Res.drawable.close
        )

        if (isVisible) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "Now let's get started!",
                    style = SightUPTheme.textStyles.h3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = rememberLottiePainter(
                        composition = composition,
                        progress = { progress },
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun animateBackgroundColor(
    startColor: Color,
    endColor: Color,
    durationMillis: Int,
): Color {
    var isStartColor by remember { mutableStateOf(true) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isStartColor) startColor else endColor,
        animationSpec = tween(durationMillis)
    )

    LaunchedEffect(Unit) {
        isStartColor = !isStartColor
    }

    return backgroundColor
}