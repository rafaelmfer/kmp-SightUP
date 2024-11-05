package com.europa.sightup.presentation.navigation

import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.TestModeEnum
import kotlinx.serialization.Serializable

@Serializable
object HomeExample

/**
 * All screen will be defined in here.
 * All screens will be at the same level, so make sure to give them a unique name.
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

@Serializable
object WelcomeScreen

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
    data class ExerciseDetails(
        val exerciseId: String,
        val exerciseName: String,
        val category: String,
        val motivation: String,
        val duration: Int,
        val imageInstruction: String,
        val video: String,
        val finishTitle: String,
        val advice: String,
    ) : ExerciseScreens

    @Serializable
    data class ExerciseCountdown(
        val exerciseId: String,
        val category: String,
        val exerciseName: String,
        val duration: Int,
        val video: String,
        val finishTitle: String,
        val advice: String,

    ) : ExerciseScreens

    @Serializable
    data class ExerciseRunning(
        val exerciseId: String,
        val category: String,
        val exerciseName: String,
        val duration: Int,
        val video: String,
        val finishTitle: String,
        val advice: String,
    ) : ExerciseScreens

    @Serializable
    data class ExerciseFinish(
        val exerciseId: String,
        val category: String,
        val exerciseName: String,
        val finishTitle: String,
        val advice: String,
    ) : ExerciseScreens

    @Serializable
    data class ExerciseEvaluationResult(
        val exerciseId: String,
        val category: String,
        val exerciseName: String,
        val answerEvaluation: String,
    ) : ExerciseScreens
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

    @Serializable
    data class TestActive(
        val testResponse: String? = TestResponse.toString(),
        val testMode: String? = TestModeEnum.Touch.displayName,
        val eyeTested: String? = "Right",
    ) : TestScreens {
        override fun toString(): String {
            return TestActive::class.simpleName.toString()
        }
    }

    @Serializable
    data class TestResult(
        val appTest: Boolean,
        val testId: String,
        val testTitle: String,
        val left: String,
        val right: String,
    ) : TestScreens
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