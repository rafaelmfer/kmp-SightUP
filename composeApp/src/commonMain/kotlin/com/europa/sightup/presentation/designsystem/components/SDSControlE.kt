package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun SDSControlE(
    modifier: Modifier = Modifier,
    upButtonOnClickResult: () -> Unit,
    leftButtonOnClickResult: () -> Unit,
    rightButtonOnClickResult: () -> Unit,
    downButtonOnClickResult: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SDSButton(
            onClick = { upButtonOnClickResult() },
            modifier = Modifier.size(80.dp),
            shape = SightUPTheme.shapes.extraLarge,
            text = "Up",
            contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            SDSButton(
                onClick = { leftButtonOnClickResult() },
                modifier = Modifier.size(80.dp),
                shape = SightUPTheme.shapes.extraLarge,
                text = "Left",
                contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
            )
            Spacer(modifier = Modifier.width(SightUPTheme.spacing.spacing_base))
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Center Image",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(SightUPTheme.spacing.spacing_base))
            SDSButton(
                onClick = { rightButtonOnClickResult() },
                modifier = Modifier.size(80.dp),
                shape = SightUPTheme.shapes.extraLarge,
                text = "Right",
                contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
            )
        }
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
        SDSButton(
            onClick = { downButtonOnClickResult() },
            modifier = Modifier.size(80.dp),
            shape = SightUPTheme.shapes.extraLarge,
            text = "Down",
            contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
        )

    }
}