package com.europa.sightup.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun TextStylesScreen() {
    val modifier = Modifier.padding(SightUPTheme.spacing.spacing_base)

    Column {
        Text(
            modifier = modifier,
            text = "H1 Larken Bold - 36dp",
            style = SightUPTheme.textStyles.h1
        )
        Text(
            modifier = modifier,
            text = "H2 Larken Bold - 32dp",
            style = SightUPTheme.textStyles.h2
        )
        Text(
            modifier = modifier,
            text = "H3 Larken Bold - 28dp",
            style = SightUPTheme.textStyles.h3
        )
        Text(
            modifier = modifier,
            text = "H4 Larken Bold - 24dp",
            style = SightUPTheme.textStyles.h4
        )
        Text(
            modifier = modifier,
            text = "H5 Larken Medium - 20dp",
            style = SightUPTheme.textStyles.h5
        )
        Text(
            modifier = modifier,
            text = "Large Lato Bold - 20dp",
            style = SightUPTheme.textStyles.large
        )
        Text(
            modifier = modifier,
            text = "Subtitle Lato Bold - 18dp",
            style = SightUPTheme.textStyles.subtitle
        )
        Text(
            modifier = modifier,
            text = "Body Lato - 16dp",
            style = SightUPTheme.textStyles.body
        )
        Text(
            modifier = modifier,
            text = "Small Lato Bold - 14dp",
            style = SightUPTheme.textStyles.small
        )
        Text(
            modifier = modifier,
            text = "Caption Lato - 12dp",
            style = SightUPTheme.textStyles.caption
        )
        Text(
            modifier = modifier,
            text = "Footnote Lato - 10dp",
            style = SightUPTheme.textStyles.footnote
        )
    }
}