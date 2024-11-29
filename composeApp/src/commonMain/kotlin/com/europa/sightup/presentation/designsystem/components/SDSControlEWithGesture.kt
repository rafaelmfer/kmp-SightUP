package com.europa.sightup.presentation.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.color.SightUPContextColor
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.coroutines.delay
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.e_right
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Helper Functions
 */

/**
 * Converts degrees to radians.
 */
fun degreesToRadians(degrees: Float): Float = (degrees * PI / 180).toFloat()

/**
 * Snaps the current angle to the closest predefined angle.
 */
fun snapToClosestAngle(angle: Float): Float {
    val angles = listOf(0f, 90f, 180f, 270f)
    return angles.minByOrNull { normalizedAngleDifference(it, angle) } ?: angle
}

/**
 * Calculates the minimum difference between two angles, considering 360° == 0°.
 */
fun normalizedAngleDifference(a: Float, b: Float): Float {
    val difference = abs((a - b + 360) % 360)
    return min(difference, 360 - difference)
}

/**
 * Calculates the angle (in degrees) between the touch position and the center.
 */
fun calculateAngle(center: Offset, touch: Offset): Float {
    val dx = touch.x - center.x
    val dy = touch.y - center.y
    val angle = (atan2(dy, dx) * (180 / PI)).toFloat()
    return (angle + 360) % 360 // Normalizes the angle to [0°, 360°]
}

@Composable
fun SDSControlE(
    upButtonOnClickResult: () -> Unit = {},
    leftButtonOnClickResult: () -> Unit = {},
    rightButtonOnClickResult: () -> Unit = {},
    downButtonOnClickResult: () -> Unit = {},
    rotationAngle: Float? = null,
    modifier: Modifier = Modifier,
) {
    val isClickedList = remember { mutableStateListOf(false, false, false, false) }

    // Resets the clicked state after 500ms
    isClickedList.forEachIndexed { index, isClicked ->
        LaunchedEffect(isClicked) {
            if (isClicked) {
                delay(500)
                isClickedList[index] = false
            }
        }
    }

    // UI dimensions
    val density = LocalDensity.current
    val imageSize = 64.dp
    val paddingOfImage = 16.dp
    val spaceBetweenImageAndCircle = 16.dp

    val buttonSize = 72.dp
    val spaceBetweenButtonAndCircle = 4.dp
    val circleBorderSize = 8.dp

    val circleSize = buttonSize + circleBorderSize + spaceBetweenButtonAndCircle

    val totalImageSize = imageSize + (paddingOfImage * 2)
    val radiusInDp = (totalImageSize / 2) + spaceBetweenImageAndCircle + circleSize

    // Dimensions in pixels for calculations
    val totalImageSizePx = with(density) { totalImageSize.toPx() }
    val circleRadius = with(density) { circleSize.toPx() } / 2
    val spaceBetweenImageAndCirclePx = with(density) { spaceBetweenImageAndCircle.toPx() }
    val radius = (totalImageSizePx / 2) + spaceBetweenImageAndCirclePx + circleRadius

    var rotationState by remember { mutableFloatStateOf(0f) }

    var center by remember { mutableStateOf(Offset.Zero) }

    val buttons = listOf(
        Triple("Right", 0f, rightButtonOnClickResult),
        Triple("Down", 90f, downButtonOnClickResult),
        Triple("Left", 180f, leftButtonOnClickResult),
        Triple("Up", 270f, upButtonOnClickResult)
    )

    LaunchedEffect(rotationAngle) {
        rotationAngle?.let {
            rotationState = it
        }
    }

    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = radiusInDp * 2,
                minHeight = radiusInDp * 2
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        val snappedAngle = snapToClosestAngle(rotationState)
                        rotationState = snappedAngle

                        buttons.forEachIndexed { index, (_, angle, onClick) ->
                            if (snappedAngle == angle) {
                                isClickedList[index] = true
                                onClick()
                            }
                        }
                    },
                    onDrag = { change, _ ->
                        val touchPosition = change.position
                        rotationState = calculateAngle(center, touchPosition)
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Central image
        Image(
            painter = painterResource(Res.drawable.e_right),
            contentDescription = "Letter E",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .background(SightUPTheme.sightUPColors.background_default)
                .padding(paddingOfImage)
                .size(imageSize)
                .padding(12.dp)
                .onGloballyPositioned { coordinates ->
                    val boxBounds = coordinates.boundsInParent()
                    center = Offset(
                        x = boxBounds.left + boxBounds.width / 2,
                        y = boxBounds.top + boxBounds.height / 2
                    )
                }
                .graphicsLayer(
                    rotationZ = rotationState,
                    transformOrigin = TransformOrigin.Center
                )
        )

        // Rotating circle
        Box(
            modifier = Modifier
                .offset {
                    val radians = degreesToRadians(rotationState)
                    IntOffset(
                        x = (radius * cos(radians)).toInt(),
                        y = (radius * sin(radians)).toInt()
                    )
                }
                .graphicsLayer(
                    rotationZ = rotationState,
                    transformOrigin = TransformOrigin.Center
                )
                .size(circleSize)
                .border(
                    width = circleBorderSize,
                    color = SightUPTheme.sightUPColors.background_default,
                    shape = CircleShape
                )
                .background(Color.Transparent, CircleShape)
                .drawBehind {
                    // Add 1.dp at both lines because of round border of circles to avoid clipping
                    val lineStart = (spaceBetweenImageAndCircle + 1.dp).toPx()
                    val lineEnd = 1.dp.toPx()
                    drawLine(
                        color = SightUPContextColor.background_default,
                        start = Offset(-lineStart, size.height / 2),
                        end = Offset(lineEnd, size.height / 2),
                        strokeWidth = 12.dp.toPx()
                    )
                }

        )

        // Directional buttons
        buttons.forEachIndexed { index, (label, angle, onClick) ->
            val backgroundColor by animateColorAsState(
                targetValue = if (isClickedList[index]) SightUPTheme.sightUPColors.background_button else SightUPTheme.sightUPColors.background_default
            )
            val textColor by animateColorAsState(
                targetValue = if (isClickedList[index]) SightUPTheme.sightUPColors.text_secondary else SightUPTheme.sightUPColors.primary_600
            )

            Box(
                modifier = Modifier
                    .offset {
                        val radians = degreesToRadians(angle)
                        IntOffset(
                            x = (radius * cos(radians)).toInt(),
                            y = (radius * sin(radians)).toInt()
                        )
                    }
                    .size(circleSize)
                    .padding(circleBorderSize) // discount the border size of the rotation circle
                    .padding(spaceBetweenButtonAndCircle)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .clickable {
                        rotationState = angle
                        isClickedList[index] = true
                        onClick()
                    }
            ) {
                Text(
                    text = label,
                    style = SightUPTheme.textStyles.body.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SDSControlEPreview() {
    SightUPTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(SightUPTheme.sightUPColors.background_activate)
                .fillMaxSize()
        ) {
            SDSControlE(
                upButtonOnClickResult = {
                    showToast(
                        "Up button clicked",
                        bottomPadding = 40
                    )
                },
                leftButtonOnClickResult = {
                    showToast(
                        "Left button clicked",
                        bottomPadding = 40
                    )
                },
                rightButtonOnClickResult = {
                    showToast(
                        "Right button clicked",
                        bottomPadding = 40
                    )
                },
                downButtonOnClickResult = {
                    showToast(
                        "Down button clicked",
                        bottomPadding = 40
                    )
                }
            )
        }
    }
}