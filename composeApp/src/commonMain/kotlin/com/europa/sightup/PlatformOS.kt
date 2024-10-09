package com.europa.sightup

interface PlatformOS {
    val name: String
    val isDebug: Boolean
}

expect fun getPlatform(): PlatformOS