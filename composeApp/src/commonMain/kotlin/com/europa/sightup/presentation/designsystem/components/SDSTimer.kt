package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.fontSize
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.coroutines.delay
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun startTimer(
    seconds: Int,
    minutes: Int,
    onTick: (Int, Int) -> Unit,
    onFinish: () -> Unit,
) {
    var secondsLeft = seconds
    var minutesLeft = minutes

    LaunchedEffect(key1 = secondsLeft, key2 = minutesLeft) {
        while (minutesLeft > 0 || secondsLeft > 0) {
            delay(1000L)
            if (secondsLeft > 0) {
                secondsLeft--
            } else if (minutesLeft > 0) {
                secondsLeft = 59
                minutesLeft--
            }
            onTick(minutesLeft, secondsLeft)
        }
        onFinish()
    }
}

@Composable
fun SDSTimer(
    title: String = "Duration",
    seconds: Int = 10,
    minutes: Int = 2,
    onTimerFinish: () -> Unit = { },
) {
    var secondsLeft by remember { mutableStateOf(seconds) }
    var minutesLeft by remember { mutableStateOf(minutes) }

    startTimer(seconds, minutes, { min, sec ->
        minutesLeft = min
        secondsLeft = sec
    }, {
        onTimerFinish()
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "$title ",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = SightUPTheme.textStyles.body
        )
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (minutesLeft > 0) {
                Text(
                    text = "$minutesLeft’",
                    style = SightUPTheme.textStyles.large.copy(
                        fontSize = SightUPTheme.fontSize.fontSize_extra_huge
                    ),
                    modifier = Modifier.clickable {
                        onTimerFinish()
                    }
                )
            }
            Text(
                text = if (secondsLeft < 10) {
                    "0$secondsLeft"
                } else {
                    secondsLeft.toString()
                } + "”",
                style = SightUPTheme.textStyles.large.copy(
                    fontSize = SightUPTheme.fontSize.fontSize_extra_huge
                ),
                modifier = Modifier.clickable {
                    onTimerFinish()
                }
            )
        }
    }
}

@Preview
@Composable
fun SDSTimerScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SDSTimer(
            title = "Duration",
            seconds = 10,
            minutes = 2,
            onTimerFinish = {
                showToast(
                    "Timer finished",
                    bottomPadding = 40
                )
            }
        )
    }
}