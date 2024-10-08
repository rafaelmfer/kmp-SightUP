package com.europa.sightup.di

import com.europa.sightup.BuildConfig
import com.europa.sightup.data.network.NetworkClient
import com.europa.sightup.data.remote.api.JsonPlaceholderApiService
import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.repository.JsonPlaceholderRepository
import com.europa.sightup.data.repository.SightUpApiRespository
import com.europa.sightup.presentation.MainViewModel
import com.europa.sightup.presentation.screens.test.TestViewModel
import io.ktor.client.HttpClient
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val targetModule: Module

val commonModule = module {
    // NetworkClient
    single<HttpClient> { NetworkClient.provideHttpClient(kVaultStorage = get()) }
    single<JsonPlaceholderApiService> {
        NetworkClient.provideKtorfit(
            baseUrl = BuildConfig.BASE_URL,
            httpClient = get()
        ).create()
    }
    single<SightUpApiService>{
        NetworkClient.provideKtorfit(
            baseUrl = BuildConfig.BASE_URL_BACKEND,
            httpClient = get()
        ).create()
    }

    // Repositories
    single<JsonPlaceholderRepository> { JsonPlaceholderRepository(api = get()) }
    single<SightUpApiRespository> { SightUpApiRespository(api = get()) }

    // ViewModels
    viewModel { MainViewModel(repository = get()) }
    viewModel { TestViewModel(repository = get()) }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule)
        modules(commonModule)
    }
}