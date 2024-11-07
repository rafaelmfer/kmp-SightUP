package com.europa.sightup.presentation.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.wear.compose.material3.Text
import com.europa.sightup.R
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin


@Composable
fun SDSControlEyeClockWear(
    buttonOneOnClick: () -> Unit,
    buttonTwoOnClick: () -> Unit,
    buttonThreeOnClick: () -> Unit,
    buttonFourOnClick: () -> Unit,
    buttonFiveOnClick: () -> Unit,
    buttonSixOnClick: () -> Unit,
    buttonSevenOnClick: () -> Unit,
    buttonEightOnClick: () -> Unit,
    buttonNineOnClick: () -> Unit,
    buttonTenOnClick: () -> Unit,
    buttonElevenOnClick: () -> Unit,
    buttonTwelveOnClick: () -> Unit,
) {
    var parentWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    fun calculateOffsetForButton(index: Int, radius: Float): IntOffset {
        val angle = Math.toRadians((index * 30).toDouble())
        val x = (radius * cos(angle)).toFloat()
        val y = (radius * sin(angle)).toFloat()
        return IntOffset(x.roundToInt(), y.roundToInt())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_default),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.astigmatism_test),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clipToBounds()
                .onGloballyPositioned { coordinates ->
                    parentWidth = coordinates.size.width
                },
            alpha = 0f
        )

        val radius = (parentWidth / 2.4f)

        val buttons = listOf(
            Pair("3", buttonThreeOnClick),
            Pair("4", buttonFourOnClick),
            Pair("5", buttonFiveOnClick),
            Pair("6", buttonSixOnClick),
            Pair("7", buttonSevenOnClick),
            Pair("8", buttonEightOnClick),
            Pair("9", buttonNineOnClick),
            Pair("10", buttonTenOnClick),
            Pair("11", buttonElevenOnClick),
            Pair("12", buttonTwelveOnClick),
            Pair("1", buttonOneOnClick),
            Pair("2", buttonTwoOnClick)
        )

        buttons.forEachIndexed { index, (label, onClick) ->
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset { calculateOffsetForButton(index, radius) }
                    .clip(CircleShape)
                    .clickable { onClick() }
            ) {
                Text(
                    text = label,
                    style = SightUPTheme.textStyles.body2.copy(color = SightUPTheme.sightUPColors.black),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

