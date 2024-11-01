package com.europa.sightup.platformspecific

interface PlatformOS {
    val name: String
    val isDebug: Boolean
}

expect fun getPlatform(): PlatformOS