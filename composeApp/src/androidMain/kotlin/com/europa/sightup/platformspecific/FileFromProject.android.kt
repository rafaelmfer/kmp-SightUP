package com.europa.sightup.platformspecific

actual fun getLocalFilePathFor(item: String): String {
    return "asset:///$item"
}