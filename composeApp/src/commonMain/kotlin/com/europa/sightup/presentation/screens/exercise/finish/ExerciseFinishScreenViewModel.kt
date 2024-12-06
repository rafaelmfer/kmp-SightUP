package com.europa.sightup.presentation.screens.exercise.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.DailyExerciseResponse
import com.europa.sightup.data.remote.response.UpdateDailyExerciseResponse
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

class ExerciseFinishScreenViewModel(private val repository: SightUpRepository) : ViewModel() {
    private val _saveExerciseState = MutableStateFlow<UIState<UpdateDailyExerciseResponse>>(UIState.InitialState())
    val saveExerciseState: StateFlow<UIState<UpdateDailyExerciseResponse>> = _saveExerciseState.asStateFlow()

    fun saveDailyExercise(exerciseId: String, feeling: String) {
        repository.saveDailyExercise(exerciseId, feeling)
            .onStart {
                _saveExerciseState.update { UIState.Loading() }
            }
            .onEach { response ->
                println("Exercise saved successfully: $response")
                _saveExerciseState.update { UIState.Success(response) }
            }
            .catch { error ->
                println("Error saving exercise: ${error.message}")
                _saveExerciseState.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    private val _dailyExerciseList = MutableStateFlow<UIState<List<DailyExerciseResponse>>>(UIState.InitialState())
    val dailyExerciseList: StateFlow<UIState<List<DailyExerciseResponse>>> = _dailyExerciseList.asStateFlow()

    fun getDailyExerciseList() {
        repository.getDailyExercise()
            .onStart {
                _dailyExerciseList.update { UIState.Loading() }
            }
            .onEach { list: List<DailyExerciseResponse> ->
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