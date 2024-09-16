package com.europa.sightup

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform