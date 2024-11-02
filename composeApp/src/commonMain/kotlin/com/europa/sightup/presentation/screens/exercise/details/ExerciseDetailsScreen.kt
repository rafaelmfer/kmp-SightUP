package com.europa.sightup.presentation.screens.exercise.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSCardExerciseBottom
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseCountdown
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.ONE_FLOAT
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.share

@Composable
fun ExerciseDetailsScreen(
    idExercise: String = "",
    title: String = "",
    category: String = "",
    motivation: String = "",
    duration: Int = 0,
    image: String = "",
    buttonText: String = "",
    navController: NavController? = null,
) {
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
            iconRight = Res.drawable.share,
            onRightButtonClick = {
                // TODO: Implement share
            },
            modifier = Modifier
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_sm,
                )
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        CoilImage(
            imageModel = { image },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                requestSize = IntSize(
                    width = -1,
                    height = 130.dp.value.toInt(),
                ),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_side_margin,
                ),
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            },
            failure = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Image failed to load")
                }
            }
        )
        Spacer(Modifier.weight(ONE_FLOAT))
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
                        motivation = motivation,
                        duration = duration,
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