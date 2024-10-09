package com.europa.sightup.presentation.ui.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.ui.theme.SightUPTheme

@Immutable
data class SightUPLineHeight(
    val lineHeight_2xs: TextUnit = 16.sp,
    val lineHeight_xs: TextUnit = 20.sp,
    val lineHeight_sm: TextUnit = 24.sp,
    val lineHeight_md: TextUnit = 28.sp,
    val lineHeight_lg: TextUnit = 32.sp,
    val lineHeight_xl: TextUnit = 36.sp,
    val lineHeight_2xl: TextUnit = 40.sp,
    val lineHeight_3xl: TextUnit = 44.sp
) {
    internal companion object {
        val default = SightUPLineHeight(
            /**
             * @property lineHeight_2xs is a TextUnit property that represents the 2xs line height value.
             * The value is set to 16.sp.
             */
            lineHeight_2xs = 16.sp,
            /**
             * @property lineHeight_xs is a TextUnit property that represents the xs line height value.
             * The value is set to 20.sp.
             */
            lineHeight_xs = 20.sp,
            /**
             * @property lineHeight_sm is a TextUnit property that represents the sm line height value.
             * The value is set to 24.sp.
             */
            lineHeight_sm = 24.sp,
            /**
             * @property lineHeight_md is a TextUnit property that represents the md line height value.
             * The value is set to 28.sp.
             */
            lineHeight_md = 28.sp,
            /**
             * @property lineHeight_lg is a TextUnit property that represents the lg line height value.
             * The value is set to 32.sp.
             */
            lineHeight_lg = 32.sp,
            /**
             * @property lineHeight_xl is a TextUnit property that represents the xl line height value.
             * The value is set to 36.sp.
             */
            lineHeight_xl = 36.sp,
            /**
             * @property lineHeight_2xl is a TextUnit property that represents the 2xl line height value.
             * The value is set to 40.sp.
             */
            lineHeight_2xl = 40.sp,
            /**
             * @property lineHeight_3xl is a TextUnit property that represents the 3xl line height value.
             * The value is set to 44.sp.
             */
            lineHeight_3xl = 44.sp,
        )
    }
}

val SightUPTheme.lineHeight: SightUPLineHeight
    @Composable
    get() = SightUPLineHeight.default