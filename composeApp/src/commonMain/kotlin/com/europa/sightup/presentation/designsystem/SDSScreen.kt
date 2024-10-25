package com.europa.sightup.presentation.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.AudioVisualizer
import com.europa.sightup.presentation.designsystem.DesignSystemSamples.Countdown
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
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing

@Composable
fun SDSSamplesScreen(navController: NavHostController) {
    val destinations = listOf(
        AudioVisualizer to AudioVisualizer::class.simpleName!!,
        SDSBottomSheet to SDSBottomSheet::class.simpleName!!,
        Countdown to Countdown::class.simpleName!!,
        TextStyles to TextStyles::class.simpleName!!,
        SDSButton to SDSButton::class.simpleName!!,
        SDSControlE to SDSControlE::class.simpleName!!,
        SDSInput to SDSInput::class.simpleName!!,
        SDSTopBar to SDSTopBar::class.simpleName!!,
        SDSConditions to SDSConditions::class.simpleName!!,
        SDSTimer to SDSTimer::class.simpleName!!,
        SDSSwitch to SDSSwitch::class.simpleName!!,
        SDSBadgeTime to SDSBadgeTime::class.simpleName!!,
        SDSFilterChip to SDSFilterChip::class.simpleName!!,
        SDSEyeClock to SDSEyeClock::class.simpleName!!,
        SDSExerciseCountdownScreen to SDSExerciseCountdownScreen::class.simpleName!!,
        SDSButtonArrow to SDSButtonArrow::class.simpleName!!,
        SDSCardDailyCheckScreen to SDSCardDailyCheckScreen::class.simpleName!!,
        SDSListButtonsSelectableScreen to SDSListButtonsSelectableScreen::class.simpleName!!,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SightUPTheme.spacing.spacing_base)
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = destinations, key = { it.second }) { (destination, label) ->
                val buttonModifier = Modifier
                    .padding(horizontal = SightUPTheme.spacing.spacing_base)
                    .fillMaxWidth()

                NavigationButton(
                    navController = navController,
                    destination = destination,
                    label = label,
                    modifier = buttonModifier
                )
            }
        }
    }
}

@Composable
fun NavigationButton(
    navController: NavHostController,
    destination: Any,
    label: String,
    modifier: Modifier,
) {
    SDSButton(
        onClick = {
            navController.navigate(destination)
        },
        text = label,
        modifier = modifier
    )
}
