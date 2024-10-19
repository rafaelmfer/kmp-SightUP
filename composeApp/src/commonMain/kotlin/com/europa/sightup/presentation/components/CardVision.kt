package com.europa.sightup.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardVision() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vision",
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(end = 8.dp)
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                    .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = "SightUP",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = "Vision Test on Sep 27, 2024",
            fontSize = 10.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(end = 8.dp)
        )
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "",
                fontSize = 10.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "Left",
                fontSize = 14.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Right",
                fontSize = 14.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SPH",
                fontSize = 10.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "-3.50",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "-0.75",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
        }
    }
}