package com.europa.sightup.presentation.designsystem.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.utils.currentTimeMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun AudioVisualizer(
    modifier: Modifier = Modifier,
    barCount: Int = 5,
    barWidth: Dp = 16.dp,
    barMaxHeight: Dp = 100.dp,
    barMinHeight: Dp = 20.dp,
    barColor: Color = SightUPTheme.colors.primary,
    animationSpeed: Int = 500,
    randomAnimation: Boolean = true,
) {
    val bars = remember { mutableStateListOf<Animatable<Float, AnimationVector1D>>() }

    // Init animation
    LaunchedEffect(Unit) {
        repeat(barCount) {
            bars.add(Animatable(barMinHeight.value))
        }
    }

    // Animation loop
    LaunchedEffect(randomAnimation) {
        while (true) {
            bars.indices.forEach { indexOfBars ->
                val targetHeight = if (randomAnimation) {
                    // Random Animation
                    Random.nextFloat() * (barMaxHeight.value - barMinHeight.value) + barMinHeight.value
                } else {
                    // Wave Animation
                    val progress = (indexOfBars + Clock.System.currentTimeMillis() / animationSpeed) % barCount
                    val wave = sin(progress * (PI / barCount)).toFloat()
                    barMinHeight.value + wave * (barMaxHeight.value - barMinHeight.value)
                }

                // Smooth animation to target height
                launch {
                    bars[indexOfBars].animateTo(
                        targetValue = targetHeight,
                        animationSpec = tween(durationMillis = animationSpeed, easing = LinearEasing)
                    )
                }
            }
            delay(animationSpeed.toLong())
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp), // padding between bars
        verticalAlignment = Alignment.CenterVertically
    ) {
        bars.forEach { animatedHeight ->
            Box(
                modifier = Modifier
                    .height(animatedHeight.value.dp) // Dynamic height
                    .width(barWidth)
                    .clip(RoundedCornerShape(50)) // Rounded bars
                    .background(barColor)
            )
        }
    }
}
