package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontWeight
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Immutable
data class SightUPFontWeight(
    val fontWeight_regular: FontWeight,
    val fontWeight_medium: FontWeight,
    val fontWeight_bold: FontWeight
) {
    internal companion object {
        val default = SightUPFontWeight(
            fontWeight_regular = FontWeight.Normal,
            fontWeight_medium = FontWeight.Medium,
            fontWeight_bold = FontWeight.Bold
        )
    }
}

val SightUPTheme.fontWeight: SightUPFontWeight
    get() = SightUPFontWeight.default