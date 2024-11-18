package com.europa.sightup.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.request.assessment.DailyCheckRequest
import com.europa.sightup.data.remote.response.DailyCheckInResponse
import com.europa.sightup.data.remote.response.DailyExerciseMessageResponse
import com.europa.sightup.data.remote.response.UserResponse
import com.europa.sightup.data.remote.response.assessment.DailyCheckResponse
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

class HomeViewModel(private val repository: SightUpRepository) : ViewModel() {
    private val _user = MutableStateFlow<UIState<UserResponse>>(UIState.InitialState())
    val user: StateFlow<UIState<UserResponse>> = _user.asStateFlow()

    fun getUser() {
        repository.getUser()
            .onStart {
                _user.update { UIState.Loading() }
            }
            .onEach { user: UserResponse ->
                _user.update { UIState.Success(user) }
            }
            .catch { error ->
                _user.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }


    private val _dailyCheckGet = MutableStateFlow<UIState<List<DailyCheckInResponse>>>(UIState.InitialState())
    val dailyCheckGet: StateFlow<UIState<List<DailyCheckInResponse>>> = _dailyCheckGet.asStateFlow()

    fun getAllDay() {
        repository.getAllDay()
            .onStart {
                _dailyCheckGet.update { UIState.Loading() }
            }
            .onEach { list: List<DailyCheckInResponse> ->
                _dailyCheckGet.update { UIState.Success(list) }
            }
            .catch { error ->
                _dailyCheckGet.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .onCompletion {
            }
            .launchIn(viewModelScope)
    }


    private val _dailyCheck = MutableStateFlow<UIState<DailyCheckResponse>>(UIState.InitialState())
    val dailyCheck: StateFlow<UIState<DailyCheckResponse>> = _dailyCheck.asStateFlow()

    fun saveDailyCheck(request: DailyCheckRequest) {
        repository.saveDailyCheck(request)
            .onStart {
                _dailyCheck.update { UIState.Loading() }
            }
            .onEach { response: DailyCheckResponse ->
                _dailyCheck.update { UIState.Success(response) }
            }
            .catch { error ->
                println(error.message)
                _dailyCheck.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    fun resetDailyCheckState() {
        _dailyCheck.update { UIState.InitialState() }
    }

    // Exercise list
    private val _dailyExerciseList = MutableStateFlow<UIState<List<DailyExerciseMessageResponse.DailyExerciseResponse>>>(UIState.InitialState())
    val dailyExerciseList: StateFlow<UIState<List<DailyExerciseMessageResponse.DailyExerciseResponse>>> = _dailyExerciseList.asStateFlow()

    fun getDailyExerciseList() {
        repository.getDailyExercise()
            .onStart {
                _dailyExerciseList.update { UIState.Loading() }
            }
            .onEach { list: List<DailyExerciseMessageResponse.DailyExerciseResponse> ->
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