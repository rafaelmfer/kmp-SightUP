package com.europa.sightup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.PostResponse
import com.europa.sightup.data.repository.JsonPlaceholderRepository
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

class MainViewModel(private val repository: JsonPlaceholderRepository) : ViewModel() {

    private val _products = MutableStateFlow<UIState<List<PostResponse>>>(UIState.InitialState())
    val products: StateFlow<UIState<List<PostResponse>>> = _products.asStateFlow()

    fun getProducts() {
        flow {
            val posts = withContext(Dispatchers.IO) {
                repository.getPosts()
            }
            emit(posts)
        }
            .onStart {
                println("onStart")
                _products.update { UIState.Loading() }
            }
            .onEach { list: List<PostResponse> ->
                println("onEach") // Quando cada lista é emitida
                _products.update { UIState.Success(list) }
            }
            .catch { error ->
                println("Error")
                _products.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .onCompletion {
                println("onCompletion")
                // Here you can do extra something when the flow completes
            }
            .launchIn(viewModelScope)
    }
}