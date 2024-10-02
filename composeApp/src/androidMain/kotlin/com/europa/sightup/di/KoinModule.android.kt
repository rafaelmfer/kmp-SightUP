package com.europa.sightup.di

import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.local.SharedPreferencesKVault
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module = module {
    single<KVaultStorage> { SharedPreferencesKVault(context = get()) }
}