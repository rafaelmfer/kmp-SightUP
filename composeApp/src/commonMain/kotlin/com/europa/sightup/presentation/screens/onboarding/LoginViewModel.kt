package com.europa.sightup.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.auth.LoginEmailResponse
import com.europa.sightup.data.remote.response.auth.LoginResponse
import com.europa.sightup.data.repository.SightUpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

sealed interface LoginUIState {
    data object InitialState : LoginUIState
    data object Loading : LoginUIState

    data class UserFound(val response: LoginEmailResponse) : LoginUIState
    data object LoginSuccess : LoginUIState

    data class Error(val errorMessage: String) : LoginUIState
}

class LoginViewModel(private val repository: SightUpRepository) : ViewModel() {

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }

    private val _state = MutableStateFlow<LoginUIState>(LoginUIState.InitialState)
    val state: StateFlow<LoginUIState> = _state.asStateFlow()

    fun checkEmail(email: String) {
        repository.checkEmail(email)
            .onStart {
                _state.update { LoginUIState.Loading }
            }
            .onEach { response: LoginEmailResponse ->
                if (response.success) {
                    _state.update { LoginUIState.UserFound(response) }
                } else {
                    _state.update { LoginUIState.Error(response.message) }
                }
            }
            .catch { error ->
                _state.update { LoginUIState.Error(error.message ?: UNKNOWN_ERROR) }
            }
            .launchIn(viewModelScope)
    }

    fun doLogin(email: String, password: String) {
        repository.doLogin(email, password)
            .onStart {
                _state.update { LoginUIState.Loading }
            }
            .onEach { response: LoginResponse ->
                _state.update { LoginUIState.LoginSuccess }
            }
            .catch { error ->
                _state.update { LoginUIState.Error(error.message ?: UNKNOWN_ERROR) }
            }
            .launchIn(viewModelScope)
    }

    fun doLoginWithProvider(idToken: String) {
        repository.doLoginWithProvider(idToken)
            .onStart {
                _state.update { LoginUIState.Loading }
            }
            .onEach { response: LoginResponse ->
                _state.update { LoginUIState.LoginSuccess }
            }
            .catch { error ->
                _state.update { LoginUIState.Error(error.message ?: UNKNOWN_ERROR) }
            }
            .launchIn(viewModelScope)
    }
}