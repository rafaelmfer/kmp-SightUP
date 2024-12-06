package com.europa.sightup.presentation.ui.theme.layout

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
class SightUPBorder {
    object Radius {
        /**
         * @property none is a Dp property that represents the none radius value.
         * The value is set to 0dp.
         */
        val none = 0.dp
        /**
         * @property xs is a Dp property that represents the extra small radius value.
         * The value is set to 2dp.
         */
        val xs = 4.dp
        /**
         * @property sm is a Dp property that represents the small radius value.
         * The value is set to 8dp.
         */
        val sm = 8.dp
        /**
         * @property md is a Dp property that represents the medium radius value.
         * The value is set to 12dp.
         */
        val md = 16.dp
        /**
         * @property lg is a Dp property that represents the large radius value.
         * The value is set to 24dp.
         */
        val lg = 24.dp
        /**
         * @property xl is a Dp property that represents the extra large radius value.
         * The value is set to 32dp.
         */
        val xl = 32.dp
        /**
         * @property full is a Dp property that represents the full radius value.
         * The value is set to 360dp.
         */
        val full = 360.dp
    }

    object Width {
        /**
         * @property none is a Dp property that represents the none width value.
         * The value is set to 0dp.
         */
        val none = 0.dp
        /**
         * @property sm is a Dp property that represents the extra small width value.
         * The value is set to 1dp.
         */
        val sm = 1.dp
        /**
         * @property md is a Dp property that represents the small width value.
         * The value is set to 2dp.
         */
        val md = 2.dp
        /**
         * @property lg is a Dp property that represents the medium width value.
         * The value is set to 4dp.
         */
        val lg = 4.dp
        /**
         * @property xl is a Dp property that represents the large width value.
         * The value is set to 8dp.
         */
        val xl = 8.dp
    }
}