package com.europa.sightup.presentation.screens.prescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.request.prescription.AddPrescriptionRequest
import com.europa.sightup.data.remote.response.AddPrescriptionResponse
import com.europa.sightup.data.remote.response.UserResponse
import com.europa.sightup.data.remote.response.visionHistory.HistoryTestResponse
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

sealed class VisionRecordUIState {
    data object InitialState : VisionRecordUIState()
    data object Loading : VisionRecordUIState()
    data object NoData : VisionRecordUIState()
    data class VisionRecord(val history: HistoryTestResponse) : VisionRecordUIState()
    data class Error(val message: String) : VisionRecordUIState()
}

sealed class PrescriptionRecordUIState {
    data object InitialState : PrescriptionRecordUIState()
    data object Loading : PrescriptionRecordUIState()
    data object PrescriptionAdded : PrescriptionRecordUIState()
    data class Error(val message: String) : PrescriptionRecordUIState()
}

class PrescriptionMainViewModel(private val repository: SightUpRepository) : ViewModel() {

    private val _records = MutableStateFlow<VisionRecordUIState>(VisionRecordUIState.InitialState)
    val records: StateFlow<VisionRecordUIState> = _records.asStateFlow()

    private val _prescription = MutableStateFlow<PrescriptionRecordUIState>(PrescriptionRecordUIState.InitialState)
    val prescription: StateFlow<PrescriptionRecordUIState> = _prescription.asStateFlow()

    private val _userPrescription = MutableStateFlow<UIState<UserResponse>>(UIState.InitialState())
    val userPrescription: StateFlow<UIState<UserResponse>> = _userPrescription.asStateFlow()

    fun getLatestRecords() {
        repository.getUserLatestVisionHistory()
            .onStart {
                _records.update { VisionRecordUIState.Loading }
            }
            .onEach { test: HistoryTestResponse? ->
                if (test == null) {
                    _records.update { VisionRecordUIState.NoData }
                } else {
                    _records.update { VisionRecordUIState.VisionRecord(test) }
                }
            }
            .catch { error ->
                _records.update { VisionRecordUIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    fun addPrescription(prescription: AddPrescriptionRequest) {
        repository.addPrescription(prescription)
            .onStart {
                _prescription.update { PrescriptionRecordUIState.Loading }
            }
            .onEach { response: AddPrescriptionResponse ->
                _prescription.update { PrescriptionRecordUIState.PrescriptionAdded }
            }
            .catch { error ->
                _prescription.update { PrescriptionRecordUIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    fun getUser() {
        repository.getUser()
            .onStart {
                _userPrescription.update { UIState.Loading() }
            }
            .onEach { user: UserResponse ->
                _userPrescription.update { UIState.Success(user) }
            }
            .catch { error ->
                _userPrescription.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    fun resetPrescriptionState() {
        _prescription.update { PrescriptionRecordUIState.InitialState }
    }
}