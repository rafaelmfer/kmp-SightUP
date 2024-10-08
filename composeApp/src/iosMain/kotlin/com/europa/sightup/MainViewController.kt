package com.europa.sightup

import androidx.compose.ui.window.ComposeUIViewController
import com.europa.sightup.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) {
    Init()
}