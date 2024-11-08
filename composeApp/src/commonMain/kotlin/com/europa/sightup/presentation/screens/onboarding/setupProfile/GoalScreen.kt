package com.europa.sightup.presentation.screens.onboarding.setupProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.europa.sightup.presentation.screens.onboarding.WelcomeViewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun GoalScreen(
    viewModel: WelcomeViewModel,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    val selectedConditions = remember { mutableStateListOf<String>() }

    var strain by remember { mutableStateOf(false) }
    var changes by remember { mutableStateOf(false) }
    var recommendations by remember { mutableStateOf(false) }
    var history by remember { mutableStateOf(false) }


    fun toggleCondition(condition: String, isSelected: Boolean) {
        if (isSelected && condition !in selectedConditions) {
            selectedConditions.add(condition)
        } else if (!isSelected && condition in selectedConditions) {
            selectedConditions.remove(condition)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            "What’s your main goal?",
            style = SightUPTheme.textStyles.h5
        )

        Spacer(Modifier.height(8.dp))
        Text(
            "Understanding your primary goal allows us to tailor recommendations and features that specifically address your eye health needs.",
            style = SightUPTheme.textStyles.body
        )

        Spacer(Modifier.height(8.dp))
        Text(
            "*You can update int the account anytime.",
            style = SightUPTheme.textStyles.body2
        )

        Spacer(Modifier.height(32.dp))

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
                    toggleCondition("Recommendations", recommendations)
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
        ) {
            SDSButton(
                text = "Skip",
                onClick = onClickLeft,
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.TEXT,
            )
            Spacer(Modifier.width(SightUPTheme.spacing.spacing_base))
            SDSButton(
                text = "Next (4/5)",
                onClick = {
                    viewModel.updateProfileData(goal = selectedConditions.toList())
                    onClickRight()
                },
                modifier = Modifier
                    .weight(1f),
                buttonStyle = ButtonStyle.PRIMARY
            )
        }
    }
}