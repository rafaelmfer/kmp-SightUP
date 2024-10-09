package com.europa.sightup.presentation.screens.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class ExerciseViewModel(private val repository: SightUpRepository) : ViewModel() {

    private val _exercises = MutableStateFlow<UIState<List<ExerciseResponse>>>(UIState.InitialState())
    val exercise: StateFlow<UIState<List<ExerciseResponse>>> = _exercises.asStateFlow()

    fun getExercises(){
        flow {
            val exercise= withContext(Dispatchers.IO) {
                repository.getExercises()
            }
            emit(exercise)
        }
            .onStart {
                println("onStart")
                _exercises.update { UIState.Loading() }
            }
            .onEach { list: List<ExerciseResponse> ->
                println("onEach")
                _exercises.update { UIState.Success(list) }
            }
            .catch { error ->
                println("Error")
                _exercises.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }
}