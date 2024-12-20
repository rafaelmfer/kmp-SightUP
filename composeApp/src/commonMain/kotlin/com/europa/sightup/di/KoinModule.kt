package com.europa.sightup.di

import com.europa.sightup.BuildConfigKMP
import com.europa.sightup.data.network.NetworkClient
import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.remote.api.createSightUpApiService
import com.europa.sightup.data.repository.SightUpRepository
import com.europa.sightup.presentation.screens.exercise.ExerciseViewModel
import com.europa.sightup.presentation.screens.exercise.evaluation.ExerciseEvaluationResultViewModel
import com.europa.sightup.presentation.screens.exercise.finish.ExerciseFinishScreenViewModel
import com.europa.sightup.presentation.screens.home.HomeViewModel
import com.europa.sightup.presentation.screens.onboarding.LoginViewModel
import com.europa.sightup.presentation.screens.onboarding.WelcomeViewModel
import com.europa.sightup.presentation.screens.prescription.PrescriptionMainViewModel
import com.europa.sightup.presentation.screens.prescription.history.PrescriptionHistoryViewModel
import com.europa.sightup.presentation.screens.test.active.ActiveTestViewModel
import com.europa.sightup.presentation.screens.test.result.TestResultViewModel
import com.europa.sightup.presentation.screens.test.root.TestViewModel
import com.europa.sightup.presentation.screens.test.tutorial.TutorialTestViewModel
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
    single<SightUpApiService> {
        NetworkClient.provideKtorfit(
            baseUrl = BuildConfigKMP.BASE_URL,
            httpClient = get()
        ).createSightUpApiService()
    }

    // Repositories
    single<SightUpRepository> { SightUpRepository(api = get(), kVaultStorage = get()) }

    // ViewModels
    viewModel { LoginViewModel(repository = get()) }
    viewModel { WelcomeViewModel(repository = get()) }
    viewModel { TestViewModel(repository = get()) }
    viewModel { ExerciseViewModel(repository = get()) }
    viewModel { ExerciseFinishScreenViewModel(repository = get()) }
    viewModel { ExerciseEvaluationResultViewModel(repository = get()) }
    viewModel { TutorialTestViewModel() }
    viewModel { ActiveTestViewModel() }
    viewModel { TestResultViewModel(repository = get()) }
    viewModel { PrescriptionMainViewModel(repository = get()) }
    viewModel { PrescriptionHistoryViewModel(repository = get()) }
    viewModel { HomeViewModel(repository = get()) }
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