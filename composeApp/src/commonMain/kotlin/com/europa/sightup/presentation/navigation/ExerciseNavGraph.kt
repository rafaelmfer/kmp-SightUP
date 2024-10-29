package com.europa.sightup.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseCountdown
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseDetails
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseFinish
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseInit
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRunning
import com.europa.sightup.presentation.screens.exercise.ExerciseRootScreen
import com.europa.sightup.presentation.screens.exercise.countdownscreen.ExerciseCountdownScreen
import com.europa.sightup.presentation.screens.exercise.details.ExerciseDetailsScreen
import com.europa.sightup.presentation.screens.exercise.finish.ExerciseFinishScreen
import com.europa.sightup.presentation.screens.exercise.running.ExerciseRunningScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.complete
import sightupkmpapp.composeapp.generated.resources.exercise_circular_tip
import sightupkmpapp.composeapp.generated.resources.start

fun NavGraphBuilder.exerciseNavGraph(navController: NavHostController) {
    navigation<ExerciseInit>(
        startDestination = ExerciseRoot,
    ) {
        composable<ExerciseRoot> {
            ExerciseRootScreen(navController = navController)
        }

        composable<ExerciseDetails> { backStackEntry ->
            val exerciseDetails = backStackEntry.toRoute<ExerciseDetails>()

            ExerciseDetailsScreen(
                idExercise = exerciseDetails.exerciseId,
                title = exerciseDetails.exerciseName,
                category = exerciseDetails.exerciseCategory,
                motivation = exerciseDetails.exerciseMotivation,
                duration = exerciseDetails.exerciseDuration,
                buttonText = stringResource(Res.string.start),
                navController = navController,
            )
        }

        composable<ExerciseCountdown> {
            val exerciseCountdown = it.toRoute<ExerciseCountdown>()

            ExerciseCountdownScreen(
                startColor = Color.LightGray,
                endColor = SightUPTheme.sightUPColors.primary_200,
                onFinish = {
                    navController.navigate(
                        ExerciseRunning(
                            exerciseId = exerciseCountdown.exerciseId,
                            exerciseName = exerciseCountdown.exerciseName,
                            exerciseMotivation = exerciseCountdown.exerciseMotivation,
                            exerciseDuration = exerciseCountdown.exerciseDuration
                        )
                    )
                }
            )
        }

        composable<ExerciseRunning> {
            val exerciseRunning = it.toRoute<ExerciseRunning>()

            ExerciseRunningScreen(
                exerciseId = exerciseRunning.exerciseId,
                exerciseName = exerciseRunning.exerciseName,
                exerciseMotivation = exerciseRunning.exerciseMotivation,
                exerciseDuration = exerciseRunning.exerciseDuration,
            )
        }

        composable<ExerciseFinish> { backStackEntry ->
            val exerciseFinish = backStackEntry.toRoute<ExerciseFinish>()

            ExerciseFinishScreen(
                idExercise = exerciseFinish.exerciseId,
                title = exerciseFinish.exerciseName,
                motivation = exerciseFinish.exerciseMotivation,
                tipText = stringResource(Res.string.exercise_circular_tip),
                buttonText = stringResource(Res.string.complete),
                navController = navController,
            )
        }
    }
}