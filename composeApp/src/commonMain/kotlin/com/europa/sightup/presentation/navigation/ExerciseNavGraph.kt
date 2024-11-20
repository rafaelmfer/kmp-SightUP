package com.europa.sightup.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.europa.sightup.presentation.designsystem.components.SDSDialog
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseCountdown
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseDetails
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseEvaluationResult
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseFinish
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseInit
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRunning
import com.europa.sightup.presentation.screens.CountdownScreen
import com.europa.sightup.presentation.screens.exercise.ExerciseRootScreen
import com.europa.sightup.presentation.screens.exercise.details.ExerciseDetailsScreen
import com.europa.sightup.presentation.screens.exercise.evaluation.ExerciseEvaluationResult
import com.europa.sightup.presentation.screens.exercise.finish.ExerciseFinishScreen
import com.europa.sightup.presentation.screens.exercise.running.ExerciseRunningScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.goBackToExerciseHome
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.complete
import sightupkmpapp.composeapp.generated.resources.start

fun NavGraphBuilder.exerciseNavGraph(navController: NavHostController) {
    navigation<ExerciseInit>(
        startDestination = ExerciseRoot,
    ) {
        composable<ExerciseRoot>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            ExerciseRootScreen(navController = navController)
        }

        composable<ExerciseDetails>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { backStackEntry ->
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

        composable<ExerciseCountdown>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { navBackStackEntry ->
            var showDialog by remember { mutableStateOf(false) }
            val arguments = navBackStackEntry.toRoute<ExerciseCountdown>()

            CountdownScreen(
                animationPath = "files/countdown_animation.json",
                titleHeader = arguments.exerciseName,
                onLeftButtonHeaderClick = {
                    navController.popBackStack()
                },
                onRightButtonHeaderClick = {
                    showDialog = true
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
                            musicAudioGuidanceEnabled = arguments.musicAudioGuidanceEnabled
                        )
                    )
                }
            )

            SDSDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = it },
                title = "Exit exercise?",
                onClose = null,
                content = { _ ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SightUPTheme.spacing.spacing_md)
                    ) {
                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                        Text(
                            text = " If you exit, you’ll need to restart the exercise.",
                            style = SightUPTheme.textStyles.body,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    }
                },
                onPrimaryClick = { showDialog = false },
                buttonPrimaryText = "Continue",
                onSecondaryClick = {
                    navController.goBackToExerciseHome()
                },
                buttonSecondaryText = "Exit",
            )
        }

        composable<ExerciseRunning>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) {
            val arguments = it.toRoute<ExerciseRunning>()

            ExerciseRunningScreen(
                exerciseId = arguments.exerciseId,
                exerciseName = arguments.exerciseName,
                exerciseDuration = arguments.duration,
                exerciseVideo = arguments.video,
                musicAudioGuidanceEnabled = arguments.musicAudioGuidanceEnabled,
                navController = navController,
            )
        }

        composable<ExerciseFinish>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { backStackEntry ->
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
        composable<ExerciseEvaluationResult>(
            enterTransition = { slideInFromRight() },
            exitTransition = { slideOutToLeft() },
            popEnterTransition = { slideInFromLeft() },
            popExitTransition = { slideOutToRight() }
        ) { backStackEntry ->
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