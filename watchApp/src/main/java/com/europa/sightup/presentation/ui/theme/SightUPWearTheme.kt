package com.europa.sightup.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme
import com.europa.sightup.presentation.ui.theme.SightUPTheme.colors
import com.europa.sightup.presentation.ui.theme.SightUPTheme.sightUPColors
import com.europa.sightup.presentation.ui.theme.color.LocalColors
import com.europa.sightup.presentation.ui.theme.color.LocalSightUPColors
import com.europa.sightup.presentation.ui.theme.color.SightUPColors
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
}

@Composable
fun SightUPTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalSightUPColors provides sightUPColors,
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = SightUPTypography(),
            content = content,
        )
    }
}
