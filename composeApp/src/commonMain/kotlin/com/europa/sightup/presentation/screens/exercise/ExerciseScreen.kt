package com.europa.sightup.presentation.screens.exercise

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.presentation.designsystem.components.CardExercise
import com.europa.sightup.presentation.designsystem.components.SDSFilterChip
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExerciseScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<ExerciseViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getExercises()
    }
    val state by viewModel.exercise.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SDSTopBar(
            title = "Eye Exercises",
            iconLeftVisible = false,
            iconRightVisible = false,
        )

        when (state) {
            is UIState.InitialState -> {}
            is UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Success -> {
                val exercises = (state as UIState.Success<List<ExerciseResponse>>).data
                ExerciseScreenContent(
                    navController = navController,
                    exercises = exercises,
                )
            }

            is UIState.Error -> {
                Text(text = "Error: ${(state as UIState.Error<List<ExerciseResponse>>).message}")
            }
        }
    }
}

@Composable
private fun ExerciseScreenContent(
    navController: NavController,
    exercises: List<ExerciseResponse>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Filter according to your eye condition:",
            style = SightUPTheme.textStyles.body,
            modifier = Modifier
                .padding(
                    top = SightUPTheme.spacing.spacing_base,
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                )
        )
        FilterChips(
            onChipClick = { text ->
                //TODO: Handle chip click to filter exercises list
            }
        )
        ExerciseList(exercises = exercises, navController = navController)
    }
}

@Composable
private fun FilterChips(onChipClick: (String) -> Unit) {
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
        items(listOfFilters) {
            SDSFilterChip(
                text = it,
                isSelected = it == "All",
                onClick = { text ->
                    onChipClick(text)
                }
            )
            if (it != listOfFilters.last()) {
                Spacer(Modifier.width(SightUPTheme.spacing.spacing_xs))
            }
        }
    }
}

@Composable
private fun ExerciseList(exercises: List<ExerciseResponse>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(exercises) { exercise ->
            ExerciseItemCard(exercise = exercise, navController = navController)
        }
    }
}

@Composable
private fun ExerciseItemCard(exercise: ExerciseResponse, navController: NavController) {
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
            modifier = Modifier.clickable {
                //TODO: Handle click on exercise item
                navController
            }
        )
    }
}