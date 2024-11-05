package com.europa.sightup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseCountdown
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseDetails
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseEvaluationResult
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseFinish
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseInit
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRunning
import com.europa.sightup.presentation.screens.exercise.ExerciseRootScreen
import com.europa.sightup.presentation.screens.exercise.countdownscreen.ExerciseCountdownScreen
import com.europa.sightup.presentation.screens.exercise.details.ExerciseDetailsScreen
import com.europa.sightup.presentation.screens.exercise.evaluation.ExerciseEvaluationResult
import com.europa.sightup.presentation.screens.exercise.finish.ExerciseFinishScreen
import com.europa.sightup.presentation.screens.exercise.running.ExerciseRunningScreen
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.complete
import sightupkmpapp.composeapp.generated.resources.start

fun NavGraphBuilder.exerciseNavGraph(navController: NavHostController) {
    navigation<ExerciseInit>(
        startDestination = ExerciseRoot,
    ) {
        composable<ExerciseRoot> {
            ExerciseRootScreen(navController = navController)
        }

        composable<ExerciseDetails> { backStackEntry ->
            val arguments = backStackEntry.toRoute<ExerciseDetails>()

            ExerciseDetailsScreen(
                idExercise = arguments.exerciseId,
                title = arguments.exerciseName,
                category = arguments.category,
                motivation = arguments.motivation,
                duration = arguments.duration,
                image = arguments.imageInstruction,
                buttonText = stringResource(Res.string.start),
                navController = navController,
            )
        }

        composable<ExerciseCountdown> {
            val arguments = it.toRoute<ExerciseCountdown>()

            ExerciseCountdownScreen(
                animationPath = "files/countdown_animation.json",
                titleHeader = arguments.exerciseName,
                onLeftButtonHeaderClick = {
                    navController.popBackStack()
                },
                onRightButtonHeaderClick = {
                    // TODO: open Dialog to confirm exit and if confirmed, navigate to ExerciseRoot
                    navController.popBackStack<ExerciseRoot>(inclusive = false)
                },
                onFinish = {
                    navController.navigate(
                        ExerciseRunning(
                            exerciseId = arguments.exerciseId,
                            category = arguments.category,
                            exerciseName = arguments.exerciseName,
                            duration = arguments.duration,
                            video = arguments.video,
                            finishTitle = arguments.finishTitle,
                            advice = arguments.advice,
                        )
                    )
                }
            )
        }

        composable<ExerciseRunning> {
            val arguments = it.toRoute<ExerciseRunning>()

            ExerciseRunningScreen(
                exerciseId = arguments.exerciseId,
                exerciseName = arguments.exerciseName,
                exerciseDuration = arguments.duration,
                exerciseVideo = arguments.video,
                navController = navController,
            )
        }

        composable<ExerciseFinish> { backStackEntry ->
            val arguments = backStackEntry.toRoute<ExerciseFinish>()

            ExerciseFinishScreen(
                idExercise = arguments.exerciseId,
                category = arguments.category,
                exerciseName = arguments.exerciseName,
                title = arguments.finishTitle,
                advice = arguments.advice,
                buttonText = stringResource(Res.string.complete),
                navController = navController,
            )
        }
        composable<ExerciseEvaluationResult> { backStackEntry ->
            val arguments = backStackEntry.toRoute<ExerciseEvaluationResult>()

            ExerciseEvaluationResult(
                category = arguments.category,
                exerciseName = arguments.exerciseName,
                answerEvaluation = arguments.answerEvaluation,
                navController = navController,
            )
        }
    }
}