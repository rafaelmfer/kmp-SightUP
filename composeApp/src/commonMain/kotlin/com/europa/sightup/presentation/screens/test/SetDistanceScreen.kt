package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.europa.sightup.presentation.designsystem.components.SwitchAudio
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.coroutines.delay

@Composable
fun SetDistanceScreen(
    onClick: () -> Unit,
) {
    val distanceState = remember { mutableStateOf("0") }

    val underRange = (distanceState.value.toFloatOrNull() ?: 0f) < 29f
    val perfectRange = (distanceState.value.toFloatOrNull() ?: 0f) in 29f..35f

    if (perfectRange) {
        LaunchedEffect(Unit) {
            delay(3000L)
            onClick()
        }
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { },
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 1f)
                .fillMaxWidth(fraction = 1f)
                .background(Color.Black.copy(alpha = 0.8f))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                topBlackBar()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        // The Camera view from Android and iOS will be displayed here
                        val camera = DistanceToCamera(distance = distanceState, aspectRatio = 3f / 4f)
                        distanceState.value = camera.getDistanceToCamera.value

                        if (underRange) {
                            val distance = distanceState.value.toFloatOrNull() ?: 0f
                            val distanceText = "${distance.toInt()} cm \n Step back further"

                            DistanceCard(text = distanceText, backgroundColor = SightUPTheme.sightUPColors.error_300)
                        } else if (perfectRange) {
                            DistanceCard(text = "30 cm \n Perfect distance!")
                        }
                    }

                    MiddleCrux()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(16.dp).fillMaxWidth(1f))
                        SwitchAudio()
                    }
                }

                BottomBlackBar()
            }
        }
    }
}

@Composable
fun DistanceCard(
    text: String,
    backgroundColor: Color = SightUPTheme.colors.primary,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            .padding(top = SightUPTheme.spacing.spacing_lg)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(SightUPTheme.spacing.spacing_base),
            text = text,
            style = SightUPTheme.textStyles.large,
            color = SightUPTheme.sightUPColors.text_secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MiddleCrux() {
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawLine(
                color = Color.White,
                start = Offset(x = canvasWidth / 2, y = canvasHeight / 2 - 90),
                end = Offset(x = canvasWidth / 2, y = canvasHeight / 2 + 90),
                strokeWidth = 4f
            )
            drawLine(
                color = Color.White,
                start = Offset(x = canvasWidth / 2 - 90, y = canvasHeight / 2),
                end = Offset(x = canvasWidth / 2 + 90, y = canvasHeight / 2),
                strokeWidth = 4f
            )
        }
    )
}

@Composable
private fun topBlackBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp)
            .background(Color.Black)
    )
}

@Composable
private fun BottomBlackBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                .clip(RoundedCornerShape(8.dp))
                .background(SightUPTheme.colors.primary)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                text = "Keep 30 cm distance away from your phone.",
                color = SightUPTheme.sightUPColors.text_secondary,
                style = SightUPTheme.textStyles.button,
                textAlign = TextAlign.Center,
            )
        }
    }
}
