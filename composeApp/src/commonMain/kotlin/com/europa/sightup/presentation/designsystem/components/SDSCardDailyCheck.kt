package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.SightUPSpacing
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.applyIf
import com.europa.sightup.utils.clickableWithRipple
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.check
import sightupkmpapp.composeapp.generated.resources.schedule

@Composable
fun SDSCardAssessment(
    isDone: Boolean = false,
    onClickCard: () -> Unit = {},
    time: String = "",
    exerciseDuration: Int = 0,
    title: String,
    subtitle: String = "",
    lineUp: Boolean = true,
    lineDown: Boolean = true,
    eyeConditions: List<String> = listOf(),
    modifier: Modifier = Modifier,
) {
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
                    .width(2.dp)
                    .constrainAs(lineOne) {
                        top.linkTo(parent.top)
                        bottom.linkTo(mbtCircle.top, margin = SightUPSpacing.default.spacing_md)
                        start.linkTo(mbtCircle.start)
                        end.linkTo(mbtCircle.end)
                        height = Dimension.fillToConstraints
                    }
                    .background(if (lineUp) SightUPTheme.sightUPColors.background_card else SightUPTheme.sightUPColors.background_light)
            )

            Box(
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_24)
                    .background(
                        color = if (isDone) SightUPTheme.sightUPColors.background_button else SightUPTheme.sightUPColors.background_default,
                        shape = CircleShape
                    )
                    .border(
                        width = SightUPBorder.Width.md,
                        color = if (isDone) SightUPTheme.sightUPColors.border_primary else SightUPTheme.sightUPColors.border_card,
                        shape = CircleShape
                    )
                    .constrainAs(mbtCircle) {
                        top.linkTo(lineOne.bottom)
                        bottom.linkTo(lineTwo.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(
                        painter = painterResource(Res.drawable.check),
                        contentDescription = "Done",
                        tint = Color.White,
                        modifier = Modifier.size(SightUPTheme.sizes.size_16)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .constrainAs(lineTwo) {
                        top.linkTo(mbtCircle.bottom, margin = SightUPSpacing.default.spacing_md)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(mbtCircle.start)
                        end.linkTo(mbtCircle.end)
                        height = Dimension.fillToConstraints
                    }
                    .background(if (lineDown) SightUPTheme.sightUPColors.background_card else SightUPTheme.sightUPColors.background_light)
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(clCard) {
                    top.linkTo(parent.top, margin = SightUPSpacing.default.spacing_xs)
                    bottom.linkTo(parent.bottom, margin = SightUPSpacing.default.spacing_xs)
                    start.linkTo(clIndicators.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    start = SightUPTheme.spacing.spacing_md,
                    top = SightUPTheme.spacing.spacing_xs,
                    bottom = SightUPTheme.spacing.spacing_xs,
                )
                .clip(SightUPTheme.shapes.medium)
                .background(
                    color = SightUPTheme.sightUPColors.background_default,
                    shape = SightUPTheme.shapes.medium
                )
                .border(
                    width = SightUPBorder.Width.sm,
                    color = SightUPTheme.sightUPColors.border_card,
                    shape = SightUPTheme.shapes.medium
                )
                .applyIf(isDone) {
                    alpha(0.6f)
                }
                .clickableWithRipple(enabled = !isDone, onClick = onClickCard)
                .padding(vertical = SightUPTheme.spacing.spacing_base),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (time.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_base)
                    ) {
                        if (isDone && title.equals("Daily Check-In", ignoreCase = true)) {
                            Image(
                                painter = painterResource(Res.drawable.check),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = SightUPTheme.spacing.spacing_2xs)
                                    .size(SightUPTheme.sizes.size_16)
                            )
                        }
                        Text(
                            text = time,
                            color = SightUPTheme.sightUPColors.text_primary,
                            style = SightUPTheme.textStyles.caption
                        )
                        if (!title.equals("Daily Check-In", ignoreCase = true)) {
                            Image(
                                painter = painterResource(Res.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = SightUPTheme.spacing.spacing_2xs)
                                    .size(SightUPTheme.sizes.size_16)
                            )
                        }
                    }
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_2xs))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SightUPTheme.spacing.spacing_base)
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

                if (subtitle.isNotBlank()) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_2xs))
                    Text(
                        text = subtitle,
                        color = SightUPTheme.sightUPColors.text_tertiary,
                        style = SightUPTheme.textStyles.caption,
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_base)
                    )
                }

                if (eyeConditions.isNotEmpty()) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            start = SightUPTheme.spacing.spacing_base,
                            end = SightUPTheme.spacing.spacing_base
                        ),
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
    clickCard: () -> Unit = { println("funcionando") },
    hour: String = "10:00 am",
    exerciseDuration: Int = 0,
    title: String = "title",
    subtitle: String = "subtitle",
    topBar: Boolean = true,
    bottomBar: Boolean = true,
    eyeConditions: List<String> = listOf("EYE_STRAIN", "DRY_EYES", "RED_EYES", "ITCHY_EYES", "WATERY_EYES"),
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SDSCardAssessment(
            false,
            onClickCard = clickCard,
            "",
            exerciseDuration,
            "Daily Check-In",
            subtitle,
            topBar,
            bottomBar,
            eyeConditions = listOf()
        )
        Spacer(modifier = Modifier.height(32.dp))
        SDSCardAssessment(
            true,
            onClickCard = clickCard,
            hour,
            exerciseDuration,
            "Daily Check-In",
            subtitle,
            topBar,
            bottomBar,
            eyeConditions = listOf()
        )
        Spacer(modifier = Modifier.height(32.dp))
        SDSCardAssessment(
            false,
            onClickCard = clickCard,
            hour,
            1,
            title,
            subtitle,
            topBar,
            bottomBar,
            eyeConditions = eyeConditions
        )
        Spacer(modifier = Modifier.height(32.dp))
        SDSCardAssessment(
            true,
            onClickCard = clickCard,
            hour,
            1,
            title,
            subtitle,
            topBar,
            bottomBar,
            eyeConditions = eyeConditions
        )
    }
}