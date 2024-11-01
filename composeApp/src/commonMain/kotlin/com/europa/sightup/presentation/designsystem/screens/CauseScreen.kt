package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SliderViewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CauseScreen(
    title: String = "What condition(s) is bothering you Today",
    subtitle: String = "Select all that applies",
    btn: (String)->Unit,
    viewModel: SliderViewModel = viewModel()
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        var smoke by remember { mutableStateOf(false) }
        var age by remember { mutableStateOf(false) }
        var distance by remember { mutableStateOf(false) }
        var badSleeping by remember { mutableStateOf(false) }
        var poorLight by remember { mutableStateOf(false) }
        var skincare by remember { mutableStateOf(false) }
        var fatigue by remember { mutableStateOf(false) }
        var costmetics by remember { mutableStateOf(false) }
        var newEnvironment by remember { mutableStateOf(false) }
        var contactLenses by remember { mutableStateOf(false) }
        var pollen by remember { mutableStateOf(false) }
        var medication by remember { mutableStateOf(false) }
        var infection by remember { mutableStateOf(false) }
        var newEyeWear by remember { mutableStateOf(false) }
        var longScreenTime by remember { mutableStateOf(false) }
        var outdated by remember { mutableStateOf(false) }
        var foreignBodie by remember { mutableStateOf(false) }

        val (topColumn, middleColumn, bottomColumn) = createRefs()

        val selectedConditions = remember { mutableStateListOf<String>() }

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

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(middleColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topColumn.bottom)
                    bottom.linkTo(bottomColumn.top)
                },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            Text(
                "Smoke",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        smoke = !smoke
                        toggleCondition("Smoke", smoke)
                    }
                    .background(if (smoke) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)

            )
            Text(
                "Age",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (age) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        age = !age
                        toggleCondition("Age", age)
                    }
            )
            Text(
                "Long distance drive",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (distance) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        distance = !distance
                        toggleCondition("long distance", distance)
                    }
            )
            Text(
                "Bad sleeping quality",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (badSleeping) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        badSleeping = !badSleeping
                        toggleCondition("bad sleeping", badSleeping)
                    }
            )
            Text(
                "Poor Lighting",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (poorLight) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        poorLight = !poorLight
                        toggleCondition("poor lighting", poorLight)
                    }
            )
            Text(
                "Skincare",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (skincare) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        skincare = !skincare
                        toggleCondition("skincare", skincare)
                    }
            )
            Text(
                "Fatigue",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (fatigue) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        fatigue = !fatigue
                        toggleCondition("fatigue", fatigue)
                    }
            )
            Text(
                "Cosmetics",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (costmetics) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        costmetics = !costmetics
                        toggleCondition("poor light", costmetics)
                    }
            )
            Text(
                "New environment",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (newEnvironment) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        newEnvironment = !newEnvironment
                        toggleCondition("new environment", newEnvironment)
                    }
            )
            Text(
                "Contact lenses",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (contactLenses) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        contactLenses = !contactLenses
                        toggleCondition("Contact lenses", contactLenses)
                    }
            )
            Text(
                "Pollen",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (pollen) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        pollen = !pollen
                        toggleCondition("pollen", pollen)
                    }
            )
            Text(
                "Medication",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (medication) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        medication = !medication
                        toggleCondition("Contact lenses", medication)
                    }
            )
            Text(
                "Infection",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (infection) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        infection = !infection
                        toggleCondition("Contact lenses", infection)
                    }
            )

            Text(
                "New eyewear",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (newEyeWear) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        newEyeWear = !newEyeWear
                        toggleCondition("new eyewear", newEyeWear)
                    }
            )

            Text(
                "Long screen time",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (longScreenTime) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        longScreenTime = !longScreenTime
                        toggleCondition("long screen time", longScreenTime)
                    }
            )

            Text(
                "Outdated eyewear",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (outdated) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        outdated = !outdated
                        toggleCondition("outdated eyewear", outdated)
                    }
            )

            Text(
                "Foreign bodies",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (foreignBodie) Color.Yellow else Color.White)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 9.dp)
                    .clickable {
                        foreignBodie = !foreignBodie
                        toggleCondition("foreign bodies", foreignBodie)
                    }
            )
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
                    println(selectedConditions)
                    println(viewModel.activeButton.value)
                    println(viewModel.selectedConditions)
                    btn("terceiro")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}