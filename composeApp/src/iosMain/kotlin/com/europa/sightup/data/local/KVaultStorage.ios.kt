package com.europa.sightup.data.local

import com.liftric.kvault.KVault

actual fun getKVaultStorage(context: Any?): KVaultStorage {
    return IOSKeychainKVault()
}

class IOSKeychainKVault : KVaultStorage {
    override val kVault get() = KVault()
}