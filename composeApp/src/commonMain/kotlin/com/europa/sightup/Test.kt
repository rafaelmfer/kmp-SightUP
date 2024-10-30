package com.europa.sightup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.screens.test.getVoiceRecognition
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(context: Any) {
    MaterialTheme {
        var clickedButton by remember { mutableStateOf(0) }
        var isListening by remember { mutableStateOf(false) }

        VoiceRecognitionScreen(
            clickedButton = clickedButton,
            isListening = isListening,
            onVoiceClick = {
                isListening = true
                getVoiceRecognition(context).startListening { spokenText ->
                    when {
                        spokenText.contains("one", ignoreCase = true) -> clickedButton = 1
                        spokenText.contains("two", ignoreCase = true) -> clickedButton = 2
                        spokenText.contains("three", ignoreCase = true) -> clickedButton = 3
                        spokenText.contains("four", ignoreCase = true) -> clickedButton = 4
                        spokenText.contains("five", ignoreCase = true) -> clickedButton = 5
                        spokenText.contains("six", ignoreCase = true) -> clickedButton = 6
                        spokenText.contains("seven", ignoreCase = true) -> clickedButton = 7
                        spokenText.contains("eight", ignoreCase = true) -> clickedButton = 8
                        spokenText.contains("nine", ignoreCase = true) -> clickedButton = 9
                        spokenText.contains("ten", ignoreCase = true) || spokenText.contains(
                            "10",
                            ignoreCase = true
                        ) -> clickedButton = 10

                        spokenText.contains("eleven", ignoreCase = true) || spokenText.contains(
                            "11",
                            ignoreCase = true
                        ) -> clickedButton = 11

                        spokenText.contains("twelve", ignoreCase = true) || spokenText.contains(
                            "12",
                            ignoreCase = true
                        ) -> clickedButton = 12

                        else -> clickedButton = 0
                    }
                    isListening = false
                }
            })
    }
}

@Composable
fun VoiceRecognitionScreen(
    clickedButton: Int,
    isListening: Boolean,
    onVoiceClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isListening) {
            Text("Listening...", fontSize = 20.sp)
        }

        Button(onClick = onVoiceClick) {
            Text("Start Listening")
        }

        if (clickedButton != 0) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Button $clickedButton clicked!")
        }
    }
}

