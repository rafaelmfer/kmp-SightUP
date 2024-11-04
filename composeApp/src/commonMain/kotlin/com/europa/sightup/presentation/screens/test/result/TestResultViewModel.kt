package com.europa.sightup.presentation.screens.test.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.request.visionHistory.ResultRequest
import com.europa.sightup.data.remote.response.visionHistory.UserHistoryResponse
import com.europa.sightup.data.remote.response.visionHistory.VisionHistoryResponse
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

class TestResultViewModel(private val repository: SightUpRepository) : ViewModel() {

    private val _newTestResult = MutableStateFlow<UIState<VisionHistoryResponse>>(UIState.InitialState())
    val newTestResult: StateFlow<UIState<VisionHistoryResponse>> = _newTestResult.asStateFlow()

    fun saveVisionTest(
        appTest: Boolean = true,
        testId: String = "",
        testTitle: String = "",
        left: String = "",
        right: String = "",
    ) {
        repository.postUserTestsResult(
            appTest = appTest,
            testId = testId,
            testTitle = testTitle,
            result = ResultRequest(
                left = left,
                right = right
            )
        )
            .onStart {
                println("onStart")
                _newTestResult.update { UIState.Loading() }
            }
            .onEach { response: VisionHistoryResponse ->
                println("onEach")
                _newTestResult.update { UIState.Success(response) }
            }
            .catch { error ->
                println("Error")
                _newTestResult.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    private val _visionHistory = MutableStateFlow<UIState<List<UserHistoryResponse>>>(UIState.InitialState())
    val visionHistory: StateFlow<UIState<List<UserHistoryResponse>>> = _visionHistory.asStateFlow()

    fun getVisionHistory() {
        repository.getUserVisionHistory()
            .onStart {
                println("onStart")
                _visionHistory.update { UIState.Loading() }
            }
            .onEach { response: UserHistoryResponse ->
                println("onEach")
                _visionHistory.update { current ->
                    // Actualiza la lista actual agregando el nuevo elemento
                    val updatedList = (current as? UIState.Success)?.data.orEmpty() + response
                    UIState.Success(updatedList)
                }
            }
            .catch { error ->
                println("Error")
                _visionHistory.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }
}


