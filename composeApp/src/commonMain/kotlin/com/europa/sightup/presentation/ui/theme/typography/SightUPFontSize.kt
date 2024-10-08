package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Immutable
data class SightUPFontSize(
    val fontSize_2xs: TextUnit = 10.sp,
    val fontSize_xs: TextUnit = 12.sp,
    val fontSize_sm: TextUnit = 14.sp,
    val fontSize_md: TextUnit = 16.sp,
    val fontSize_lg: TextUnit = 18.sp,
    val fontSize_xl: TextUnit = 20.sp,
    val fontSize_2xl: TextUnit = 24.sp,
    val fontSize_3xl: TextUnit = 28.sp,
    val fontSize_4xl: TextUnit = 32.sp,
    val fontSize_huge: TextUnit = 36.sp,
) {
    internal companion object {
        val default = SightUPFontSize(
            fontSize_2xs = 10.sp,
            fontSize_xs = 12.sp,
            fontSize_sm = 14.sp,
            fontSize_md = 16.sp,
            fontSize_lg = 18.sp,
            fontSize_xl = 20.sp,
            fontSize_2xl = 24.sp,
            fontSize_3xl = 28.sp,
            fontSize_4xl = 32.sp,
            fontSize_huge = 36.sp
        )
    }
}

val SightUPTheme.fontSize: SightUPFontSize
    @Composable
    get() = SightUPFontSize.default