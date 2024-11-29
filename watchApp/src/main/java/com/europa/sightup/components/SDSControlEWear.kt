package com.europa.sightup.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.europa.sightup.R
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun SDSControlEWear(
    upButtonOnClickResult: () -> Unit = {},
    leftButtonOnClickResult: () -> Unit = {},
    rightButtonOnClickResult: () -> Unit = {},
    downButtonOnClickResult: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val isClickedList = remember { mutableStateListOf(false, false, false, false) }

    // Resets the clicked state after 500ms
    isClickedList.forEachIndexed { index, isClicked ->
        LaunchedEffect(isClicked) {
            if (isClicked) {
                delay(300)
                isClickedList[index] = false
            }
        }
    }

    // UI dimensions
    val density = LocalDensity.current
    val imageSize = 40.dp
    val paddingOfImage = 4.dp
    val spaceBetweenImageAndCircle = 4.dp

    val buttonSize = 52.dp
    val spaceBetweenButtonAndCircle = 2.dp
    val circleBorderSize = 4.dp

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

    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = radiusInDp * 2,
                minHeight = radiusInDp * 2
            ),
        contentAlignment = Alignment.Center
    ) {
        // Central image
        Image(
            painter = painterResource(R.drawable.e_right),
            contentDescription = "Letter E",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .background(SightUPTheme.sightUPColors.background_default)
                .padding(paddingOfImage)
                .size(imageSize)
                .padding(8.dp)
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