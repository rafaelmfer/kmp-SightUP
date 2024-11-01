package com.europa.sightup.platformspecific

import com.europa.sightup.BuildConfig

class AndroidPlatform : PlatformOS {
    override val name: String = "ANDROID"
    override val isDebug: Boolean = BuildConfig.DEBUG
}

actual fun getPlatform(): PlatformOS = AndroidPlatform()