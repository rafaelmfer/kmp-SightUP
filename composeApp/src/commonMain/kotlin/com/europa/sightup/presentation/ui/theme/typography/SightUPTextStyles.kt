package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Immutable
data class SightUPTextStyles(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,

    val large: TextStyle,
    val subtitle: TextStyle,
    val body: TextStyle,
    val small: TextStyle,
    val caption: TextStyle,
    val footnote: TextStyle,
)

val SightUPTheme.textStyles: SightUPTextStyles
    @Composable
    get() = SightUPTextStyles(
        h1 = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = SightUPTheme.larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_huge,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        h2 = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = SightUPTheme.larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_4xl,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        h3 = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = SightUPTheme.larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_3xl,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        h4 = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = SightUPTheme.larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_2xl,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        h5 = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = SightUPTheme.larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_xl,
            fontWeight = SightUPFontWeight.default.fontWeight_medium,
        ),

        large = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_xl,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        subtitle = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_lg,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        body = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_md,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        small = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_sm,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        caption = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_xs,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        footnote = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_2xs,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
    )