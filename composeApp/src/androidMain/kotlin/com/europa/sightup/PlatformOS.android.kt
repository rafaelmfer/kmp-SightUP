package com.europa.sightup

class AndroidPlatform : PlatformOS {
    override val name: String = "ANDROID"
    override val isDebug: Boolean = BuildConfig.DEBUG
}

actual fun getPlatform(): PlatformOS = AndroidPlatform()