package com.europa.sightup.platformspecific

import platform.Foundation.NSBundle

actual fun getLocalFilePathFor(item: String): String {
    val videoFilePath = NSBundle.mainBundle.pathForResource(item, null)
    return videoFilePath ?: ""
}