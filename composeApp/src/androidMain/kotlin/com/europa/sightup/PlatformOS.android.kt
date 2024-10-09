package com.europa.sightup

import android.os.Build

class AndroidPlatform : PlatformOS {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val isDebug: Boolean = BuildConfig.DEBUG
}

actual fun getPlatform(): PlatformOS = AndroidPlatform()