package com.europa.sightup.presentation.screens.test.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.request.visionHistory.ResultRequest
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.visionHistory.VisionHistoryResponse
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.presentation.screens.test.active.ActiveTest
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

class TestResultViewModel(private val repository: SightUpRepository) : ViewModel() {

    private var activeTest: ActiveTest = ActiveTest.VisualAcuity
        private set

    private val _newTestResult = MutableStateFlow<UIState<VisionHistoryResponse>>(UIState.InitialState())
    val newTestResult: StateFlow<UIState<VisionHistoryResponse>> = _newTestResult.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    // Eye Test Functions
    fun setActiveTest(test: ActiveTest) {
        activeTest = test
    }

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
                _newTestResult.update { UIState.Loading() }
            }
            .onEach { response: VisionHistoryResponse ->
                _newTestResult.update { UIState.Success(response) }
                _saveSuccess.value = true
            }
            .catch { error ->
                _newTestResult.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }

    // Fetch the test to show in the card and also to send through the navigation
    private val _tests = MutableStateFlow<UIState<List<TestResponse>>>(UIState.InitialState())
    val test: StateFlow<UIState<List<TestResponse>>> = _tests.asStateFlow()

    fun getTests() {
        repository.getTests()
            .onStart {
                _tests.update { UIState.Loading() }
            }
            .onEach { list: List<TestResponse> ->
                _tests.update { UIState.Success(list) }
            }
            .catch { error ->
                _tests.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .onCompletion {
                println("onCompletion")
                // Here you can do extra something when the flow completes
            }
            .launchIn(viewModelScope)
    }
}


