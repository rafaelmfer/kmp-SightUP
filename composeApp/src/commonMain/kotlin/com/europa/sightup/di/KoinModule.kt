package com.europa.sightup.di

import com.europa.sightup.BuildConfigKMP
import com.europa.sightup.data.network.NetworkClient
import com.europa.sightup.data.remote.api.JsonPlaceholderApiService
import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.remote.api.createSightUpApiService
import com.europa.sightup.data.repository.JsonPlaceholderRepository
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.platformspecific.getPlatform
import com.europa.sightup.presentation.MainViewModel
import com.europa.sightup.presentation.screens.exercise.ExerciseViewModel
import com.europa.sightup.presentation.screens.onboarding.LoginViewModel
import com.europa.sightup.presentation.screens.test.active.ActiveTestViewModel
import com.europa.sightup.presentation.screens.test.result.TestResultViewModel
import com.europa.sightup.presentation.screens.test.root.TestViewModel
import com.europa.sightup.presentation.screens.test.tutorial.TutorialTestViewModel
import com.europa.sightup.utils.ANDROID
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
            baseUrl = BuildConfigKMP.BASE_URL,
            httpClient = get()
        ).create()
    }
    single<SightUpApiService> {
        NetworkClient.provideKtorfit(
            baseUrl = if (getPlatform().name == ANDROID) BuildConfigKMP.BASE_URL_BACKEND_ANDROID_EMU else BuildConfigKMP.BASE_URL_BACKEND_IOS_EMU,
            httpClient = get()
        ).createSightUpApiService()
    }

    // Repositories
    single<JsonPlaceholderRepository> { JsonPlaceholderRepository(api = get()) }
    single<SightUpRepository> { SightUpRepository(api = get(), kVaultStorage = get()) }

    // ViewModels
    viewModel { MainViewModel(repository = get()) }
    viewModel { LoginViewModel(repository = get()) }
    viewModel { TestViewModel(repository = get()) }
    viewModel { ExerciseViewModel(repository = get()) }
    viewModel { TutorialTestViewModel() }
    viewModel { ActiveTestViewModel() }
    viewModel { TestResultViewModel( repository = get()) }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule)
        modules(commonModule)
    }
}