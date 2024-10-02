package com.europa.sightup.di

import com.europa.sightup.data.local.IOSKeychainKVault
import com.europa.sightup.data.local.KVaultStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module = module {
    single<KVaultStorage> { IOSKeychainKVault() }
}