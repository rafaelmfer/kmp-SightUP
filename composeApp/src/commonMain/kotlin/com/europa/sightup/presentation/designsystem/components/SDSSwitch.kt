package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SDSSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    thumbSize: Dp = 19.dp,
    trackHeight: Dp = 20.dp,
) {
    Box(
        modifier = modifier
            .width(40.dp)
            .height(trackHeight)
            .background(
                color = if (isChecked) Color(0xFF2C76A8) else Color.LightGray,
                shape = RoundedCornerShape(percent = 50)
            )
            .clickable { onCheckedChange(!isChecked) }
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset(x = if (isChecked) 20.dp else 0.dp)
                .padding(4.dp)
                .background(Color.White, shape = CircleShape)
        )
    }
}

@Preview
@Composable
fun SDSSwitchScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = SightUPTheme.spacing.spacing_base,
            alignment = Alignment.CenterVertically
        )
    ) {
        SDSSwitch(isChecked = false, onCheckedChange = {})

        SDSSwitch(isChecked = true, onCheckedChange = {})

        CustomSwitchExample()

        SwitchAudio()
    }
}


@Composable
fun CustomSwitchExample() {
    var isChecked by remember { mutableStateOf(false) }

    Switch(
        checked = isChecked,
        onCheckedChange = { isChecked = it },
        modifier = Modifier
            .width(36.dp) // TODO: Talk with designers because we cannot resize switch, because of accessibility
            .height(20.dp), // TODO: they really want a small switch ??
        colors = SwitchDefaults.colors(
            checkedThumbColor = SightUPTheme.sightUPColors.neutral_0, // Cor branca para o thumb quando ativado
            uncheckedThumbColor = SightUPTheme.sightUPColors.neutral_0, // Cor branca para o thumb quando desativado
            checkedTrackColor = SightUPTheme.sightUPColors.primary_600, // Cor azul para o track ativado
            uncheckedTrackColor = SightUPTheme.sightUPColors.neutral_200, // Cor cinza para o track desativado
        ),
        thumbContent = {
            if (isChecked) {
                Box(modifier = Modifier.fillMaxSize()) {
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = " ",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }
            }
        }
    )
}