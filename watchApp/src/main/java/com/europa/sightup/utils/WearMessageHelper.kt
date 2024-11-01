package com.europa.sightup.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WearMessageHelper(private val context: Context) {

    companion object {
        private const val TAG = "WearMessageHelper"
    }

    private val messageClient: MessageClient = Wearable.getMessageClient(context)
    private val nodeClient: NodeClient = Wearable.getNodeClient(context)
    private var nodeId: String? = null

    private val internalScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private suspend fun sendMessage(messagePath: String, message: String): Boolean {
        val nodeId = nodeId ?: getNodeId() ?: return false
        return try {
            Tasks.await(messageClient.sendMessage(nodeId, messagePath, message.toByteArray()))
            Log.d(TAG, "Message sent successfully")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send message", e)
            false
        }
    }

    private suspend fun getNodeId(): String? {
        return withContext(Dispatchers.IO) {
            val nodes = Tasks.await(nodeClient.connectedNodes)
            nodeId = nodes.firstOrNull()?.id
            nodeId
        }
    }

    /**
     * Function to send a message
     * @param path The message's path.
     * @param message The message's content.
     * @param coroutineScope A CoroutineScope optional. If provided, it'll be used. Otherwise, the internal scope will be used.
     */
    fun sendWearMessage(
        path: String,
        message: String,
        coroutineScope: CoroutineScope = internalScope,
    ) {
        coroutineScope.launch {
            val success = sendMessage(path, message)
            if (success) {
                Log.d(TAG, "sendWearMessage(): Message sent successfully")
            } else {
                Log.e(TAG, "sendWearMessage(): Message failed to send")
            }
        }
    }

    fun clear() {
        internalScope.cancel()
    }
}

