package com.europa.sightup.presentation.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.fontSize
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Countdown(modifier: Modifier = Modifier, startsIn: Int = 3, onCountdownFinished: () -> Unit) {
    var currentCount by remember { mutableStateOf(startsIn) }
    val animationDuration = 1000

    LaunchedEffect(Unit) {
        for (i in startsIn downTo 1) {
            currentCount = i
            delay(animationDuration.toLong())
        }
        onCountdownFinished()
    }

    Box(
        modifier = Modifier
            .size(SightUPTheme.sizes.size_200)
            .clip(CircleShape)
            .background(color = Color(0xFFEDEEF0))
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = currentCount,
            transitionSpec = {
                (fadeIn(animationSpec = tween(durationMillis = animationDuration)) +
                    scaleIn(initialScale = 2f, animationSpec = tween(durationMillis = animationDuration))) togetherWith
                    (fadeOut(animationSpec = tween(durationMillis = animationDuration)) +
                        scaleOut(targetScale = 0.5f, animationSpec = tween(durationMillis = animationDuration)))
            }, label = ""
        ) { targetCount ->
            Text(
                modifier = Modifier
                    .padding(SightUPTheme.spacing.spacing_base),
                text = targetCount.toString(),
                fontSize = SightUPTheme.fontSize.fontSize_extra_huge,
                fontWeight = FontWeight.Bold,
                color = SightUPTheme.sightUPColors.black
            )
        }
    }
}
