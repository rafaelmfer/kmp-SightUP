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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.SightUPSpacing
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.clock

@Composable
fun SDSCardAssessment(
    btnRound: Boolean = false,
    onClickCard: () -> Unit,
    hour: String,
    exerciseDuration: Int,
    title: String,
    subtitle: String,
    topBar: Boolean,
    bottomBar: Boolean,
    eyeConditions: List<String>,
    modifier: Modifier = Modifier,
) {
    var btnActive by remember { mutableStateOf(btnRound) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
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
            val (lineOne, mbtCircle, lineTwo) = createRefs()

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .constrainAs(lineOne) {
                        top.linkTo(parent.top)
                        bottom.linkTo(mbtCircle.top, margin = SightUPSpacing.default.spacing_md)
                        start.linkTo(mbtCircle.start)
                        end.linkTo(mbtCircle.end)
                        height = Dimension.fillToConstraints
                    }
                    .background(if (topBar) SightUPTheme.sightUPColors.neutral_400 else Color.White)
            )

            Button(
                onClick = {
                    btnActive = !btnActive
                },
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(mbtCircle) {
                        top.linkTo(lineOne.bottom)
                        bottom.linkTo(lineTwo.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (btnActive) SightUPTheme.sightUPColors.background_info else Color.Transparent
                ),
                border = BorderStroke(
                    width = SightUPBorder.Width.sm,
                    color = if (btnActive) SightUPTheme.sightUPColors.border_primary else SightUPTheme.sightUPColors.neutral_400
                )

            ) {

            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .constrainAs(lineTwo) {
                        top.linkTo(mbtCircle.bottom, margin = SightUPSpacing.default.spacing_md)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(mbtCircle.start)
                        end.linkTo(mbtCircle.end)
                        height = Dimension.fillToConstraints
                    }
                    .background(if (bottomBar) SightUPTheme.sightUPColors.neutral_400 else Color.White)
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(clCard) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(clIndicators.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    start = SightUPTheme.spacing.spacing_md,
                    top = SightUPTheme.spacing.spacing_xs,
                    bottom = SightUPTheme.spacing.spacing_xs,
                )
                .background(
                    color = SightUPTheme.sightUPColors.background_default,
                    shape = SightUPTheme.shapes.small
                )
                .border(
                    width = SightUPBorder.Width.sm,
                    color = SightUPTheme.sightUPColors.border_card,
                    shape = SightUPTheme.shapes.small
                )
                .padding(SightUPTheme.spacing.spacing_base)
                .clickable(onClick = onClickCard),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
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
                        text = title,
                        color = SightUPTheme.sightUPColors.text_primary,
                        style = SightUPTheme.textStyles.subtitle
                    )
                    if (exerciseDuration > 0) {
                        SDSBadgeTime(exerciseDuration)
                    }
                }

                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        color = SightUPTheme.sightUPColors.text_tertiary,
                        style = SightUPTheme.textStyles.caption,
                    )
                }

                if (eyeConditions.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(eyeConditions) { condition ->
                            SDSConditions(
                                type = SDSConditionsEnum.fromString(condition),
                            )
                            if (condition != eyeConditions.last()) {
                                Spacer(Modifier.width(SightUPTheme.spacing.spacing_xs))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SDSCardDailyCheckScreen(
    btnRound: Boolean = false,
    clickCard: () -> Unit = { println("funcionando") },
    hour: String = "10:00 am",
    exerciseDuration: Int = 0,
    title: String = "title",
    subtitle: String = "subtitle",
    topBar: Boolean = true,
    bottomBar: Boolean = true,
    eyeConditions: List<String> = listOf("EYE_STRAIN", "DRY_EYES", "RED_EYES"),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SDSCardAssessment(
            btnRound,
            onClickCard = clickCard,
            hour,
            exerciseDuration,
            title,
            subtitle,
            topBar,
            bottomBar,
            eyeConditions = eyeConditions
        )
    }
}