package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_right

@Preview
@Composable
fun SDSButtonArrowScreen() {
    Column(
        modifier = Modifier.padding(SightUPTheme.spacing.spacing_base),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = SightUPTheme.spacing.spacing_base,
            alignment = Alignment.CenterVertically
        )
    ) {
        SDSButtonArrow(
            text = "Next",
            onClick = {}
        )
        SDSButtonArrow(
            text = "Next",
            onClick = {}
        )
        SDSButtonArrow(
            text = "Next",
            onClick = {}
        )
        SDSButtonArrow(
            text = "Next",
            onClick = {}
        )
    }
}

@Composable
fun SDSButtonArrow(
    text: String = "",
    onClick: () -> Unit = {},
    textStyle: TextStyle = SightUPTheme.textStyles.body2,
    shape: Shape = SightUPTheme.shapes.extraLarge,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = SightUPTheme.spacing.spacing_base,
        vertical = SightUPTheme.spacing.spacing_xs
    ),
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = SightUPTheme.sightUPColors.info_100,
            contentColor = SightUPTheme.sightUPColors.text_primary,
        ),
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            style = textStyle,
            lineHeight = 2.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Spacer(modifier = Modifier.width(SightUPTheme.spacing.spacing_xs))
        Icon(
            painter = painterResource(Res.drawable.arrow_right),
            contentDescription = null,
            modifier = Modifier
                .size(SightUPTheme.sizes.size_16)
        )
    }
}