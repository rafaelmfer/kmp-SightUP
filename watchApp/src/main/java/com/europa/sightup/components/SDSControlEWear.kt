package com.europa.sightup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.europa.sightup.R
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import kotlinx.coroutines.delay

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

    isClickedList.forEachIndexed { index, isClicked ->
        LaunchedEffect(isClicked) {
            if (isClicked) {
                delay(1000)
                isClickedList[index] = false
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (isClickedList[0]) SightUPTheme.sightUPColors.primary_600
                    else SightUPTheme.sightUPColors.background_default
                )
                .clickable {
                    isClickedList[0] = true
                    upButtonOnClickResult()
                }
        ) {
            Text(
                text = "Up",
                color = if (isClickedList[0]) SightUPTheme.sightUPColors.text_secondary else SightUPTheme.sightUPColors.primary_700,
                style = SightUPTheme.textStyles.body.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        Row(
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isClickedList[1]) SightUPTheme.sightUPColors.primary_600
                        else SightUPTheme.sightUPColors.background_default
                    )
                    .clickable {
                        isClickedList[1] = true
                        leftButtonOnClickResult()
                    }
            ) {
                Text(
                    text = "Left",
                    color = if (isClickedList[1]) SightUPTheme.sightUPColors.text_secondary else SightUPTheme.sightUPColors.primary_700,
                    style = SightUPTheme.textStyles.body.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Imagem no centro
            Image(
                painter = painterResource(R.drawable.e_right),
                contentDescription = "Letter E",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SightUPTheme.sightUPColors.background_default)
                    .padding(SightUPTheme.spacing.spacing_sm)
                    .size(24.dp),
                contentScale = ContentScale.Fit
            )
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isClickedList[2]) SightUPTheme.sightUPColors.primary_600
                        else SightUPTheme.sightUPColors.background_default
                    )
                    .clickable {
                        isClickedList[2] = true
                        rightButtonOnClickResult()
                    }
            ) {
                Text(
                    text = "Right",
                    color = if (isClickedList[2]) SightUPTheme.sightUPColors.text_secondary else SightUPTheme.sightUPColors.primary_700,
                    style = SightUPTheme.textStyles.body.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (isClickedList[3]) SightUPTheme.sightUPColors.primary_600
                    else SightUPTheme.sightUPColors.background_default
                )
                .clickable {
                    isClickedList[3] = true
                    downButtonOnClickResult()
                }
        ) {
            Text(
                text = "Down",
                color = if (isClickedList[3]) SightUPTheme.sightUPColors.text_secondary else SightUPTheme.sightUPColors.primary_700,
                style = SightUPTheme.textStyles.body.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
