package com.europa.sightup.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.europa.sightup.R
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.fontSize

@Composable
fun SDSControlEWear(
    upButtonOnClickResult: () -> Unit,
    leftButtonOnClickResult: () -> Unit,
    rightButtonOnClickResult: () -> Unit,
    downButtonOnClickResult: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { upButtonOnClickResult() },
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier,
        ) {
            Text(
                text = "Up",
                fontSize = SightUPTheme.fontSize.fontSize_xs
            )
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        Row(
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Button(
                onClick = { leftButtonOnClickResult() },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier,
            ) {
                Text(
                    text = "Left",
                    fontSize = SightUPTheme.fontSize.fontSize_xs
                )
            }
            Image(
                painter = painterResource(R.drawable.e_right),
                contentDescription = "Center Image",
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
            Button(
                onClick = { rightButtonOnClickResult() },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier,
            ) {
                Text(
                    text = "Right",
                    fontSize = SightUPTheme.fontSize.fontSize_xs
                )
            }
        }
        Button(
            onClick = { downButtonOnClickResult() },
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier,
        ) {
            Text(
                text = "Down",
                fontSize = SightUPTheme.fontSize.fontSize_xs
            )
        }
    }
}