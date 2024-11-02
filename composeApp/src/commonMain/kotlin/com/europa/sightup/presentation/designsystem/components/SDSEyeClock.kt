package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.astigmatic_clock_eye_chart

@Composable
fun SDSEyeClock(
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

    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(Res.drawable.astigmatic_clock_eye_chart),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clipToBounds()
                        .onGloballyPositioned { coordinates ->
                            parentWidth = coordinates.size.width
                        }
                )
            }
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 2).toDp() - 25.dp }

                        IntOffset(offsetX.roundToPx(), 0)
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonTwelveOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 4).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.2).toInt(),
                            offsetY.roundToPx() / 18
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonElevenOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 15).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.3).toInt(),
                            (offsetY.roundToPx() / 4.5).toInt()
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonTenOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 15).toDp() - 0.dp }

                        IntOffset(0, (offsetY.roundToPx() / 2.3).toInt())
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonNineOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 15).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.3).toInt(),
                            (offsetY.roundToPx() / 2) + (offsetY.roundToPx() / 6)
                        ) //
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonEightOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 4).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.2).toInt(),
                            offsetY.roundToPx() / 2 + (offsetY.roundToPx() / 3.2).toInt()
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonSevenOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() - SightUPTheme.sizes.size_48 }
                        val offsetX = with(density) { (parentWidth / 2).toDp() - 25.dp }

                        IntOffset((offsetX.roundToPx()), offsetY.roundToPx())
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonSixOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth / 3).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx()) + (offsetX.roundToPx()),
                            offsetY.roundToPx() / 2 + (offsetY.roundToPx() / 3.2).toInt()
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonFiveOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.22).toInt(),
                            (offsetY.roundToPx() / 2) + (offsetY.roundToPx() / 6)
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonFourOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth).toDp() - SightUPTheme.sizes.size_48 }

                        IntOffset(offsetX.roundToPx(), (offsetY.roundToPx() / 2.3).toInt())
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonThreeOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.22).toInt(),
                            (offsetY.roundToPx() / 4.5).toInt()
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonTwoOnClick() }
            )
            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .offset {
                        val offsetY = with(density) { (parentWidth).toDp() + 0.dp }
                        val offsetX = with(density) { (parentWidth).toDp() - 0.dp }

                        IntOffset(
                            (offsetX.roundToPx() / 1.52).toInt(),
                            offsetY.roundToPx() / 18
                        )
                    }
                    .clip(SightUPTheme.shapes.extraLarge)
                    .clickable { buttonOneOnClick() }
            )
        }
    }
}

@Composable
fun SDSEyeClockScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SDSEyeClock(
            buttonOneOnClick = {
                showToast(
                    "Button One Clicked",
                    bottomPadding = 40
                )
            },
            buttonTwoOnClick = {
                showToast(
                    "Button Two Clicked",
                    bottomPadding = 40
                )
            },
            buttonThreeOnClick = {
                showToast(
                    "Button Three Clicked",
                    bottomPadding = 40
                )
            },
            buttonFourOnClick = {
                showToast(
                    "Button Four Clicked",
                    bottomPadding = 40
                )
            },
            buttonFiveOnClick = {
                showToast(
                    "Button Five Clicked",
                    bottomPadding = 40
                )
            },
            buttonSixOnClick = {
                showToast(
                    "Button Six Clicked",
                    bottomPadding = 40
                )
            },
            buttonSevenOnClick = {
                showToast(
                    "Button Seven Clicked",
                    bottomPadding = 40
                )
            },
            buttonEightOnClick = {
                showToast(
                    "Button Eight Clicked",
                    bottomPadding = 40
                )
            },
            buttonNineOnClick = {
                showToast(
                    "Button Nine Clicked",
                    bottomPadding = 40
                )
            },
            buttonTenOnClick = {
                showToast(
                    "Button Ten Clicked",
                    bottomPadding = 40
                )
            },
            buttonElevenOnClick = {
                showToast(
                    "Button Eleven Clicked",
                    bottomPadding = 40
                )
            },
            buttonTwelveOnClick = {
                showToast(
                    "Button Twelve Clicked",
                    bottomPadding = 40
                )
            })
    }

}