package com.europa.sightup

import com.europa.sightup.platformspecific.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}