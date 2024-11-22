package com.europa.sightup.presentation.screens.exercise.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.DailyExerciseResponse
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class ExerciseEvaluationResultViewModel(private val repository: SightUpRepository) : ViewModel() {

    private val _dailyExerciseList = MutableStateFlow<UIState<List<DailyExerciseResponse>>>(UIState.InitialState())
    val dailyExerciseList: StateFlow<UIState<List<DailyExerciseResponse>>> = _dailyExerciseList.asStateFlow()

    fun getDailyExerciseList() {
        repository.getDailyExercise()
            .onStart {
                _dailyExerciseList.update { UIState.Loading() }
            }
            .onEach { list: List<DailyExerciseResponse> ->
                println("Received daily exercises: $list")
                _dailyExerciseList.update { UIState.Success(list) }
            }
            .catch { error ->
                _dailyExerciseList.update { UIState.Error(error.message ?: "Unknown error") }
                println("Error fetching daily exercises: ${error.message}")
            }
            .onCompletion { println("Completed fetching daily exercises.") }
            .launchIn(viewModelScope)
    }
}