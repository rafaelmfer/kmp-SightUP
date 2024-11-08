package com.europa.sightup.presentation.screens.prescription.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.visionHistory.UserHistoryResponse
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

class PrescriptionHistoryViewModel(private val repository: SightUpRepository) : ViewModel() {

    private val _history = MutableStateFlow<UIState<UserHistoryResponse>>(UIState.InitialState())
    val history: StateFlow<UIState<UserHistoryResponse>> = _history.asStateFlow()

    fun getVisionHistory() {
        repository.getUserVisionHistory()
            .onStart {
                _history.update { UIState.Loading() }
            }
            .onEach { list: UserHistoryResponse ->
                _history.update { UIState.Success(list) }
            }
            .catch { error ->
                _history.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }
}