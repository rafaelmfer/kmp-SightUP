package com.europa.sightup.platformspecific

interface WearMessageReceiver {
    fun startListening(onMessageReceived: (String) -> Unit)
    fun stopListening()
}

expect fun createWearMessageReceiver(messagePath: String): WearMessageReceiver