package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun GenderScreen(vGender: String, btn: (Boolean) -> Unit, gender: (String) -> Unit) {
    var selectedGender by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        if (vGender != "") {
            selectedGender = vGender
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
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

        SDSButton(
            text = "Male",
            onClick = { selectedGender = "Male" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedGender == "Male") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Female",
            onClick = { selectedGender = "Female" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedGender == "Female") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Intersex",
            onClick = { selectedGender = "Intersex" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedGender == "Intersex") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Prefer not to disclose",
            onClick = { selectedGender = "Prefer not to disclose" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedGender == "Prefer not to disclose") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(40.dp))

        Row {
            SDSButton(
                "Skip",
                {},
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.TEXT,
            )

            Spacer(Modifier.width(20.dp))

            SDSButton(
                "Next(2/5)",
                {
                    btn(false)
                    gender(selectedGender.toString())
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}