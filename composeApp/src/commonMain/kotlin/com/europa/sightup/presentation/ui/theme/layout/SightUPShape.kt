package com.europa.sightup.presentation.ui.theme.layout

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalShapes = staticCompositionLocalOf {
    Shapes(
        /**
         * @property extraSmall is a RoundedCornerShape property that represents the extra small shape.
         * The value is set to RoundedCornerShape(SightUPBorder.Radius.xs) = 4.dp.
         */
        extraSmall = RoundedCornerShape(SightUPBorder.Radius.xs),
        /**
         * @property small is a RoundedCornerShape property that represents the small shape.
         * The value is set to RoundedCornerShape(SightUPBorder.Radius.sm) = 8.dp.
         */
        small = RoundedCornerShape(SightUPBorder.Radius.sm),
        /**
         * @property medium is a RoundedCornerShape property that represents the medium shape.
         * The value is set to RoundedCornerShape(SightUPBorder.Radius.md) = 16.dp.
         */
        medium = RoundedCornerShape(SightUPBorder.Radius.md),
        /**
         * @property large is a RoundedCornerShape property that represents the large shape.
         * The value is set to RoundedCornerShape(SightUPBorder.Radius.lg) = 24.dp.
         */
        large = RoundedCornerShape(SightUPBorder.Radius.full),
        /**
         * @property extraLarge is a CircleShape property that represents the extra large shape.
         * The value is set to CircleShape.
         */
        extraLarge = CircleShape,
    )
}
