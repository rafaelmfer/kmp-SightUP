package com.europa.sightup.platformspecific

import android.view.View
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun StatusBarNavBarColors(statusBarColor: Color, navBarColor: Color) {
    val activity = LocalContext.current as ComponentActivity
    val window = activity.window

    window.statusBarColor = statusBarColor.toArgb()
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}