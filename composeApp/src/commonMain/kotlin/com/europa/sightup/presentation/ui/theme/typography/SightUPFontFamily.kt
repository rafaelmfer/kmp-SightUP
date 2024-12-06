package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import org.jetbrains.compose.resources.Font
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.larken_bold
import sightupkmpapp.composeapp.generated.resources.larken_bold_italic
import sightupkmpapp.composeapp.generated.resources.larken_medium
import sightupkmpapp.composeapp.generated.resources.larken_medium_italic
import sightupkmpapp.composeapp.generated.resources.larken_regular
import sightupkmpapp.composeapp.generated.resources.larken_regular_italic
import sightupkmpapp.composeapp.generated.resources.lato_bold
import sightupkmpapp.composeapp.generated.resources.lato_bold_italic
import sightupkmpapp.composeapp.generated.resources.lato_regular
import sightupkmpapp.composeapp.generated.resources.lato_regular_italic

@Composable
fun LarkenFontFamily() = FontFamily(
    Font(Res.font.larken_regular, weight = FontWeight.Normal),
    Font(Res.font.larken_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(Res.font.larken_medium, weight = FontWeight.Medium),
    Font(Res.font.larken_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(Res.font.larken_bold, weight = FontWeight.Bold),
    Font(Res.font.larken_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
)

val SightUPTheme.larkenFontFamily: FontFamily
    @Composable
    get() = LarkenFontFamily()

@Composable
fun LatoFontFamily() = FontFamily(
    Font(Res.font.lato_regular, weight = FontWeight.Normal),
    Font(Res.font.lato_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(Res.font.lato_bold, weight = FontWeight.Bold),
    Font(Res.font.lato_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
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
        headlineLarge = TextStyle(
            fontFamily = larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_3xl,
            lineHeight = SightUPLineHeight.default.lineHeight_2xl,
            fontWeight = SightUPFontWeight.default.fontWeight_medium,
        ),
        headlineMedium = TextStyle(
            fontFamily = larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_2xl,
            lineHeight = SightUPLineHeight.default.lineHeight_xl,
            fontWeight = SightUPFontWeight.default.fontWeight_medium,
        ),
        headlineSmall = TextStyle(
            fontFamily = larkenFontFamily,
            fontSize = SightUPFontSize.default.fontSize_xl,
            lineHeight = SightUPLineHeight.default.lineHeight_lg,
            fontWeight = SightUPFontWeight.default.fontWeight_medium,
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
