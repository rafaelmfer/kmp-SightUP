package com.europa.sightup.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.CardExerciseBottom
import com.europa.sightup.presentation.designsystem.components.SDSTimerScreen
import com.europa.sightup.presentation.designsystem.screens.CountdownScreen
import com.europa.sightup.presentation.screens.onboarding.DisclaimerScreen
import com.europa.sightup.presentation.screens.onboarding.TutorialScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Disclaimer() {
    SightUPTheme {
        DisclaimerScreen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Tutorial() {
    SightUPTheme {
        TutorialScreen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Countdown() {
    SightUPTheme {
        CountdownScreen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TimerPreview() {
    SightUPTheme {
        SDSTimerScreen()
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun CardExerciseBottomPreview() {
    SightUPTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CardExerciseBottom(
                audio = true,
                typeExercise = "Exercise",
                duration = "10 min",
                title = "Exercise Title",
                description = "Exercise Description",
                subDescriptionTitle = "Sub Description Title",
                subDescription = arrayListOf("Sub Description 1", "Sub Description 2", "Sub Description 3"),
                onClick = {}
            )
        }

    }
}