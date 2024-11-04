package com.europa.sightup.presentation.screens.test.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.data.remote.response.visionHistory.UserHistoryResponse
import com.europa.sightup.utils.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TestResultScreen(
    appTest: Boolean = true,
    testId: String = "",
    testTitle: String = "",
    left: String = "",
    right: String = "",
) {
    val viewModel = koinViewModel<TestResultViewModel>()

    val testResultState by viewModel.newTestResult.collectAsState()
    val visionHistoryState by viewModel.visionHistory.collectAsState()

    // TODO DELETE THIS AFTER
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("You're in the test result screen")
        Text("App Test: $appTest")
        Text("Test ID: $testId")
        Text("Test Title: $testTitle")
        Text("Left Eye Result: $left")
        Text("Right Eye Result: $right")

        Button(onClick = {
            viewModel.saveVisionTest(
                appTest = appTest,
                testId = testId,
                testTitle = testTitle,
                left = left,
                right = right
            )
        }) {
            Text("Save to DB")
        }

        when (testResultState) {
            is UIState.Loading -> {
                Text("Saving test result...")
            }

            is UIState.Success -> {
                Text("Test saved successfully!")
                GetVisionHistory(viewModel, visionHistoryState)
            }

            is UIState.Error -> {
                Text("Error saving test: ${(testResultState as UIState.Error).message}")
            }

            else -> {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun GetVisionHistory(viewModel: TestResultViewModel, visionHistoryState: UIState<List<UserHistoryResponse>>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { viewModel.getVisionHistory() }) {
            Text("Get Vision History")
        }

        when (visionHistoryState) {
            is UIState.Loading -> {
                Text("Loading vision history...")
            }

            is UIState.Success -> {
                val userHistoryList = (visionHistoryState as UIState.Success<List<UserHistoryResponse>>).data

                if (userHistoryList.isEmpty()) {
                    Text("No test history available.")
                } else {
                    userHistoryList.forEach { userHistory ->
                        Text("User ID: ${userHistory.userId}")
                        Text("User Email: ${userHistory.userEmail}")

                        userHistory.tests.forEach { test ->
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            Text("Test ID: ${test.testId}")
                            Text("Title: ${test.testTitle}")
                            Text("Date: ${test.date}")
                            Text("Left Eye Result: ${test.result.left}")
                            Text("Right Eye Result: ${test.result.right}")
                        }
                    }
                }
            }

            is UIState.Error -> {
                Text("Error loading history: ${(visionHistoryState as UIState.Error).message}")
            }

            else -> {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
