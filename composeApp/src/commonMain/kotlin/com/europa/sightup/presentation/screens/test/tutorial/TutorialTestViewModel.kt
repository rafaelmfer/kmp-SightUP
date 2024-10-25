package com.europa.sightup.presentation.screens.test.tutorial

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.europa.sightup.presentation.designsystem.components.TestModeEnum

class TutorialTestViewModel : ViewModel() {
    var currentStep by mutableStateOf(1)
        private set

    var eyeTested by mutableStateOf("right")
        private set

    var selectedMode by mutableStateOf(TestModeEnum.Touch)
        private set

    fun advanceStep() {
        currentStep++
    }

    fun goBackStep() {
        if (currentStep > 1) currentStep--
    }

    fun updateEyeTested(eye: String) {
        eyeTested = eye
    }

    fun updateSelectedMode(mode: TestModeEnum) {
        selectedMode = mode
    }
}
