package com.europa.sightup.presentation.screens.test.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.TestResponse
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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class TestViewModel(private val repository: SightUpRepository) : ViewModel() {

    private val _tests = MutableStateFlow<UIState<List<TestResponse>>>(UIState.InitialState())
    val test: StateFlow<UIState<List<TestResponse>>> = _tests.asStateFlow()


    fun getTests() {
        flow {
            val test = withContext(Dispatchers.IO) {
                repository.getTests()
            }
            emit(test)
        }
            .onStart {
                println("onStart")
                _tests.update { UIState.Loading() }
            }
            .onEach { list: List<TestResponse> ->
                println("onEach")
                _tests.update { UIState.Success(list) }
            }
            .catch { error ->
                println("Error")
                _tests.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .onCompletion {
                println("onCompletion")
                // Here you can do extra something when the flow completes
            }
            .launchIn(viewModelScope)
    }
}