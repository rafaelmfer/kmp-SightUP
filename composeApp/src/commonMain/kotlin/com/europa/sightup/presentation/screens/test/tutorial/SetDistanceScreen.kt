package com.europa.sightup.presentation.screens.test.tutorial

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.europa.sightup.presentation.designsystem.components.DistanceMessageCard
import com.europa.sightup.presentation.screens.test.DistanceToCamera
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetDistanceScreen(
    onClick: () -> Unit,
) {
    val distanceState = remember { mutableStateOf("0") }

    val underRange = (distanceState.value.toFloatOrNull() ?: 0f) < 30f
    val perfectRange = (distanceState.value.toFloatOrNull() ?: 0f) in 30f..40f
    val overRange = (distanceState.value.toFloatOrNull() ?: 0f) > 40f

    if (perfectRange) {
        LaunchedEffect(Unit) {
            delay(2000L)
            onClick()
        }
    }

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    BindEffect(controller)

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

                TopBlackBar()

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
                        coroutineScope.launch {
                            controller.providePermission(Permission.CAMERA)
                        }
                        DistanceToCamera(distance = distanceState)
                        val distance = distanceState.value.toFloatOrNull() ?: 0f

                        if (underRange) {
                            val distanceText = "${distance.toInt()} cm \n Step back further"
                            DistanceMessageCard(
                                text = distanceText,
                                backgroundColor = SightUPTheme.sightUPColors.background_error,
                                textColor = SightUPTheme.sightUPColors.error_300
                            )
                        } else if (perfectRange) {
                            DistanceMessageCard(text = "${distance.toInt()} cm \n Perfect distance!")
                        } else if (overRange) {
                            val distanceText = "${distance.toInt()} cm \n Move closer"
                            DistanceMessageCard(
                                text = distanceText,
                                backgroundColor = SightUPTheme.sightUPColors.background_error,
                                textColor = SightUPTheme.sightUPColors.error_300
                            )
                        }
                    }
                    MiddleCrux()
                }
                BottomBlackBar()
            }
        }
    }
}

@Composable
private fun MiddleCrux() {
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
private fun TopBlackBar() {
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
                .background(SightUPTheme.sightUPColors.background_info)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                text = "Keep 30 cm distance away from your phone.",
                color = SightUPTheme.sightUPColors.info_300,
                style = SightUPTheme.textStyles.button,
                textAlign = TextAlign.Center,
            )
        }
    }
}
