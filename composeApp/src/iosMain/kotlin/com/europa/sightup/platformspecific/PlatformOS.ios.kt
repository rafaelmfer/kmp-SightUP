package com.europa.sightup.platformspecific

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
class IOSPlatform : PlatformOS {
    override val name: String = UIDevice.currentDevice.systemName().toUpperCase(Locale.current)
    override val isDebug: Boolean = Platform.isDebugBinary
}

actual fun getPlatform(): PlatformOS = IOSPlatform()