package com.europa.sightup

import androidx.compose.ui.window.ComposeUIViewController
import com.europa.sightup.di.initializeKoin
import com.europa.sightup.utils.PostsWithState

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) {
     App()
    //PostsWithState()
}