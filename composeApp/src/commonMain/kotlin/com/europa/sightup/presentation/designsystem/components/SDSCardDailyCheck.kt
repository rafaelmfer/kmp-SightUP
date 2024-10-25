package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.clock

@Composable
fun SDSCardDailyCheck(
    btnRound: Boolean = false,
    clickCard: () -> Unit = { println("funcionando") },
    hour: String = "10:00 am",
    exerciseDuration: String = "3 min",
    title: String = "title",
    subtitle: String = "subtitle",
    topBar: Boolean = true,
    bottomBar: Boolean = true,
    eyeCondition: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CardDailyCheck(
            btnRound,
            onClickCard = clickCard,
            hour,
            exerciseDuration,
            title,
            subtitle,
            topBar,
            bottomBar,
            eyeCondition
        )
    }
}


@Composable
fun CardDailyCheck(
    btnRound: Boolean = false,
    onClickCard: () -> Unit,
    hour: String,
    exerciseDuration: String,
    title: String,
    subtitle: String,
    topBar: Boolean,
    bottomBar: Boolean,
    eyeCondition: Boolean
) {
    var btnActive by remember { mutableStateOf(btnRound) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (clIndicators, clCard) = createRefs()

        ConstraintLayout(
            modifier = Modifier.constrainAs(clIndicators) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(clCard.start)
                height = Dimension.fillToConstraints
            }
        ) {
            val linha1 = createRef()
            val mbtCircle = createRef()
            val linha2 = createRef()

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .constrainAs(linha1) {
                        top.linkTo(parent.top)
                        bottom.linkTo(mbtCircle.top, margin = 16.dp)
                        start.linkTo(mbtCircle.start)
                        end.linkTo(mbtCircle.end)
                        height = Dimension.fillToConstraints
                    }
                    .background(if (topBar) Color(0xFFAAB4BD) else Color.White)
            )

            Button(
                onClick = {
                    btnActive = !btnActive
                },
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(mbtCircle) {
                        top.linkTo(linha1.bottom)
                        bottom.linkTo(linha2.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (btnActive) Color(0xFFDAF1FC) else Color.White
                ),
                border = BorderStroke(2.dp, if (btnActive) Color(0xFF2C76A8) else Color(0xFFAAB4BD))

            ) {

            }

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .constrainAs(linha2) {
                        top.linkTo(mbtCircle.bottom, margin = 16.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(mbtCircle.start)
                        end.linkTo(mbtCircle.end)
                        height = Dimension.fillToConstraints
                    }
                    .background(if (bottomBar) Color(0xFFAAB4BD) else Color.White)
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .constrainAs(clCard) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(clIndicators.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(start = 16.dp)
                .background(if (btnActive) Color(0xFFDAF1FC) else Color.White, shape = RoundedCornerShape(16.dp))
                .border(
                    2.dp,
                    if (btnActive) Color(0xFFDAF1FC) else Color(0xFFAAB4BD),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(onClick = onClickCard),
            ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (hour.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            hour,
                            color = Color.Black,
                            fontSize = 12.sp,
                            style = SightUPTheme.textStyles.caption
                        )
                        Image(
                            painter = painterResource(Res.drawable.clock),
                            contentDescription = "Descrição da imagem",
                            modifier = Modifier
                                .width(15.dp)
                                .height(15.dp)
                                .padding(start = 3.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        title,
                        color = Color.Black,
                        fontSize = 18.sp,
                        style = SightUPTheme.textStyles.subtitle
                    )

                    if (exerciseDuration.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .border(1.dp, Color.Black, shape = RoundedCornerShape(23.dp))
                                .background(Color.White, shape = RoundedCornerShape(23.dp))
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.clock),
                                contentDescription = "Descrição da imagem",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                "1 min",
                                style = SightUPTheme.textStyles.caption,
                                modifier = Modifier.padding(end = 3.dp)
                            )
                        }
                    }
                }

                if (subtitle.isNotEmpty()) {
                    Text(
                        subtitle,
                        color = Color(0xFF77818A),
                        style = SightUPTheme.textStyles.caption,
                    )
                }

                if (eyeCondition) {
                    Spacer(modifier = Modifier.size(32.dp))
                    Row {
                        Text(
                            "Eye Strain",
                            color = Color(0xFF7D4CF8),
                            style = SightUPTheme.textStyles.caption,
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    Color(0xFFEDE6FF),
                                    shape = RoundedCornerShape(23.dp)
                                )
                                .background(
                                    Color(0xFFEDE6FF),
                                    shape = RoundedCornerShape(23.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 4.dp
                                )
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            "Eye Strain",
                            color = Color(0xFFDC6A1E),
                            style = SightUPTheme.textStyles.caption,
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    Color(0xFFF9E4D6),
                                    shape = RoundedCornerShape(23.dp)
                                )
                                .background(
                                    Color(0xFFF9E4D6),
                                    shape = RoundedCornerShape(23.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 4.dp
                                )
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            "Eye Strain",
                            color = Color(0xFFDF263C),
                            style = SightUPTheme.textStyles.caption,
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    Color(0xFFFCDFE2),
                                    shape = RoundedCornerShape(23.dp)
                                )
                                .background(
                                    Color(0xFFFCDFE2),
                                    shape = RoundedCornerShape(23.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 4.dp
                                )
                        )
                    }
                }
            }
        }
    }
}
