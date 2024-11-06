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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun OftenScreenn(often: String, btn: (Boolean) -> Unit, complete: (String) -> Unit) {
    var selectedUnit by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        if (often != "") {
            selectedUnit = often
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            "How often do you want to do eye exercise?",
            style = SightUPTheme.textStyles.h5
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Frequency helps SightUp creates a customized schedule and reminders, ensuring you stay consistent and effectively improve your eye health.",
            style = SightUPTheme.textStyles.body
        )

        Text(
            "*You can update int the account anytime.",
            style = SightUPTheme.textStyles.body2
        )

        Spacer(Modifier.height(32.dp))

        Text(
            "Measurement",
            style = SightUPTheme.textStyles.body2
        )

        SDSButton(
            text = "Once a day",
            onClick = { selectedUnit = "onceDay" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "onceDay") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "2 - 3 timer per day",
            onClick = { selectedUnit = "threeTime" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "threeTime") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "4 - 6 times per day",
            onClick = { selectedUnit = "sixTime" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "siTime") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Nothing",
            onClick = { selectedUnit = "nothing" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "nothing") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "As many as I need",
            onClick = { selectedUnit = "many" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "many") Color.Blue else Color.Transparent
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
                "Next(5/5)",
                {
                    btn(false)
                    complete(selectedUnit.toString())
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}