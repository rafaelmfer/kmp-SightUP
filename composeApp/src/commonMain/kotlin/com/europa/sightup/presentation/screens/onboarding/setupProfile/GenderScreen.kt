package com.europa.sightup.presentation.screens.onboarding.setupProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun GenderScreen(
    viewModel: WelcomeViewModel,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    var selectedGender by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            "What is your born sex?",
            style = SightUPTheme.textStyles.h5
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Knowing your gender allows us to tailor recommendations based on unique eye health needs.",
            style = SightUPTheme.textStyles.body
        )

        Text(
            "*You can update int the account anytime.",
            style = SightUPTheme.textStyles.body2
        )

        Spacer(Modifier.height(32.dp))

        val items: List<String> = listOf("Male", "Female", "Intersex", "Prefer not to disclose")

        SDSListButtonsSelectable(
            items = items,
            onSelectedChange = {
                selectedGender = it
            }
        )

        Spacer(Modifier.height(40.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
        ){
            SDSButton(
                text = "Skip",
                onClick = onClickLeft,
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.TEXT,
            )

            Spacer(Modifier.width(SightUPTheme.spacing.spacing_base))

            SDSButton(
                text = "Next (2/5)",
                onClick = {
                    viewModel.updateProfileData(gender = selectedGender)
                    onClickRight()
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}