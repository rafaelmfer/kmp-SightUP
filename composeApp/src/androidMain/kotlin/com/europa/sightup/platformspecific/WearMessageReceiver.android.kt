package com.europa.sightup.platformspecific

import android.content.Context
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import org.koin.mp.KoinPlatform

actual fun createWearMessageReceiver(messagePath: String): WearMessageReceiver {
    val context = KoinPlatform.getKoin().get<Context>()
    return AndroidMessageReceiver(context, messagePath)
}

class AndroidMessageReceiver(
    private val context: Context,
    private val messagePath: String,
) : WearMessageReceiver, MessageClient.OnMessageReceivedListener {

    private var onMessageReceivedCallback: ((String) -> Unit)? = null

    override fun startListening(onMessageReceived: (String) -> Unit) {
        onMessageReceivedCallback = onMessageReceived
        Wearable.getMessageClient(context).addListener(this)
    }

    override fun stopListening() {
        Wearable.getMessageClient(context).removeListener(this)
        onMessageReceivedCallback = null
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == messagePath) {
            val message = String(messageEvent.data)
            onMessageReceivedCallback?.invoke(message)
        }
    }
}