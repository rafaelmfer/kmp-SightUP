package com.europa.sightup.presentation.screens.exercise.finish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSCardExerciseBottom
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.ONE_FLOAT
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExerciseFinishScreen(
    idExercise: String = "",
    title: String = "",
    motivation: String = "",
    tipText: String = "",
    buttonText: String = "",
    navController: NavController? = null,
) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        SDSTopBar(
            title = "",
            iconLeftVisible = false,
            iconRightVisible = false,
            modifier = Modifier
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_sm,
                )
        )

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_side_margin,
                )
                .weight(ONE_FLOAT),
            contentDescription = null
        )

        SDSCardExerciseBottom(
            category = "",
            title = title,
            motivation = "For better results, repeat this exercise twice a day.",
            duration = 0,
            buttonText = buttonText,
            onClick = {
                navController?.navigate(Home)
            },
            showGuidance = false,
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_md,
                )
        )
    }
}