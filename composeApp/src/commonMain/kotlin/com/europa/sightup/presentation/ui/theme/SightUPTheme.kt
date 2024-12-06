package com.europa.sightup.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.europa.sightup.presentation.ui.theme.SightUPTheme.colors
import com.europa.sightup.presentation.ui.theme.SightUPTheme.shapes
import com.europa.sightup.presentation.ui.theme.SightUPTheme.sightUPColors
import com.europa.sightup.presentation.ui.theme.color.LocalColors
import com.europa.sightup.presentation.ui.theme.color.LocalSightUPColors
import com.europa.sightup.presentation.ui.theme.color.SightUPColors
import com.europa.sightup.presentation.ui.theme.layout.LocalShapes
import com.europa.sightup.presentation.ui.theme.typography.SightUPTypography

/**
 * @see <a href="https://developer.android.com/jetpack/compose/designsystems/custom#implementing-using-material-components">Custom design systems in Compose</a>
 */
object SightUPTheme {
    val sightUPColors: SightUPColors
        @Composable @ReadOnlyComposable
        get() = LocalSightUPColors.current

    val colors: ColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalColors.current

    val shapes: Shapes
        @Composable @ReadOnlyComposable
        get() = LocalShapes.current
}
@Composable
fun SightUPTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalSightUPColors provides sightUPColors,
        LocalColors provides colors,
        LocalShapes provides shapes,
    ) {
        MaterialTheme(
            colorScheme = colors,
            shapes = shapes,
            typography = SightUPTypography(),
            content = content,
        )
    }
}
