package com.europa.sightup.presentation.designsystem

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.AudioVisualizer
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.Countdown
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.DesignSystemInit
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.Home
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSBadgeTime
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSButton
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSButtonArrow
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSCardDailyCheckScreen
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSConditions
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSControlE
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSExerciseCountdownScreen
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSEyeClock
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSFilterChip
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSInput
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSListButtonsSelectableScreen
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSSwitch
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSTimer
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.SDSTopBar
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.TextStyles
import com.europa.sightup.presentation.designsystem.components.SDSBadgeTimeScreen
import com.europa.sightup.presentation.designsystem.components.SDSButtonArrowScreen
import com.europa.sightup.presentation.designsystem.components.SDSCardDailyCheckScreen
import com.europa.sightup.presentation.designsystem.components.SDSConditionsScreen
import com.europa.sightup.presentation.designsystem.components.SDSEyeClockScreen
import com.europa.sightup.presentation.designsystem.components.SDSFilterChipScreen
import com.europa.sightup.presentation.designsystem.components.SDSListButtonsSelectablePreview
import com.europa.sightup.presentation.designsystem.components.SDSSwitchScreen
import com.europa.sightup.presentation.designsystem.components.SDSTimerScreen
import com.europa.sightup.presentation.designsystem.components.SDSTopBarScreen
import com.europa.sightup.presentation.designsystem.components.SdsButtonScreen
import com.europa.sightup.presentation.designsystem.screens.AudioVisualizerScreen
import com.europa.sightup.presentation.designsystem.screens.CountdownScreen
import com.europa.sightup.presentation.designsystem.screens.SDSBottomSheetScreen
import com.europa.sightup.presentation.designsystem.screens.SDSControlEScreen
import com.europa.sightup.presentation.designsystem.screens.SDSInputScreen
import com.europa.sightup.presentation.designsystem.screens.TextStylesScreen
import com.europa.sightup.presentation.screens.exercise.countdownscreen.SDSExerciseCountdownScreen
import kotlinx.serialization.Serializable

sealed interface DesignSystemSamples {

    @Serializable
    data object DesignSystemInit : DesignSystemSamples

    @Serializable
    data object Home : DesignSystemSamples

    @Serializable
    data object TextStyles : DesignSystemSamples

    @Serializable
    data object Countdown : DesignSystemSamples

    @Serializable
    data object AudioVisualizer : DesignSystemSamples

    @Serializable
    data object SDSButton : DesignSystemSamples

    @Serializable
    data object SDSBottomSheet : DesignSystemSamples

    @Serializable
    data object SDSControlE : DesignSystemSamples

    @Serializable
    data object SDSInput : DesignSystemSamples

    @Serializable
    data object SDSTopBar : DesignSystemSamples

    @Serializable
    data object SDSConditions : DesignSystemSamples

    @Serializable
    data object SDSTimer : DesignSystemSamples

    @Serializable
    data object SDSSwitch : DesignSystemSamples

    @Serializable
    data object SDSBadgeTime : DesignSystemSamples

    @Serializable
    data object SDSFilterChip : DesignSystemSamples

    @Serializable
    data object SDSEyeClock : DesignSystemSamples

    @Serializable
    data object SDSExerciseCountdownScreen : DesignSystemSamples

    @Serializable
    data object SDSButtonArrow : DesignSystemSamples

    @Serializable
    data object SDSCardDailyCheckScreen : DesignSystemSamples

    @Serializable
    data object SDSListButtonsSelectableScreen : DesignSystemSamples
}

fun NavGraphBuilder.designSystemNavGraph(navController: NavHostController) {
    navigation<DesignSystemInit>(
        startDestination = Home,
    ) {
        composable<Home> {
            SDSSamplesScreen(navController = navController)
        }
        composable<TextStyles> {
            TextStylesScreen()
        }
        composable<Countdown> {
            CountdownScreen()
        }
        composable<AudioVisualizer> {
            AudioVisualizerScreen()
        }
        composable<SDSButton> {
            SdsButtonScreen()
        }
        composable<SDSBottomSheet> {
            SDSBottomSheetScreen()
        }
        composable<SDSControlE> {
            SDSControlEScreen()
        }
        composable<SDSInput> {
            SDSInputScreen()
        }
        composable<SDSTopBar> {
            SDSTopBarScreen()
        }
        composable<SDSConditions> {
            SDSConditionsScreen()
        }
        composable<SDSTimer> {
            SDSTimerScreen()
        }
        composable<SDSSwitch> {
            SDSSwitchScreen()
        }
        composable<SDSBadgeTime> {
            SDSBadgeTimeScreen()
        }
        composable<SDSFilterChip> {
            SDSFilterChipScreen()
        }
        composable<SDSEyeClock> {
            SDSEyeClockScreen()
        }
        composable<SDSExerciseCountdownScreen> {
            SDSExerciseCountdownScreen()
        }
        composable<SDSButtonArrow> {
            SDSButtonArrowScreen()
        }
        composable<SDSCardDailyCheckScreen> {
            SDSCardDailyCheckScreen()
        }
        composable<SDSListButtonsSelectableScreen> {
            SDSListButtonsSelectablePreview()
        }
    }
}