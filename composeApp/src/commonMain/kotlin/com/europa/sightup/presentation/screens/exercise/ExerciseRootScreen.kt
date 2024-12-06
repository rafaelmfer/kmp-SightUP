package com.europa.sightup.presentation.screens.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.presentation.designsystem.components.CardExercise
import com.europa.sightup.presentation.designsystem.components.SDSFilterChip
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseDetails
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExerciseRootScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<ExerciseViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getExercises()
    }
    val state by viewModel.exercise.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        SDSTopBar(
            title = "Eye Exercises",
            iconLeftVisible = false,
            iconRightVisible = false,
        )
        when (state) {
            is UIState.InitialState -> {}
            is UIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val exercises = (state as UIState.Success).data
                ExerciseScreenContent(
                    navController = navController,
                    exercises = exercises,
                )
            }

            is UIState.Error -> {
                Text(text = "Error: ${(state as UIState.Error).message}")
            }
        }
    }
}

@Composable
private fun ExerciseScreenContent(
    navController: NavController,
    exercises: List<ExerciseResponse>,
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredExercises = if (selectedFilter == "All") {
        exercises
    } else {
        exercises.filter { it.helps.contains(selectedFilter) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterChips(
            selectedFilter = selectedFilter,
            onChipClick = { text ->
                selectedFilter = text
            }
        )
        ExerciseList(exercises = filteredExercises, navController = navController)
    }
}

@Composable
private fun FilterChips(onChipClick: (String) -> Unit, selectedFilter: String) {
    val listOfFilters = listOf(
        "All",
        "Eye Strain",
        "Dry Eyes",
        "Red Eyes",
        "Irritated Eyes",
        "Watery Eyes",
        "Itchy Eyes"
    )

    LazyRow(
        contentPadding = PaddingValues(
            start = SightUPTheme.spacing.spacing_side_margin,
            top = SightUPTheme.spacing.spacing_sm,
            end = SightUPTheme.spacing.spacing_side_margin,
            bottom = SightUPTheme.spacing.spacing_base
        ),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(listOfFilters) { filter ->
            SDSFilterChip(
                text = filter,
                isSelected = filter == selectedFilter,
                onClick = {
                    onChipClick(filter)
                }
            )
            if (filter != listOfFilters.last()) {
                Spacer(Modifier.width(SightUPTheme.spacing.spacing_xs))
            }
        }
    }
}

@Composable
private fun ExerciseList(exercises: List<ExerciseResponse>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(exercises) { index, exercise ->
            ExerciseItemCard(exercise = exercise, index = index, navController = navController)
        }
    }
}

@Composable
private fun ExerciseItemCard(exercise: ExerciseResponse, index: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SightUPTheme.spacing.spacing_side_margin,
                vertical = SightUPTheme.spacing.spacing_xs
            )
    ) {
        CardExercise(
            exercise = exercise,
            iconWhite = index == 1 || index == 2,
            onClick = {
                navController.navigate(
                    ExerciseDetails(
                        exerciseId = exercise.id,
                        exerciseName = exercise.title,
                        category = exercise.category,
                        motivation = exercise.motivation,
                        duration = exercise.duration,
                        imageInstruction = exercise.imageInstruction,
                        video = exercise.video,
                        finishTitle = exercise.finishTitle,
                        advice = exercise.advice
                    )
                )
            },
            modifier = Modifier
        )
    }
}