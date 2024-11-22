package com.europa.sightup.platformspecific

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual fun getShareService(): ShareService = IOSShareService()

class IOSShareService : ShareService {
    override fun shareText(text: String) {
        val activityItems = listOf(text)
        val activityViewController = UIActivityViewController(
            activityItems = activityItems,
            applicationActivities = null
        )

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(activityViewController, animated = true, completion = null)
    }
}