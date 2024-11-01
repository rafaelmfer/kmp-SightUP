package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SliderWithPoints(
    showScreen: (Boolean) -> Unit,
    btn: (String) -> Unit,
    jsonEye1: String,
    jsonEye2: String,
    jsonEye3: String,
    jsonEye4: String,
    jsonEye5: String,

    sliderViewModel: SliderViewModel = viewModel(),
) {
    val buttonLabels = listOf("btn1", "btn2", "btn3", "btn4", "btn5")
    val activeBtn by sliderViewModel.activeButton.collectAsState()
    var activeAnimation: String by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (middleBox: ConstrainedLayoutReference, topColumn, bottomColumn) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .constrainAs(topColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        ) {
            Text(
                "Title",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.h5
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "SubTitle",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.button
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(middleBox) {
                    top.linkTo(topColumn.bottom)
                    bottom.linkTo(bottomColumn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        ) {

            activeAnimation = when (activeBtn) {
                    "btn1" -> jsonEye1
                    "btn2" -> jsonEye2
                    "btn3" -> jsonEye3
                    "btn4" -> jsonEye4
                    "btn5" -> jsonEye5
                    else -> ""
                }

            val composition by rememberLottieComposition(activeAnimation) {
                LottieCompositionSpec.JsonString(
                    //Res.readBytes("files/animation_delete_me.json").decodeToString()
                    Res.readBytes(activeAnimation).decodeToString()
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 33.dp)
                .constrainAs(bottomColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color(0xFFD3E3EE))
                        .align(Alignment.Center)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    buttonLabels.forEach { label ->
                        Button(
                            onClick = {
                                sliderViewModel.setActiveButton(label)
                            },
                            modifier = Modifier.size(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeBtn == label) Color(0xFF235E86) else Color(0xFFD3E3EE)
                            )
                        ) { }
                    }
                }
            }

            Spacer(modifier = Modifier.height(23.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Very Poor",
                    style = SightUPTheme.textStyles.caption,
                    color = if (activeBtn == "btn1") Color(0xFF235E86) else SightUPTheme.sightUPColors.text_tertiary
                )
                Text(
                    "Poor",
                    style = SightUPTheme.textStyles.caption,
                    color = if (activeBtn == "btn2") Color(0xFF235E86) else SightUPTheme.sightUPColors.text_tertiary
                )
                Text(
                    "Moderate",
                    style = SightUPTheme.textStyles.caption,
                    color = if (activeBtn == "btn3") Color(0xFF235E86) else SightUPTheme.sightUPColors.text_tertiary
                )
                Text(
                    "Good",
                    style = SightUPTheme.textStyles.caption,
                    color = if (activeBtn == "btn4") Color(0xFF235E86) else SightUPTheme.sightUPColors.text_tertiary
                )
                Text(
                    "Very Good",
                    style = SightUPTheme.textStyles.caption,
                    color = if (activeBtn == "btn5") Color(0xFF235E86) else SightUPTheme.sightUPColors.text_tertiary
                )
            }

            Spacer(modifier = Modifier.height(99.dp))

            SDSButton(
                "Next(1/4)",
                onClick = {
                    showScreen(true)
                    btn("primeiro")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

