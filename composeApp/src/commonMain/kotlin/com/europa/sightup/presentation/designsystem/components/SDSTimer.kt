package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.coroutines.delay

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

    startTimer(seconds, minutes, { minutes, seconds ->
        minutesLeft = minutes
        secondsLeft = seconds
    }, {
        onTimerFinish()
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = SightUPTheme.textStyles.body
        )
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (minutesLeft > 0) {
                Text(
                    text = minutesLeft.toString() + "\'",
                    style = SightUPTheme.textStyles.large.copy(
                        fontSize = 48.sp
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
                } + "\"",
                style = SightUPTheme.textStyles.large.copy(
                    fontSize = 48.sp
                ),
                modifier = Modifier.clickable {
                    onTimerFinish()
                }
            )
        }
    }
}