package com.europa.sightup.presentation.screens.exercise.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSCardExerciseBottom
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseCountdown
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
fun ExerciseDetailsScreen(
    idExercise: String = "",
    title: String = "",
    category: String = "",
    motivation: String = "",
    duration: Int = 0,
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

    var isChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_default)
    ) {
        SDSTopBar(
            title = "",
            iconLeftVisible = true,
            onLeftButtonClick = {
                navController?.popBackStack()
            },
            iconRightVisible = true,
            iconRight = Icons.Default.Share,
            onRightButtonClick = {
                // TODO: Implement share
            },
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
            category = category,
            title = title,
            motivation = motivation,
            duration = duration,
            buttonText = buttonText,
            onClick = {
                navController?.navigate(
                    ExerciseCountdown(
                        exerciseId = idExercise,
                        exerciseName = title,
                        exerciseMotivation = motivation,
                        exerciseDuration = duration,
                    )
                )
            },
            isChecked = isChecked,
            onCheckedChanged = { isChecked = it },
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_md,
                )
        )
    }
}