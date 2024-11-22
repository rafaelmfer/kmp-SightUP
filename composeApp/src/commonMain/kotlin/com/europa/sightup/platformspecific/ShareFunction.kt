package com.europa.sightup.platformspecific

interface ShareService {
    fun shareText(text: String)
}

expect fun getShareService(): ShareService