package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun BirthDayScreen(birthday: String, btn: (Boolean) -> Unit, birth: (String) -> Unit) {
    var birthdayInput by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        if (birthday != "") {
            birthdayInput = birthday
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            "When is your birthday?",
            style = SightUPTheme.textStyles.h5
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Your birthday helps us provide age-specific insights for better vision care.",
            style = SightUPTheme.textStyles.body
        )

        Spacer(Modifier.height(32.dp))

        SDSInput(
            modifier = Modifier.fillMaxWidth(),
            value = birthdayInput,
            onValueChange = { newValue ->
                birthdayInput = newValue
            },
            label = "Birthday",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            fullWidth = true
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
                "Next(1/5)",
                {
                    btn(false)
                    birth(birthdayInput)
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}