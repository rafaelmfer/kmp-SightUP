package com.europa.sightup.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.request.assessment.DailyCheckRequest
import com.europa.sightup.data.remote.response.assessment.DailyCheckResponse
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

class DailyCheckViewModel(private val repository: SightUpRepository) : ViewModel() {

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
}