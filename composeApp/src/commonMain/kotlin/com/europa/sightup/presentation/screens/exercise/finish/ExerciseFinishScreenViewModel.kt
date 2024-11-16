package com.europa.sightup.presentation.screens.exercise.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.UpdateDailyExerciseResponse
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class ExerciseFinishScreenViewModel (private val repository: SightUpRepository): ViewModel() {
    private val _saveExerciseState = MutableStateFlow<UIState<UpdateDailyExerciseResponse>>(UIState.InitialState())
    val saveExerciseState: StateFlow<UIState<UpdateDailyExerciseResponse>> = _saveExerciseState.asStateFlow()

    // Function to save daily exercise result
    fun saveDailyExercise(userIdentifier: String, exerciseId: String) {
        repository.saveDailyExercise(userIdentifier, exerciseId)
            .onStart {
                _saveExerciseState.update { UIState.Loading() }
            }
            .onEach { response ->
                _saveExerciseState.update { UIState.Success(response) }
            }
            .catch { error ->
                _saveExerciseState.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }
}