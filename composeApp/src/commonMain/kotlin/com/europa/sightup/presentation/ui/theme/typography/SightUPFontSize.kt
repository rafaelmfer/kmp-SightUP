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
    val fontSize_extra_huge: TextUnit = 40.sp,
) {
    internal companion object {
        val default = SightUPFontSize(
            /**
             * @property fontSize_2xs is a TextUnit property that represents the 2xs font size value.
             * The value is set to 10.sp.
             */
            fontSize_2xs = 10.sp,
            /**
             * @property fontSize_xs is a TextUnit property that represents the xs font size value.
             * The value is set to 12.sp.
             */
            fontSize_xs = 12.sp,
            /**
             * @property fontSize_sm is a TextUnit property that represents the sm font size value.
             * The value is set to 14.sp.
             */
            fontSize_sm = 14.sp,
            /**
             * @property fontSize_md is a TextUnit property that represents the md font size value.
             * The value is set to 16.sp.
             */
            fontSize_md = 16.sp,
            /**
             * @property fontSize_lg is a TextUnit property that represents the lg font size value.
             * The value is set to 18.sp.
             */
            fontSize_lg = 18.sp,
            /**
             * @property fontSize_xl is a TextUnit property that represents the xl font size value.
             * The value is set to 20.sp.
             */
            fontSize_xl = 20.sp,
            /**
             * @property fontSize_2xl is a TextUnit property that represents the 2xl font size value.
             * The value is set to 24.sp.
             */
            fontSize_2xl = 24.sp,
            /**
             * @property fontSize_3xl is a TextUnit property that represents the 3xl font size value.
             * The value is set to 28.sp.
             */
            fontSize_3xl = 28.sp,
            /**
             * @property fontSize_4xl is a TextUnit property that represents the 4xl font size value.
             * The value is set to 32.sp.
             */
            fontSize_4xl = 32.sp,
            /**
             * @property fontSize_huge is a TextUnit property that represents the huge font size value.
             * The value is set to 36.sp.
             */
            fontSize_huge = 36.sp,
            /**
             * @property fontSize_extra_huge is a TextUnit property that represents the extra huge font size value.
             * The value is set to 40.sp.
             */
            fontSize_extra_huge = 40.sp,
        )
    }
}

val SightUPTheme.fontSize: SightUPFontSize
    @Composable
    get() = SightUPFontSize.default