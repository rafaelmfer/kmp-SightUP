package com.europa.sightup.presentation.screens.test.active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object EyeTestRepository {
    var rightEyeResult: String? = null
    var leftEyeResult: String? = null

    fun saveTestResult(eye: String, finalLevel: String?) {
        if (eye == "right") {
            rightEyeResult = finalLevel
        } else if (eye == "left") {
            leftEyeResult = finalLevel
        }
    }
}

class ActiveTestViewModel : ViewModel() {

    sealed class TestState {
        data object InProgress : TestState()
        data object Completed : TestState()
    }

    private var activeTest: ActiveTest = ActiveTest.VisualAcuity

    private val _testState = MutableStateFlow<TestState>(TestState.InProgress)
    val testState: StateFlow<TestState> = _testState

    private var currentEye = "right"

    // EChart Test variables
    private var correctAnswersCount by mutableStateOf(0)
    private var wrongAnswersCount by mutableStateOf(0)
    private var currentRow by mutableStateOf(1)

    var currentEFormat by mutableStateOf(EChart.getRandomIcon(1))
        private set

    private var lastDirection: EChart? = null // To ensure the same direction is not repeated


    // Eye Test Functions
    fun setActiveTest(test: ActiveTest) {
        activeTest = test
    }

    fun updateCurrentEye(eye: String) {
        currentEye = eye
    }

    private fun displayFinalResults() {
        _testState.value = TestState.Completed
    }

    fun testButton(navController: NavController) {
        if (activeTest == ActiveTest.VisualAcuity) {
            wrongAnswersCount++
            handleEChartSelection(null, navController)
        } else if (activeTest == ActiveTest.Astigmatism) {
            endTestForCurrentEye(navController, "0")
        }
    }

    fun onClickChangeUI(input: Any, navController: NavController) {
        when (activeTest) {
            ActiveTest.VisualAcuity -> {
                if (input is EChart) {
                    handleEChartSelection(input, navController)
                } else {
                    println("Error: Input type mismatch for VisualAcuity test")
                }
            }

            ActiveTest.Astigmatism -> {
                if (input is Int) {
                    handleAstigmatismSelection(input, navController)
                } else {
                    println("Error: Input type mismatch for Astigmatism test")
                }
            }
        }
    }

    private fun endTestForCurrentEye(
        navController: NavController, result: String?,
    ) {
        EyeTestRepository.saveTestResult(eye = currentEye, finalLevel = result)

        if (currentEye == "right") {
            currentEye = "left"
            navController.popBackStack()
        } else {
            displayFinalResults()
        }
    }

    // Visual Acuity Test functions
    private fun handleEChartSelection(
        selectedDirection: EChart?,
        navController: NavController,
    ) {
        selectedDirection?.let {
            if (selectedDirection == currentEFormat?.direction) {
                correctAnswersCount++
            } else {
                wrongAnswersCount++
            }
        }

        // To check if the test is completed or move to the next row
        when {
            correctAnswersCount == 3 -> {
                if (currentRow >= 8) {
                    endTestForCurrentEye(navController, EChart.getScoreForRow(currentRow))
                    return
                }
                currentRow++
                resetCounters()
            }

            wrongAnswersCount >= 3 -> {
                val finalRow = if (currentRow > 1) currentRow - 1 else currentRow
                endTestForCurrentEye(navController, EChart.getScoreForRow(finalRow))
                return
            }
        }
        currentEFormat = EChart.getRandomIcon(currentRow, lastDirection)
        lastDirection = currentEFormat?.direction
    }

    private fun resetCounters() {
        correctAnswersCount = 0
        wrongAnswersCount = 0
    }

    // Function for the Astigmatism test
    private fun handleAstigmatismSelection(direction: Int, navController: NavController) {
        val angle = AstigmatismChart.getAngleForDirection(direction)
        endTestForCurrentEye(navController, "$angle")
    }
}


