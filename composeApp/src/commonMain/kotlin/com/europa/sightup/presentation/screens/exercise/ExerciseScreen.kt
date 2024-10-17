package com.europa.sightup.presentation.screens.exercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.presentation.designsystem.components.CardExercise
import com.europa.sightup.utils.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExerciseScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<ExerciseViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getExercises()
    }
    val state by viewModel.exercise.collectAsStateWithLifecycle()

    CardExerciseScreen(navController, state)
}

@Composable
fun CardExerciseScreen(
    navController: NavController,
    state: UIState<List<ExerciseResponse>>
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Execise",
                    fontSize = 28.sp
                )

                when (state) {
                    is UIState.Error -> {
                        Text(text = "Error: ${state.message}")
                    }

                    is UIState.InitialState -> {}
                    is UIState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is UIState.Success -> {
                        val exercises = state.data
                        ExerciseList(
                            exercises = exercises,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseList(exercises: List<ExerciseResponse>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(exercises) { exercise ->
            ExerciseItemCard(exercise = exercise)
        }
    }
}

@Composable
fun ExerciseItemCard(exercise: ExerciseResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        CardExercise(Modifier, exercise)
    }
}