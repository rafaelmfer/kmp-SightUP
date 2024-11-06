package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun UnitTwoScreen(btn: (Boolean) -> Unit, unitTwo: (List<String>) -> Unit) {
    val selectedConditions = remember { mutableStateListOf<String>() }

    var strain: Boolean by remember { mutableStateOf(false) }
    var changes: Boolean by remember { mutableStateOf(false) }
    var recommendations: Boolean by remember { mutableStateOf(false) }
    var history: Boolean by remember { mutableStateOf(false) }


    fun toggleCondition(condition: String, isSelected: Boolean) {
        if (isSelected) {
            selectedConditions.add(condition)
        } else {
            selectedConditions.remove(condition)
        }
        println("Selected conditions: $selectedConditions")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            "Which unit do you prefer?",
            style = SightUPTheme.textStyles.h5
        )

        Spacer(Modifier.height(8.dp))
        Text(
            "Understanding your primary goal allows us to tailor recommendations and features that specifically address your eye health needs.",
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

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Yellow)
                .clickable {
                    strain = !strain
                    toggleCondition("Strain", strain)
                }
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                )
                .background(if (strain) SightUPTheme.sightUPColors.info_100 else Color.White)
                .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Improve eye strain",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.body
            )
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Yellow)
                .clickable {
                    changes = !changes
                    toggleCondition("Changes", changes)
                }
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                )
                .background(if (changes) SightUPTheme.sightUPColors.info_100 else Color.White)
                .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Track vision changes over time",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.body
            )
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Yellow)
                .clickable {
                    recommendations = !recommendations
                    toggleCondition("recommendations", recommendations)
                }
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                )
                .background(if (recommendations) SightUPTheme.sightUPColors.info_100 else Color.White)
                .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Get personalized eye care recommendations",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.body
            )
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Yellow)
                .clickable {
                    history = !history
                    toggleCondition("History", history)
                }
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                )
                .background(if (history) SightUPTheme.sightUPColors.info_100 else Color.White)
                .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Maintain vision history record",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.body
            )
        }

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
                    unitTwo(selectedConditions)
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}