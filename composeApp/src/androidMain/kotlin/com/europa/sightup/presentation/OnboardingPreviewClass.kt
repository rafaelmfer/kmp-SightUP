package com.europa.sightup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.SDSCardExerciseBottom
import com.europa.sightup.presentation.designsystem.components.SDSCardTestBottom
import com.europa.sightup.presentation.designsystem.components.SDSTimerScreen
import com.europa.sightup.presentation.designsystem.screens.CountdownScreen
import com.europa.sightup.presentation.screens.exercise.details.ExerciseDetailsScreen
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
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            SDSCardExerciseBottom(
                category = "",
                title = "Circular Motion",
                motivation = "Let’s take a quick break and give your eyes some gentle movement!",
                duration = 0,
                buttonText = "Start",
                onClick = {},
                modifier = Modifier.background(Color.White)
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun CardTestBottomPreview() {
    SightUPTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            SDSCardTestBottom(
                title = "Visual Acuity",
                description = "Visual acuity refers to the clarity or sharpness of vision, which is measured by your ability to discern letters or details at a specific distance.",
                requirements = listOf(
                    "• This test utilizes the camera",
                    "• Test can be done by holding your phone or from a distance."
                ),
                buttonText = "Start",
                onClick = {},
                modifier = Modifier.background(Color.White)
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ScreenPreview() {
    SightUPTheme {
        ExerciseDetailsScreen()
    }
}