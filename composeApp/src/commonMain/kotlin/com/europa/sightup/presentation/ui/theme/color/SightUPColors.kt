package com.europa.sightup.presentation.ui.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SightUPColors(
    // Unused colors, colors created just to fulfill the requirements of the Material Theme
    val border_transparent: Color = SightUPContextColor.border_transparent,
    val background_overlay_dark: Color = SightUPContextColor.background_overlay_dark,

    // SightUP Colors
    val black: Color = SightUPColorPalette.black,
    val white: Color = SightUPColorPalette.white,

    // Primary Colors
    /**
     * Primary color is the color #E8F0F6
     * @see SightUPColorPalette.primary_100
     */
    val primary_100: Color = SightUPColorPalette.primary_100,
    /**
     * Primary color is the color #D3E3EE
     * @see SightUPColorPalette.primary_200
     */
    val primary_200: Color = SightUPColorPalette.primary_200,
    /**
     * Primary color is the color #A9C8DC
     * @see SightUPColorPalette.primary_300
     */
    val primary_300: Color = SightUPColorPalette.primary_300,
    /**
     * Primary color is the color #80ACCB
     * @see SightUPColorPalette.primary_400
     */
    val primary_400: Color = SightUPColorPalette.primary_400,
    /**
     * Primary color is the color #5691B9
     * @see SightUPColorPalette.primary_500
     */
    val primary_500: Color = SightUPColorPalette.primary_500,
    /**
     * Primary color is the color #2C76A8
     * @see SightUPColorPalette.primary_600
     */
    val primary_600: Color = SightUPColorPalette.primary_600,
    /**
     * Primary color is the color #235E86
     * @see SightUPColorPalette.primary_700
     */
    val primary_700: Color = SightUPColorPalette.primary_700,
    /**
     * Primary color is the color #1A4765
     * @see SightUPColorPalette.primary_800
     */
    val primary_800: Color = SightUPColorPalette.primary_800,


    // Neutral Colors
    /**
     * Neutral color is the color #FEFEFE
     * @see SightUPColorPalette.neutral_0
     */
    val neutral_0: Color = SightUPColorPalette.neutral_0,
    /**
     * Neutral color is the color #EAECEE
     * @see SightUPColorPalette.neutral_100
     */
    val neutral_100: Color = SightUPColorPalette.neutral_100,
    /**
     * Neutral color is the color #D5D9DE
     * @see SightUPColorPalette.neutral_200
     */
    val neutral_200: Color = SightUPColorPalette.neutral_200,
    /**
     * Neutral color is the color #BFC7CD
     * @see SightUPColorPalette.neutral_300
     */
    val neutral_300: Color = SightUPColorPalette.neutral_300,
    /**
     * Neutral color is the color #AAB4BD
     * @see SightUPColorPalette.neutral_400
     */
    val neutral_400: Color = SightUPColorPalette.neutral_400,
    /**
     * Neutral color is the color #95A1AC
     * @see SightUPColorPalette.neutral_500
     */
    val neutral_500: Color = SightUPColorPalette.neutral_500,
    /**
     * Neutral color is the color #77818A
     * @see SightUPColorPalette.neutral_600
     */
    val neutral_600: Color = SightUPColorPalette.neutral_600,
    /**
     * Neutral color is the color #596167
     * @see SightUPColorPalette.neutral_700
     */
    val neutral_700: Color = SightUPColorPalette.neutral_700,
    /**
     * Neutral color is the color #3C4045
     * @see SightUPColorPalette.neutral_800
     */
    val neutral_800: Color = SightUPColorPalette.neutral_800,
    /**
     * Neutral color is the color #1E2022
     * @see SightUPColorPalette.neutral_900
     */
    val neutral_900: Color = SightUPColorPalette.neutral_900,


    // Orange Colors
    /**
     * Orange color is the color #F9E4D6
     * @see SightUPColorPalette.orange_100
     */
    val orange_100: Color = SightUPColorPalette.orange_100,
    /**
     * Orange color is the color #DC6A1E
     * @see SightUPColorPalette.orange_200
     */
    val orange_200: Color = SightUPColorPalette.orange_200,
    /**
     * Orange color is the color #CD6B29
     * @see SightUPColorPalette.orange_300
     */
    val orange_300: Color = SightUPColorPalette.orange_300,


    // Success Colors
    /**
     * Success color is the color #ECFBED
     * @see SightUPColorPalette.success_100
     */
    val success_100: Color = SightUPColorPalette.success_100,
    /**
     * Success color is the color #79B97D
     * @see SightUPColorPalette.success_200
     */
    val success_200: Color = SightUPColorPalette.success_200,
    /**
     * Success color is the color #57925B
     * @see SightUPColorPalette.success_300
     */
    val success_300: Color = SightUPColorPalette.success_300,


    // Error Colors
    /**
     * Error color is the color #FFEAE4
     * @see SightUPColorPalette.error_100
     */
    val error_100: Color = SightUPColorPalette.error_100,
    /**
     * Error color is the color #F95C30
     * @see SightUPColorPalette.error_200
     */
    val error_200: Color = SightUPColorPalette.error_200,
    /**
     * Error color is the color #BE4A2A
     * @see SightUPColorPalette.error_300
     */
    val error_300: Color = SightUPColorPalette.error_300,


    // Warning Colors
    /**
     * Warning color is the color #FFF6DB
     * @see SightUPColorPalette.warning_100
     */
    val warning_100: Color = SightUPColorPalette.warning_100,
    /**
     * Warning color is the color #D1A626
     * @see SightUPColorPalette.warning_200
     */
    val warning_200: Color = SightUPColorPalette.warning_200,
    /**
     * Warning color is the color #B8901A
     * @see SightUPColorPalette.warning_300
     */
    val warning_300: Color = SightUPColorPalette.warning_300,


    // Info Colors
    /**
     * Info color is the color #E5F7FF
     * @see SightUPColorPalette.info_100
     */
    val info_100: Color = SightUPColorPalette.info_100,
    /**
     * Info color is the color #1BA7E7
     * @see SightUPColorPalette.info_200
     */
    val info_200: Color = SightUPColorPalette.info_200,
    /**
     * Info color is the color #11719C
     * @see SightUPColorPalette.info_300
     */
    val info_300: Color = SightUPColorPalette.info_300,

    // Purple Colors
    /**
     * Purple color is the color #F5F1FF
     * @see SightUPColorPalette.purple_100
     */
    val purple_100: Color = SightUPColorPalette.purple_100,
    /**
     * Purple color is the color #A890E3
     * @see SightUPColorPalette.purple_200
     */
    val purple_200: Color = SightUPColorPalette.purple_200,
    /**
     * Purple color is the color #6645BA
     * @see SightUPColorPalette.purple_300
     */
    val purple_300: Color = SightUPColorPalette.purple_300,


    // Divider Color
    /**
     * Divider color is the color Black with 0.08 alpha (8%)
     * @see SightUPContextColor.divider
     */
    val divider: Color = SightUPContextColor.divider,


    // Text Colors
    /**
     * Text primary color is the color #1E2022
     * @see SightUPContextColor.text_primary
     */
    val text_primary: Color = SightUPContextColor.text_primary,
    /**
     * Text secondary color is the color #FEFEFE
     * @see SightUPContextColor.text_secondary
     */
    val text_secondary: Color = SightUPContextColor.text_secondary,
    /**
     * Text tertiary color is the color #AAB4BD
     * @see SightUPContextColor.text_tertiary
     */
    val text_tertiary: Color = SightUPContextColor.text_tertiary,
    /**
     * Text disabled color is the color #95A1AC
     * @see SightUPContextColor.text_disabled
     */
    val text_disabled: Color = SightUPContextColor.text_disabled,


    // Border Colors
    /**
     * Border default color is the color #D5D9DE
     * @see SightUPContextColor.border_default
     */
    val border_default: Color = SightUPContextColor.border_default,
    /**
     * Border primary color is the color #2C76A8
     * @see SightUPContextColor.border_primary
     */
    val border_primary: Color = SightUPContextColor.border_primary,
    /**
     * Border disabled color is the color #AAB4BD
     * @see SightUPContextColor.border_disabled
     */
    val border_disabled: Color = SightUPContextColor.border_disabled,
    /**
     * Border card color is the color #E8F0F6
     * @see SightUPContextColor.border_card
     */
    val border_card: Color = SightUPContextColor.border_card,
    /**
     * Border error color is the color #FFEFF2
     * @see SightUPContextColor.border_error
     */
    val border_error: Color = SightUPContextColor.border_error,
    /**
     * Border warning color is the color #FFE8E2
     * @see SightUPContextColor.border_warning
     */
    val border_warning: Color = SightUPContextColor.border_warning,
    /**
     * Border success color is the color #DEFFE0
     * @see SightUPContextColor.border_success
     */
    val border_success: Color = SightUPContextColor.border_success,
    /**
     * Border info color is the color #DEF5FF
     * @see SightUPContextColor.border_info
     */
    val border_info: Color = SightUPContextColor.border_info,
    /**
     * Border purple color is the color #EDE6FF
     * @see SightUPContextColor.border_purple
     */
    val border_purple: Color = SightUPContextColor.border_purple,


    // Background Colors
    /**
     * Background default color is the color #FEFEFE
     * @see SightUPContextColor.background_default
     */
    val background_default: Color = SightUPContextColor.background_default,
    /**
     * Background light color is the color #F5FCFF
     * @see SightUPContextColor.background_light
     */
    val background_light: Color = SightUPContextColor.background_light,
    /**
     * Background card color is the color #EAECEE
     * @see SightUPContextColor.background_card
     */
    val background_card: Color = SightUPContextColor.background_card,
    /**
     * Background button color is the color #2C76A8
     * @see SightUPContextColor.background_button
     */
    val background_button: Color = SightUPContextColor.background_button,
    /**
     * Background disabled color is the color #D5D9DE
     * @see SightUPContextColor.background_disabled
     */
    val background_disabled: Color = SightUPContextColor.background_disabled,
    /**
     * Background activate color is the color #D3E3EE
     * @see SightUPContextColor.background_activate
     */
    val background_activate: Color = SightUPContextColor.background_activate,
    /**
     * Background overlay color is the color #1E2022
     * @see SightUPContextColor.background_overlay
     */
    val background_overlay: Color = SightUPContextColor.background_overlay,
    /**
     * Background error color is the color #FFEAE4
     * @see SightUPContextColor.background_error
     */
    val background_error: Color = SightUPContextColor.background_error,
    /**
     * Background warning color is the color #FFF6DB
     * @see SightUPContextColor.background_warning
     */
    val background_warning: Color = SightUPContextColor.background_warning,
    /**
     * Background success color is the color #ECFBED
     * @see SightUPContextColor.background_success
     */
    val background_success: Color = SightUPContextColor.background_success,
    /**
     * Background info color is the color #E5F7FF
     * @see SightUPContextColor.background_info
     */
    val background_info: Color = SightUPContextColor.background_info,
    /**
     * Background purple color is the color #F5F1FF
     * @see SightUPContextColor.background_purple
     */
    val background_purple: Color = SightUPContextColor.background_purple,
    /**
     * Background orange color is the color #F9E4D6
     * @see SightUPContextColor.background_orange
     */
    val background_orange: Color = SightUPContextColor.background_orange,
)

internal val LocalSightUPColors = staticCompositionLocalOf { SightUPColors() }