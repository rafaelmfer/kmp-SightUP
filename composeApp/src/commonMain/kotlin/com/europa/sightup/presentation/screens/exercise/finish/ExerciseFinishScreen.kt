package com.europa.sightup.presentation.screens.exercise.finish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSCardExerciseBottom
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseEvaluationResult
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseRoot
import com.europa.sightup.presentation.screens.JoinInBottomSheet
import com.europa.sightup.presentation.screens.exercise.evaluation.EvaluateExercise
import com.europa.sightup.presentation.screens.exercise.evaluation.ExerciseEvaluationResultViewModel
import com.europa.sightup.presentation.screens.onboarding.LoginSignUpScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.isUserLoggedIn
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseFinishScreen(
    idExercise: String = "",
    category: String = "",
    exerciseName: String = "",
    title: String = "",
    advice: String = "",
    buttonText: String = "",
    navController: NavController? = null,
) {
    val viewModel = koinViewModel<ExerciseFinishScreenViewModel>()
    val userIsLogged = isUserLoggedIn
    // Collect the save exercise state
    val saveExerciseState by viewModel.saveExerciseState.collectAsStateWithLifecycle()

    // Trigger save operation when the screen is loaded
    LaunchedEffect(Unit) {
        viewModel.saveDailyExercise("", idExercise)
    }

    var evaluateSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }
    var joinInSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }
    var loginSignUpSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }

    var titleAnimation by remember { mutableStateOf("") }
    var finishAnimation by remember { mutableStateOf("") }
    when (exerciseName) {
        "Circular Motion" -> {
            titleAnimation = "This exercise helps improve the flexibility of your eye muscles."
            finishAnimation = "files/circular_motion_result.json"
        }

        "Blink Relaxation" -> {
            titleAnimation = "Blinking helps to refresh your eyes and maintain moisture."
            finishAnimation = "files/blink_relaxation_result.json"
        }

        "Color Game" -> {
            titleAnimation = "This exercise enhances your ability to track moving objects and improves visual coordination."
            finishAnimation = "files/coordination_color_game_result.json"
        }

        else -> {
            titleAnimation = "Stretching your eyes helps relieve tension and reduces fatigue from close-up work."
            finishAnimation = "files/distension_stretching_result.json"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        Header()
        Spacer(Modifier.weight(ONE_FLOAT))
        Animation(finishAnimation)
        Spacer(Modifier.height(SightUPTheme.sizes.size_40))
        Text(
            text = titleAnimation,
            style = SightUPTheme.textStyles.large,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                )
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        SDSCardExerciseBottom(
            category = "",
            title = title,
            motivation = advice,
            duration = 0,
            buttonText = buttonText,
            onClick = {
                if (userIsLogged) {
                    evaluateSheetVisibility = BottomSheetEnum.SHOW
                } else {
                    joinInSheetVisibility = BottomSheetEnum.SHOW
                }
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

    JoinInBottomSheet(
        bottomSheetVisible = joinInSheetVisibility,
        onBottomSheetVisibilityChange = {
            joinInSheetVisibility = it
        },
        onCloseClick = {
            navController?.popBackStack<ExerciseRoot>(inclusive = false)
        },
        onCloseBottomSheet = {
            loginSignUpSheetVisibility = BottomSheetEnum.SHOW
        }
    )

    LoginSignUpScreen(
        bottomSheetVisible = loginSignUpSheetVisibility,
        onBottomSheetVisibilityChange = {
            loginSignUpSheetVisibility = it
        },
        onSuccessfulLogin = {
            navController?.popBackStack<ExerciseRoot>(inclusive = false)
        },
        navController = navController
    )

    SDSBottomSheet(
        title = "",
        isDismissible = true,
        expanded = evaluateSheetVisibility,
        onExpandedChange = {
            evaluateSheetVisibility = it
        },
        fullHeight = true,
        iconRightVisible = true,
        onIconRightClick = {
            navController?.popBackStack<ExerciseRoot>(inclusive = false)
        },
        onDismiss = {
            evaluateSheetVisibility = BottomSheetEnum.HIDE
        },
        sheetContent = {
            EvaluateExercise(
                onSaveClicked = { answer ->
                    navController?.navigate(
                        ExerciseEvaluationResult(
                            exerciseId = idExercise,
                            category = category,
                            exerciseName = exerciseName,
                            answerEvaluation = answer
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = SightUPTheme.spacing.spacing_side_margin,
                        end = SightUPTheme.spacing.spacing_side_margin,
                        bottom = SightUPTheme.spacing.spacing_lg,
                    )
            )
        }
    )
}

@Composable
private fun Header() {
    SDSTopBar(
        title = "",
        iconLeftVisible = false,
        iconRightVisible = false,
        modifier = Modifier
            .padding(
                horizontal = SightUPTheme.spacing.spacing_xs,
            )
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Animation(animationPath: String) {
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
    Image(
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress },
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 72.dp,
            )
            .aspectRatio(0.8f, matchHeightConstraintsFirst = true)
            .background(Color.Transparent),
        contentDescription = null
    )
}