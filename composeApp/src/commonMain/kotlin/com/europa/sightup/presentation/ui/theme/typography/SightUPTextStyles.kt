package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Immutable
data class SightUPTextStyles(
    /**
     * @property h1 is a TextStyle property that represents the h1 text style. Where:
     * @property fontFamily is Larken
     * @property fontSize is 36sp
     * @property fontWeight is FontWeight.Bold
     */
    val h1: TextStyle,
    /**
     * @property h2 is a TextStyle property that represents the h2 text style. Where:
     * @property fontFamily is Larken
     * @property fontSize is 32sp
     * @property fontWeight is FontWeight.Bold
     */
    val h2: TextStyle,
    /**
     * @property h3 is a TextStyle property that represents the h3 text style. Where:
     * @property fontFamily is Larken
     * @property fontSize is 28.sp
     * @property fontWeight is FontWeight.Bold
     */
    val h3: TextStyle,
    /**
     * @property h4 is a TextStyle property that represents the h4 text style. Where:
     * @property fontFamily is Larken
     * @property fontSize is 26.sp
     * @property fontWeight is FontWeight.Bold
     */
    val h4: TextStyle,
    /**
     * @property h5 is a TextStyle property that represents the h5 text style. Where:
     * @property fontFamily is Larken
     * @property fontSize is 24.sp
     * @property fontWeight is FontWeight.Bold
     */
    val h5: TextStyle,

    /**
     * @property large is a TextStyle property that represents the large text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 20.sp
     * @property fontWeight is FontWeight.Bold
     */
    val large: TextStyle,
    /**
     * @property subtitle is a TextStyle property that represents the subtitle text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 18.sp
     * @property fontWeight is FontWeight.Bold
     */
    val subtitle: TextStyle,
    /**
     * @property body is a TextStyle property that represents the body text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 16.sp
     * @property fontWeight is FontWeight.Regular
     */
    val body: TextStyle,
    /**
     * @property body2 is a TextStyle property that represents the body text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 14.sp
     * @property fontWeight is FontWeight.Regular
     */
    val body2: TextStyle,
    /**
     * @property button is a TextStyle property that represents the small text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 14.sp
     * @property fontWeight is FontWeight.Bold
     */
    val button: TextStyle,
    /**
     * @property caption is a TextStyle property that represents the caption text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 12.sp
     * @property fontWeight is FontWeight.Regular
     */
    val caption: TextStyle,
    /**
     * @property footnote is a TextStyle property that represents the footnote text style. Where:
     * @property fontFamily is Lato
     * @property fontSize is 10.sp
     * @property fontWeight is FontWeight.Regular
     */
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
            fontSize = SightUPFontSize.default.fontSize_lg,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        subtitle = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_md,
            fontWeight = SightUPFontWeight.default.fontWeight_bold,
        ),
        body = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_base,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        body2 = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = SightUPTheme.latoFontFamily,
            fontSize = SightUPFontSize.default.fontSize_sm,
            fontWeight = SightUPFontWeight.default.fontWeight_regular,
        ),
        button = MaterialTheme.typography.bodyLarge.copy(
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