package com.europa.sightup.presentation.ui.theme.layout

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalShapes = staticCompositionLocalOf {
    Shapes(
        /**xs**/
        extraSmall = RoundedCornerShape(SightUPBorder.Radius.xs),
        /**sm**/
        small = RoundedCornerShape(SightUPBorder.Radius.sm),
        /**md**/
        medium = RoundedCornerShape(SightUPBorder.Radius.md),
        /**full**/
        large = RoundedCornerShape(SightUPBorder.Radius.full),
        /**circle**/
        extraLarge = CircleShape,
    )
}
