package com.europa.sightup.platformspecific

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIInterfaceOrientationMaskLandscapeRight
import platform.UIKit.UIInterfaceOrientationMaskPortrait
import platform.UIKit.UIWindowSceneGeometryPreferencesIOS

@Composable
actual fun LandscapeOrientation(
    isLandscape: Boolean,
    content: @Composable () -> Unit
) {
    DisposableEffect(isLandscape) {
        val windowScene = UIApplication.sharedApplication.keyWindow?.windowScene
        val orientation = if (isLandscape) {
            UIInterfaceOrientationMaskLandscapeRight
        } else {
            UIInterfaceOrientationMaskPortrait
        }
        windowScene?.requestGeometryUpdateWithPreferences(UIWindowSceneGeometryPreferencesIOS().apply {
            interfaceOrientations = orientation
        }, errorHandler = null)
        onDispose {
            windowScene?.requestGeometryUpdateWithPreferences(UIWindowSceneGeometryPreferencesIOS().apply {
                interfaceOrientations = UIInterfaceOrientationMaskPortrait
            }, errorHandler = null)
        }
    }
    content()
}