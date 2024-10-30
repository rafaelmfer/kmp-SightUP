package com.europa.sightup.presentation.screens.test.active

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.europa.sightup.data.repository.SightUpRepository

class ActiveTestViewModel(private val repository: SightUpRepository) : ViewModel() {

    private var currentEye = "right"
    private var currentEyeFinalLevel by mutableStateOf(1)

    // Control variables for the test
    private var correctAnswersCount by mutableStateOf(0)
    private var wrongAnswersCount by mutableStateOf(0)
    private var currentRow by mutableStateOf(1)

    var currentEFormat by mutableStateOf(EChart.getRandomIcon(currentRow))
        private set

    private fun updateFinalLevel(finalLevel: Int) {
        currentEyeFinalLevel = finalLevel
//        TODO: save the test results in the database
//        repository.saveTestResults(currentEye, finalLevel)
    }

    private fun printTestStatus() {
        println("test -> $currentEye eye: ${EChart.getScoreForRow(currentEyeFinalLevel)}")
    }

    private fun resetCounters() {
        correctAnswersCount = 0
        wrongAnswersCount = 0
    }

    fun updateCurrentEye(eye: String) {
        currentEye = eye
    }

    fun cannotSeeButton(navController: NavController) {
        wrongAnswersCount++
        checkAnswerAndChangeE(null, navController)
    }

    fun checkAnswerAndChangeE(
        selectedDirection: EChart?,
        navController: NavController,
    ) {
        selectedDirection?.let {
            val currentE = EChart.entries.find { it.resourceId == currentEFormat }
            if (selectedDirection == currentE) {
                correctAnswersCount++
                println("test -> Correct answer! Current count: $correctAnswersCount")
            } else {
                wrongAnswersCount++
                println("test -> Wrong answer! Current count: $wrongAnswersCount")
            }
        }

        // Change rows or end the test based on the current count
        when {
            correctAnswersCount == 3 -> {
                if (currentRow >= 5) {
                    endTestForCurrentEye(navController, currentRow)
                    return
                }
                currentRow++
                resetCounters()
            }

            wrongAnswersCount >= 3 -> {
                endTestForCurrentEye(navController, currentRow - 1)
                return
            }
        }

        currentEFormat = EChart.getRandomIcon(currentRow)
    }

    private fun endTestForCurrentEye(navController: NavController, row: Int) {
        updateFinalLevel(row)
        printTestStatus()
        resetCounters()
        navController.popBackStack()
    }
}

