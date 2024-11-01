package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.components.StepProgressBar
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.SliderWithPoints
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AnimationJson(animation: String) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animation).decodeToString()

        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = Compottie.IterateForever
    )
    Image(
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress },
        ),
        modifier = Modifier
            .fillMaxWidth()
            //.align(Alignment.Center)
            .background(Color.Transparent),
        contentDescription = null
    )
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainScreen() {

    var firstScreen by remember { mutableStateOf(true) }
    var secondScreen: Boolean by remember { mutableStateOf(false) }
    var causeScreen: Boolean by remember { mutableStateOf(false) }
    var iconLeftVisible: Boolean by remember { mutableStateOf(false) }
    var iconRightVisible: Boolean by remember { mutableStateOf(true) }
    var topBarTitle: String by remember { mutableStateOf("Daily Check-in") }
    var currentStep: Int by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SDSTopBar(
            modifier = Modifier,
            title = topBarTitle,
            iconLeftVisible = iconLeftVisible,
            onLeftButtonClick = {
                if(secondScreen){
                    secondScreen = false
                    iconLeftVisible = false
                    currentStep = 1
                    firstScreen = true
                }else if (causeScreen){
                    causeScreen = false
                    secondScreen = true
                    currentStep = 2
                }
            },
            iconRightVisible = iconRightVisible
        )

        StepProgressBar(
            modifier = Modifier,
            4,
            currentStep
        )

        Spacer(modifier = Modifier.height(32.dp))

        when {
            firstScreen -> {
                SliderWithPoints(
                    showScreen = { screen ->
                        if (screen) {
                            firstScreen = false
                            secondScreen = true
                        }
                    },
                    btn = { it ->
                        iconLeftVisible = true
                        currentStep = 2
                    },
                    jsonEye1 = "files/animation_delete_me.json",
                    jsonEye2 = "files/animation_delete_me.json",
                    jsonEye3 = "files/animation_delete_me.json",
                    jsonEye4 = "files/animation_delete_me.json",
                    jsonEye5 = "files/animation_delete_me.json"
                )
            }

            secondScreen -> {
                ConditionBotheringScreen(
                    btn = { it -> println(it) },
                    showScreen = {
                        currentStep = 3
                        if (secondScreen) {
                            secondScreen = false;
                            causeScreen = true;
                        }
                    }
                )
            }

            causeScreen -> {
                CauseScreen(
                    btn = { it ->
                        println(it)
                    }
                )
            }
        }
    }
}