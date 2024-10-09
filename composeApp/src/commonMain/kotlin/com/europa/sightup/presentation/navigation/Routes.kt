package com.europa.sightup.presentation.navigation

import kotlinx.serialization.Serializable

/**
    All screen will be defined in here.
    All screens will be at the same level, so make sure to give them a unique name.
**/
// Home Routes
@Serializable
object Home

@Serializable
object HomeExample

// Exercise Routes
@Serializable
object Exercise

@Serializable
object ExerciseDetail

// Test Routes
@Serializable
object Test

// Record Routes
@Serializable
object Record

// Account Routes
@Serializable
object Account

// Onboarding Routes
sealed interface OnboardingScreens {

    @Serializable
    data object OnboardingInit : OnboardingScreens

    @Serializable
    data object Disclaimer : OnboardingScreens

}


/** After a new route is added it has to be called from the NavigationGraph.kt file **/