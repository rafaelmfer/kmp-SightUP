package com.europa.sightup.presentation.screens.exercise.evaluation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.designsystem.components.hideBottomSheetWithAnimation
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.Moods
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.goBackToExerciseHome
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.add
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.evaluate_exercise_good
import sightupkmpapp.composeapp.generated.resources.evaluate_exercise_neutral
import sightupkmpapp.composeapp.generated.resources.evaluate_exercise_tired
import sightupkmpapp.composeapp.generated.resources.tip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEvaluationResult(
    category: String = "",
    exerciseName: String = "",
    answerEvaluation: String = "",
    navController: NavController? = null,
    modifier: Modifier = Modifier,
) {
    // TODO: Get if the user is in a program
    val inUserProgram = true
    // TODO: Get if the user has a schedule for this exercise today`
    val hasScheduleForThisToday = true

    var scheduleSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }
    var bottomSheetTitle by remember { mutableStateOf("") }

    val mood = Moods.fromString(answerEvaluation)

    val image = when (mood) {
        Moods.VERY_GOOD,
        Moods.GOOD,
            -> Res.drawable.evaluate_exercise_good

        Moods.MODERATE -> Res.drawable.evaluate_exercise_neutral

        Moods.ADD -> Res.drawable.add

        Moods.POOR,
        Moods.VERY_POOR,
            -> Res.drawable.evaluate_exercise_tired
    }
    val title = when (mood) {
        Moods.VERY_GOOD,
        Moods.GOOD,
            -> "It's great to know you're feeling better now!"

        Moods.MODERATE -> "Keep up the great work!"

        Moods.ADD -> "Add new Icon"

        Moods.POOR,
        Moods.VERY_POOR,
            -> "We’re sorry to know your eyes are still tired."
    }

    val subtitle = when (mood) {
        Moods.VERY_GOOD,
        Moods.GOOD,
            -> "We'll add this to your routine, but feel free to adjust it as you prefer."

        Moods.MODERATE -> "We believe that after a few more exercises, you'll really notice the difference, and your eyes will feel much better!"

        Moods.ADD -> "Adding new Icon"

        Moods.POOR,
        Moods.VERY_POOR,
            -> "We’ll recommend new exercises to boost your eyesight and help you to feel better."
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SDSTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SightUPTheme.spacing.spacing_sm),
            title = "",
            iconLeftVisible = false,
            iconRightVisible = true,
            iconRight = Res.drawable.close,
            onRightButtonClick = {
                navController?.goBackToExerciseHome()
            }
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        Image(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                )
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        Text(
            text = title,
            style = SightUPTheme.textStyles.h4,
            textAlign = TextAlign.Center,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                )
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_4))
        Text(
            text = subtitle,
            style = SightUPTheme.textStyles.body,
            textAlign = TextAlign.Center,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                )
        )
        if (hasScheduleForThisToday) {
            Spacer(Modifier.height(SightUPTheme.sizes.size_16))
            Text(
                text = "You have another section scheduled at:",
                style = SightUPTheme.textStyles.body,
                textAlign = TextAlign.Center,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = SightUPTheme.spacing.spacing_side_margin,
                        end = SightUPTheme.spacing.spacing_side_margin,
                    )
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_12))
            Text(
                text = "2:00 pm",
                style = SightUPTheme.textStyles.h5,
                textAlign = TextAlign.Center,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = SightUPTheme.spacing.spacing_side_margin,
                        end = SightUPTheme.spacing.spacing_side_margin,
                    )
            )
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_36))
        if (mood == Moods.VERY_GOOD || mood == Moods.GOOD) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = SightUPTheme.spacing.spacing_side_margin,
                        end = SightUPTheme.spacing.spacing_side_margin,
                    )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.tip),
                    contentDescription = null
                )
                Text(
                    text = "Help your friends on their journey to improve their vision.",
                    style = SightUPTheme.textStyles.body,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(SightUPTheme.sizes.size_36))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_lg
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
        ) {
            if (inUserProgram) {
                SDSButton(
                    text = "Reschedule",
                    onClick = {
                        bottomSheetTitle = "Change Schedule"
                        scheduleSheetVisibility = BottomSheetEnum.SHOW
                    },
                    buttonStyle = ButtonStyle.OUTLINED,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(ONE_FLOAT),
                )
                SDSButton(
                    text = if (mood == Moods.VERY_GOOD || mood == Moods.GOOD) "Share" else "Eye Exercises",
                    onClick = {
                        if (mood == Moods.VERY_GOOD || mood == Moods.GOOD) {
                            // TODO: Implement Share
                        } else {
                            navController?.goBackToExerciseHome()
                        }

                    },
                    buttonStyle = ButtonStyle.PRIMARY,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(ONE_FLOAT),
                )
            } else {
                if (mood == Moods.VERY_GOOD || mood == Moods.GOOD) {
                    SDSButton(
                        text = "Set Schedule",
                        onClick = {
                            bottomSheetTitle = "Set Recurrence"
                            scheduleSheetVisibility = BottomSheetEnum.SHOW
                        },
                        buttonStyle = ButtonStyle.OUTLINED,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(ONE_FLOAT),
                    )
                }
                SDSButton(
                    text = if (mood == Moods.VERY_GOOD || mood == Moods.GOOD) "Share" else "Eye Exercises",
                    onClick = {
                        if (mood == Moods.VERY_GOOD || mood == Moods.GOOD) {
                            // TODO: Implement Share
                        } else {
                            navController?.goBackToExerciseHome()
                        }
                    },
                    buttonStyle = ButtonStyle.PRIMARY,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(ONE_FLOAT),
                )
            }
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },
    )


    var frequency by remember { mutableStateOf("") }
    var period by remember { mutableStateOf("") }
    SDSBottomSheet(
        isDismissible = true,
        expanded = scheduleSheetVisibility,
        onExpandedChange = {
            scheduleSheetVisibility = it
        },
        sheetState = sheetState,
        fullHeight = false,
        title = bottomSheetTitle,
        iconRightVisible = false,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            ) {
                Text(
                    text = category,
                    style = SightUPTheme.textStyles.subtitle,
                    color = SightUPTheme.sightUPColors.primary_700,
                )
                Spacer(Modifier.width(SightUPTheme.sizes.size_4))
                Text(
                    text = exerciseName,
                    style = SightUPTheme.textStyles.h4,
                    color = SightUPTheme.sightUPColors.text_primary,
                )
                Spacer(Modifier.height(SightUPTheme.sizes.size_20))
                SDSInput(
                    value = frequency,
                    onValueChange = { newText -> frequency = newText },
                    label = "Select the frequency",
                    hint = "Daily",
                    fullWidth = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                )
                Spacer(Modifier.height(SightUPTheme.sizes.size_20))
                SDSInput(
                    value = period,
                    onValueChange = { newText -> period = newText },
                    label = "Choose how often it should repeat",
                    hint = "3 times per day",
                    fullWidth = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                )
                Spacer(Modifier.height(SightUPTheme.sizes.size_20))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = SightUPTheme.spacing.spacing_lg
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        SightUPTheme.spacing.spacing_sm,
                        Alignment.CenterHorizontally
                    ),
                ) {
                    SDSButton(
                        text = "Cancel",
                        onClick = {
                            scope.hideBottomSheetWithAnimation(
                                sheetState = sheetState,
                                onBottomSheetVisibilityChange = { scheduleSheetVisibility = BottomSheetEnum.HIDE },
                                onFinish = { /* Do nothing */ }
                            )
                        },
                        buttonStyle = ButtonStyle.OUTLINED,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(ONE_FLOAT),
                    )
                    SDSButton(
                        text = "Confirm",
                        onClick = {
                            //TODO: implement on finish changing result screen to success page
                            scope.hideBottomSheetWithAnimation(
                                sheetState = sheetState,
                                onBottomSheetVisibilityChange = { scheduleSheetVisibility = BottomSheetEnum.HIDE },
                                onFinish = {
                                    navController.goBackToExerciseHome()
                                }
                            )
                        },
                        buttonStyle = ButtonStyle.PRIMARY,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(ONE_FLOAT),
                    )
                }
            }
        }
    )
}

