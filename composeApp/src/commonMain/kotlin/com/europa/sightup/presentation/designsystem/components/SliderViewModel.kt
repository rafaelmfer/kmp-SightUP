package com.europa.sightup.presentation.designsystem.components

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SliderViewModel : ViewModel() {
    private val _activeButton = MutableStateFlow("")
    val activeButton: StateFlow<String> get() = _activeButton

    fun setActiveButton(button: String) {
        _activeButton.value = button
    }

    private val _selectedConditions = mutableStateListOf<String>()
    val selectedConditions: List<String> get() = _selectedConditions

    fun updateSelectedConditions(conditions: List<String>) {
        _selectedConditions.clear()
        _selectedConditions.addAll(conditions)
    }

}