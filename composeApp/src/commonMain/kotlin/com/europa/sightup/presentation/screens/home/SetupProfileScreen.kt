package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.components.StepProgressBar
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import multiplatform.network.cmptoast.showToast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupProfileScreen() {
    var birthdayScreen by remember { mutableStateOf(true) }
    var genderScreen: Boolean by remember { mutableStateOf(false) }
    var unitScreen: Boolean by remember { mutableStateOf(false) }
    var unitTwoScreen: Boolean by remember { mutableStateOf(false) }
    var oftenScreen: Boolean by remember { mutableStateOf(false) }
    var currentStep: Int by remember { mutableStateOf(1) }

    var birthday: String by remember { mutableStateOf("") }
    var gender : String by remember { mutableStateOf("") }
    var unit : String by remember { mutableStateOf("")}
    var unitTwo: String by remember { mutableStateOf("") }
    var often: String by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },
    )
    var bottomSheetVisibility by remember { mutableStateOf(BottomSheetEnum.SHOW) }

    SDSBottomSheet(
        isDismissible = true,
        expanded = bottomSheetVisibility,
        onExpandedChange = {
            bottomSheetVisibility = it
        },
        sheetState = sheetState,
        fullHeight = true,
        title = null,
        iconRightVisible = true,
        sheetContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                SDSTopBar(
                    modifier = Modifier ,
                    title = "SetupProfile",
                    iconLeftVisible = if (birthdayScreen) false else true,
                    onLeftButtonClick = {
                        if (genderScreen) {
                            genderScreen = false
                            birthdayScreen = true
                            currentStep = 1
                        } else if (unitScreen) {
                            unitScreen = false
                            genderScreen = true
                            currentStep = 2
                        } else if (unitTwoScreen) {
                            unitTwoScreen = false
                            unitScreen = true
                            currentStep = 3
                        } else if (oftenScreen) {
                            oftenScreen = false
                            unitTwoScreen = true
                            currentStep = 4
                        }
                    },
                    iconRightVisible = true,
                    onRightButtonClick = {
                        showToast("close clicked")
                    }
                )

                Spacer(Modifier .height(20.dp))

                StepProgressBar(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    5,
                    currentStep
                )

                when {
                    birthdayScreen -> {
                        BirthDayScreen(
                            birthday,
                            btn = { it ->
                                birthdayScreen = it
                                genderScreen = true
                                currentStep = 2
                            },
                            birth = { it ->
                                birthday = it
                            }

                        )
                    }

                    genderScreen -> {
                        GenderScreen(
                            gender,
                            btn = { it ->
                                genderScreen = it
                                unitScreen = true
                                currentStep = 3
                            },
                            gender = { it->
                                gender = it
                            }
                        )
                    }

                    unitScreen -> {
                        UnitScreen(
                            unit,
                            btn = { it ->
                                unitScreen = it
                                unitTwoScreen = true
                                currentStep = 4
                            },
                            unit = { it ->
                                unit = it
                            }
                        )
                    }

                    unitTwoScreen -> {
                        UnitTwoScreen(
                            unitTwo,
                            btn = { it ->
                                unitTwoScreen = it
                                oftenScreen = true
                                currentStep = 5
                            },
                            unitTwo = { it ->
                                unitTwo = it
                            }
                        )
                    }

                    oftenScreen -> {
                        OftenScreenn(
                            often,
                            btn = { it ->
                                unitTwoScreen = it
                                currentStep = 5
                            },
                            complete = { it->
                                often = it
                                showToast("$birthday $gender $unit $unitTwo $often")
                            }
                        )
                    }
                }
            }
        }
    )
}

