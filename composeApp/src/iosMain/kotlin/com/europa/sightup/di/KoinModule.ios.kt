package com.europa.sightup.di

import com.europa.sightup.data.local.IOSKeychainKVault
import com.europa.sightup.data.local.KVaultStorage
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module = module {
    single<KVaultStorage> { IOSKeychainKVault() }
}

// Swift Helper to inject Koin dependencies on iOS
object KoinSwiftHelper : KoinComponent {
    val koin = getKoin()

    fun getKVaultStorage() = koin.get<KVaultStorage>()
}