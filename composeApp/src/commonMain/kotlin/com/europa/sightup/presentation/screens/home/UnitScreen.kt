package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun UnitScreen(vUnit:String, btn: (Boolean) -> Unit, unit: (String)->Unit){
    var selectedUnit by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        if (vUnit != "") {
            selectedUnit = vUnit
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

        Text("Knowing your gender allows us to tailor recommendations based on unique eye health needs.",
            style = SightUPTheme.textStyles.body)

        Text("*You can update int the account anytime.",
            style = SightUPTheme.textStyles.body2)

        Spacer(Modifier.height(32.dp))

        Text("Measurement",
            style = SightUPTheme.textStyles.body2)

        SDSButton(
            text = "Feet",
            onClick = { selectedUnit = "Feet" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "Feet") Color.Blue else Color.Transparent
                ),
            buttonStyle = ButtonStyle.TEXT
        )

        Spacer(Modifier.height(16.dp))

        SDSButton(
            text = "Cm",
            onClick = { selectedUnit = "Cm" },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (selectedUnit == "Cm") Color.Blue else Color.Transparent
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
                "Next(3/5)",
                {
                    btn(false)
                    unit(selectedUnit.toString())
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}