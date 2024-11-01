package com.europa.sightup.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Composable
fun StepProgressBar(
    modifier: Modifier = Modifier,
    numberOfSteps: Int,
    currentStep: Int
) {
    Row(
        modifier = modifier.fillMaxWidth()
                            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 1..numberOfSteps) {
            Step(
                isComplete = step < currentStep,
                isCurrent = step == currentStep,
                modifier = Modifier.weight(1f)
            )
            // Add space between steps, except after the last step
            if (step < numberOfSteps) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun Step(
    modifier: Modifier = Modifier,
    isComplete: Boolean,
    isCurrent: Boolean,
) {
    // Current step gets primary color, completed and future steps get secondary
    val color = when {
        isCurrent -> SightUPTheme.colors.primary
        isComplete -> SightUPTheme.colors.inversePrimary
        else -> SightUPTheme.colors.inversePrimary
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Horizontal line to represent the step progress
        Divider(
            modifier = Modifier
                .align(Alignment.Center)
                .height(6.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(100)),
            color = color
        )
    }
}
