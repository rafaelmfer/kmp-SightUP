package com.europa.sightup.presentation.screens.exercise.running

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSTimer
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseFinish

@Composable
fun ExerciseRunningScreen(
    exerciseId: String = "",
    exerciseName: String = "",
    exerciseMotivation: String = "",
    exerciseDuration: Int = 0,
    navController: NavController? = null,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SDSTimer(
            seconds = 10,
            minutes = 0,
            onTimerFinish = {
                navController?.navigate(
                    ExerciseFinish(
                        exerciseId = exerciseId,
                        exerciseName = exerciseName,
                        exerciseMotivation = exerciseMotivation,
                        exerciseDuration = exerciseDuration
                    )
                )
            }
        )
    }
}