package com.europa.sightup.data.local

import android.content.Context
import com.liftric.kvault.KVault

actual fun getKVaultStorage(context: Any?): KVaultStorage {
    return SharedPreferencesKVault(context as Context)
}

class SharedPreferencesKVault(private val context: Context) : KVaultStorage {
    override val kVault: KVault get() = KVault(context = context)
}