package com.europa.sightup.presentation.screens.onboarding.setupProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSListButtonsSelectable
import com.europa.sightup.presentation.screens.onboarding.WelcomeViewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun OftenScreen(
    viewModel: WelcomeViewModel,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    var selectedUnit by remember { mutableStateOf<String?>(null) } // Estado mutable
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            "How often do you want to do eye exercise?",
            style = SightUPTheme.textStyles.h5
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Frequency helps SightUp create a customized schedule and reminders, ensuring you stay consistent and effectively improve your eye health.",
            style = SightUPTheme.textStyles.body
        )

        Text(
            "*You can update it in the account anytime.",
            style = SightUPTheme.textStyles.body2
        )

        Spacer(Modifier.height(32.dp))

        val items: List<String> = listOf(
            "Once a day",
            "2 - 3 times per day",
            "4 - 6 times per day",
            "As many as I need",
            "Nothing"
        )

        SDSListButtonsSelectable(
            items = items,
            onSelectedChange = { selected ->
                selectedUnit = selected
            }
        )

        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
        ) {
            SDSButton(
                text = "Skip",
                onClick = onClickLeft,
                modifier = Modifier.weight(1f),
                buttonStyle = ButtonStyle.TEXT,
            )

            Spacer(Modifier.width(SightUPTheme.spacing.spacing_base))

            SDSButton(
                text = "Complete",
                onClick = {
                    viewModel.updateProfileData(often = selectedUnit)
                    onClickRight()
                },
                modifier = Modifier.weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}

