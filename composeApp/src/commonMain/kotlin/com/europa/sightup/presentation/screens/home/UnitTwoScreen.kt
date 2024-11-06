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
fun UnitTwoScreen(vUnitTwo:String, btn: (Boolean) -> Unit, unitTwo: (String)->Unit){
    var selectedUnit by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        if (vUnitTwo != "") {
            selectedUnit = vUnitTwo
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ){
        Spacer(Modifier.height(32.dp))
        Text("Which unit do you prefer?",
            style = SightUPTheme.textStyles.h5)

        Spacer(Modifier.height(8.dp))
        Text("Understanding your primary goal allows us to tailor recommendations and features that specifically address your eye health needs.",
            style = SightUPTheme.textStyles.body)

        Text("*You can update int the account anytime.",
            style = SightUPTheme.textStyles.body2)

        Spacer(Modifier.height(32.dp))
        Text("Measurement",
            style = SightUPTheme.textStyles.body2)

        SDSButton(
            text = "Improve eye strain",
            onClick = { selectedUnit = "improve" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "improve") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Track vision changes over time",
            onClick = { selectedUnit = "track" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "track") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Get personalized eye care recommendations",
            onClick = { selectedUnit = "personalized" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "personalized") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Maintain a vision history record",
            onClick = { selectedUnit = "maintain" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "maintain") Color.Blue else Color.Transparent
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
                "Next(4/5)",
                {
                    btn(false)
                    unitTwo(selectedUnit.toString())
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}