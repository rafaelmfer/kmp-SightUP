package com.europa.sightup.presentation.navigation

import com.europa.sightup.data.remote.response.TestResponse
import kotlinx.serialization.Serializable

/**
All screen will be defined in here.
All screens will be at the same level, so make sure to give them a unique name.
 **/

// Onboarding Routes
sealed interface OnboardingScreens {
    @Serializable
    data object OnboardingInit : OnboardingScreens

    @Serializable
    data object Disclaimer : OnboardingScreens

    @Serializable
    data object Tutorial : OnboardingScreens

}

// Home Routes
@Serializable
object Home

// Exercise Routes
sealed interface ExerciseScreens {
    @Serializable
    data object ExerciseInit : ExerciseScreens

    @Serializable
    data object ExerciseRoot : ExerciseScreens

    @Serializable
    object Prescription

    @Serializable
    object ExerciseDetail
}

// Test Routes
sealed interface TestScreens {
    @Serializable
    data object TestInit : TestScreens

    @Serializable
    data object TestRoot : TestScreens

    @Serializable
    data class TestIndividual(val testResponse: String? = TestResponse.toString()) : TestScreens {
        override fun toString(): String {
            return TestIndividual::class.simpleName.toString()
        }
    }

    @Serializable
    data class TestTutorial(val testResponse: String? = TestResponse.toString()) : TestScreens {
        override fun toString(): String {
            return TestTutorial::class.simpleName.toString()
        }
    }
}

// Prescriptions Routes
sealed interface PrescriptionsScreens {
    @Serializable
    data object PrescriptionsInit : PrescriptionsScreens

    @Serializable
    data object PrescriptionsRoot : PrescriptionsScreens
}

// Account Routes
@Serializable
sealed interface AccountScreens {
    @Serializable
    data object AccountInit : AccountScreens

    @Serializable
    data object AccountRoot : AccountScreens
}


/** After a new route is added it has to be called from the NavigationGraph.kt file **/