package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material3.Typography
import com.europa.sightup.R
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Composable
fun LarkenFontFamily() = FontFamily(
    Font(R.font.larken_regular, weight = FontWeight.Normal),
    Font(R.font.larken_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.larken_medium, weight = FontWeight.Medium),
    Font(R.font.larken_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.larken_bold, weight = FontWeight.Bold),
    Font(R.font.larken_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
)

val SightUPTheme.larkenFontFamily: FontFamily
    @Composable
    get() = LarkenFontFamily()

@Composable
fun LatoFontFamily() = FontFamily(
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
)

val SightUPTheme.latoFontFamily: FontFamily
    @Composable
    get() = LatoFontFamily()

@Composable
fun SightUPTypography() = Typography().run {

    val latoFontFamily = LatoFontFamily()
    val larkenFontFamily = LarkenFontFamily()
    copy(
        displayLarge = displayLarge.copy(
            fontFamily = larkenFontFamily,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        displayMedium = displayMedium.copy(
            fontFamily = larkenFontFamily,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        displaySmall = displaySmall.copy(
            fontFamily = larkenFontFamily,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),

        titleLarge = TextStyle(
            fontFamily = larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_lg,
            lineHeight = SightUPLineHeight.default.lineHeight_md,
            fontWeight = SightUPFontWeight.default.fontWeight_medium,
        ),
        titleMedium = TextStyle(
            fontFamily = larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_md,
            lineHeight = SightUPLineHeight.default.lineHeight_sm,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        titleSmall = TextStyle(
            fontFamily = larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_sm,
            lineHeight = SightUPLineHeight.default.lineHeight_xs,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),

        bodyLarge = TextStyle(
            fontFamily = latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_md,
            lineHeight = SightUPLineHeight.default.lineHeight_md,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        bodyMedium = TextStyle(
            fontFamily = latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_sm,
            lineHeight = SightUPLineHeight.default.lineHeight_sm,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        bodySmall = TextStyle(
            fontFamily = latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_xs,
            lineHeight = SightUPLineHeight.default.lineHeight_xs,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),

        labelLarge = TextStyle(
            fontFamily = latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_md,
            lineHeight = SightUPLineHeight.default.lineHeight_sm,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        labelMedium = TextStyle(
            fontFamily = latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_sm,
            lineHeight = SightUPLineHeight.default.lineHeight_xs,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        labelSmall = TextStyle(
            fontFamily = latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_xs,
            lineHeight = SightUPLineHeight.default.lineHeight_2xs,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
    )
}
