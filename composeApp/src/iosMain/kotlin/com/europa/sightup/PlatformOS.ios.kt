package com.europa.sightup

import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
class IOSPlatform: PlatformOS {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val isDebug: Boolean = Platform.isDebugBinary
}

actual fun getPlatform(): PlatformOS = IOSPlatform()