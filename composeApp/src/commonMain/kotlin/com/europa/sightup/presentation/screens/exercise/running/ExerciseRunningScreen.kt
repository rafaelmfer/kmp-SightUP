package com.europa.sightup.presentation.screens.exercise.running

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSTimer
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseFinish
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExerciseRunningScreen(
    animationPath: String = "files/splash_animation.json",
    exerciseId: String = "",
    exerciseName: String = "",
    exerciseMotivation: String = "",
    exerciseDuration: Int = 0,
    navController: NavController? = null,
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animationPath).decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(SightUPTheme.sightUPColors.neutral_0),
            contentDescription = "Lottie animation"
        )

        SDSTimer(
            seconds = 10,
            minutes = 0,
            onTimerFinish = {
                navController?.navigate(
                    ExerciseFinish(
                        exerciseId = exerciseId,
                        exerciseName = exerciseName,
                        motivation = exerciseMotivation,
                        duration = exerciseDuration
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}