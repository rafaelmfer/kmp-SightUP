package com.europa.sightup.platformspecific

actual fun createWearMessageReceiver(messagePath: String): WearMessageReceiver {
    return IOSMessageReceiver()
}

class IOSMessageReceiver : WearMessageReceiver {

    override fun startListening(onMessageReceived: (String) -> Unit) {
        // Do nothing for iOS
    }

    override fun stopListening() {
        // Do nothing for iOS
    }
}