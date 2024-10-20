package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    thumbSize: Dp = 19.dp,
    trackHeight: Dp = 20.dp
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