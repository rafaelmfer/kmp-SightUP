package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SliderViewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles


@Composable
fun ConditionBotheringScreen(
    title: String = "What condition(s) is bothering you Today",
    subtitle: String = "Select all that applies",
    btn: (String) -> Unit,
    showScreen: (Boolean) -> Unit,
    viewModel: SliderViewModel = viewModel(),
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        var btn1 by remember { mutableStateOf(false) }
        var btn2 by remember { mutableStateOf(false) }
        var btn3 by remember { mutableStateOf(false) }
        var btn4 by remember { mutableStateOf(false) }
        var btn5 by remember { mutableStateOf(false) }

        val selectedConditions = remember { mutableStateListOf<String>() }

        fun toggleCondition(condition: String, isSelected: Boolean) {
            if (isSelected) {
                selectedConditions.add(condition)
            } else {
                selectedConditions.remove(condition)
            }
            println("Selected conditions: $selectedConditions")
        }

        val (topColumn, middleColumn, bottomColumn) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        ) {
            Text(
                title,
                modifier = Modifier.fillMaxWidth(),
                style = SightUPTheme.textStyles.h5
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                subtitle,
                modifier = Modifier.fillMaxWidth(),
                style = SightUPTheme.textStyles.body
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(middleColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topColumn.bottom)
                    bottom.linkTo(bottomColumn.top)
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            Column(modifier = Modifier.border(1.dp, Color.Transparent, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .clickable {
                    btn1 = !btn1
                    toggleCondition("EyeStrain", btn1)
                }
                .background(if (btn1) Color.Yellow else Color.White)
                .fillMaxWidth()) {
                Text(
                    "EyeStrain",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Tired or overworked eyes",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body2,
                    textAlign = TextAlign.Center
                )
            }

            Column(modifier = Modifier.border(1.dp, Color.Transparent, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .clickable {
                    btn2 = !btn2
                    toggleCondition("Dry eyes", btn2)
                }
                .background(if (btn2) Color.Yellow else Color.White)
                .fillMaxWidth()) {
                Text(
                    "Dry eyes",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "lack of moisture",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body2,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.border(1.dp, Color.Transparent, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    btn3 = !btn3
                    toggleCondition("Red Eyes", btn3)
                }
                .background(if (btn3) Color.Yellow else Color.White)
                .fillMaxWidth()) {
                Text(
                    "Red Eyes",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Bloodshot or irritated",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body2,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.border(1.dp, Color.Transparent, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    btn4 = !btn4
                    toggleCondition("Irritated Eyes", btn4)
                }
                .background(if (btn4) Color.Yellow else Color.White)
                .fillMaxWidth()) {
                Text(
                    "Irritated Eyes",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Unconfortable, inflamed sensation",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body2,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.border(1.dp, Color.Transparent, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    btn5 = !btn5
                    toggleCondition("Itchy Eyes", btn5)
                }
                .background(if (btn5) Color.Yellow else Color.White)
                .fillMaxWidth()) {
                Text(
                    "Itchy Eyes",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Urge to rub",
                    modifier = Modifier.fillMaxWidth(),
                    style = SightUPTheme.textStyles.body2,
                    textAlign = TextAlign.Center
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            SDSButton(
                "Next(2/3)",
                onClick = {
                    btn("Segundo")
                    viewModel.updateSelectedConditions(selectedConditions)
                    showScreen(false)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

