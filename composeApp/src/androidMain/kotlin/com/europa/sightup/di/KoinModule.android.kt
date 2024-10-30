package com.europa.sightup.di

import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.local.SharedPreferencesKVault
import com.europa.sightup.presentation.screens.test.AndroidVoiceRecognition
import com.europa.sightup.presentation.screens.test.VoiceRecognition
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module = module {
    single<KVaultStorage> { SharedPreferencesKVault(context = get()) }
    factory<VoiceRecognition> { AndroidVoiceRecognition(context = get()) }
}