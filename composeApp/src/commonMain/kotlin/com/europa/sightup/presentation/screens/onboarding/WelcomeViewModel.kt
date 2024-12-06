package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.europa.sightup.data.remote.response.ProfileResponse
import com.europa.sightup.data.remote.response.UserResponse
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class WelcomeViewModel(private val repository: SightUpRepository) : ViewModel() {
    // Bottom sheet progress controllers
    var outerStep by mutableStateOf(1) // Steps without the progress bar
    var currentStep by mutableStateOf(1) // Steps with the progress bar

    private val _bottomSheetState = MutableStateFlow(BottomSheetEnum.SHOW)
    val bottomSheetState: StateFlow<BottomSheetEnum> = _bottomSheetState

    fun showBottomSheet() {
        _bottomSheetState.value = BottomSheetEnum.SHOW
    }

    fun hideBottomSheet() {
        _bottomSheetState.value = BottomSheetEnum.HIDE
    }

    // User information
    private val _user = MutableStateFlow<UIState<UserResponse>>(UIState.InitialState())
    val user: MutableStateFlow<UIState<UserResponse>> = _user

    fun getUser() {
        flow {
            val userResponse = withContext(Dispatchers.IO) {
                repository.getUserInfo()
            }
            emit(userResponse)
        }
            .onStart {
                _user.update { UIState.Loading() }
            }
            .onEach { response: UserResponse? ->
                if (response != null) {
                    println("User: $response")
                    _user.update {
                        UIState.Success(response)
                    }
                } else {
                    _user.update { UIState.Error("User data not found") }
                }
            }
            .catch { error ->
                _user.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .onCompletion {
                println("User:" + _user.value)
            }
            .launchIn(viewModelScope)
    }

    // User profile data
    private var userProfile by mutableStateOf(UserProfile())

    fun updateProfileData(
        username: String? = null,
        birthday: String? = null,
        gender: String? = null,
        unit: String? = null,
        goal: List<String>? = null,
        often: String? = null,
    ) {
        userProfile = userProfile.copy(
            username = username ?: userProfile.username,
            birthday = birthday ?: userProfile.birthday,
            gender = gender ?: userProfile.gender,
            unit = unit ?: userProfile.unit,
            goal = goal ?: userProfile.goal,
            often = often ?: userProfile.often
        )
        println("Updated UserProfile: $userProfile")
    }

    private val _profileState = MutableStateFlow<UIState<ProfileResponse>>(UIState.InitialState())
    val profileState: StateFlow<UIState<ProfileResponse>> = _profileState

    fun saveSetupProfile() {
        val goals = userProfile.goal?.joinToString(", ") ?: ""

        repository.setupProfile(
            userName = userProfile.username,
            birthday = userProfile.birthday?.toIntOrNull(),
            gender = userProfile.gender,
            goal = goals,
            frequency = userProfile.often,
            unit = userProfile.unit
        )
            .onStart {
                println("Saving profile to database...")
                _profileState.update { UIState.Loading() }
            }
            .onEach { response ->
                println("Profile saved successfully: $response")
                _profileState.update { UIState.Success(response) }
            }
            .catch { error ->
                println("Error saving profile: ${error.message}")
                _profileState.update { UIState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }

    data class UserProfile(
        var username: String? = null,
        var birthday: String? = null,
        var gender: String? = null,
        var unit: String? = null,
        var goal: List<String>? = null,
        var often: String? = null,
    )
}