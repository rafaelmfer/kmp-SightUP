package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSButtonArrow
import com.europa.sightup.presentation.designsystem.components.SDSCardAssessment
import com.europa.sightup.presentation.navigation.HomeExample
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.SightUPSpacing
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.guide_book

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val name = "Linda"

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(SightUPTheme.sightUPColors.background_light),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GreetingWithIcons(name)
        NextTestCard(
            nameOfTest = "Vision Acuity Test",
            testDate = "Oct 04, 2024",
            numberOfDays = 9,
            onClickClose = {
                showToast(
                    message = "Close",
                    bottomPadding = 40
                )
            },
            onClickEdit = {
                showToast(
                    message = "Edit",
                    bottomPadding = 40
                )
            }
        )
        AssessmentList(navController)
        EyeWellnessTips()
    }
}

@Composable
private fun GreetingWithIcons(name: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SightUPTheme.spacing.spacing_side_margin)
    ) {
        val (greetingText, todayIcon, calendarIcon) = createRefs()

        Text(
            text = "Hello, $name",
            style = SightUPTheme.textStyles.h3,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier.constrainAs(greetingText) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(todayIcon.start, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        Icon(
            imageVector = Icons.Default.Event,
            contentDescription = "Today",
            modifier = Modifier
                .size(24.dp)
                .constrainAs(todayIcon) {
                    top.linkTo(greetingText.top)
                    bottom.linkTo(greetingText.bottom)
                    start.linkTo(greetingText.end, margin = 8.dp)
                }
        )

        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Calendar",
            modifier = Modifier
                .size(24.dp)
                .constrainAs(calendarIcon) {
                    top.linkTo(todayIcon.top)
                    bottom.linkTo(todayIcon.bottom)
                    start.linkTo(todayIcon.end, margin = 8.dp)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun NextTestCard(
    nameOfTest: String = "Vision Acuity Test",
    testDate: String = "Oct 04, 2024",
    numberOfDays: Int = 9,
    onClickClose: () -> Unit = {},
    onClickEdit: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            .background(SightUPTheme.sightUPColors.background_default),
        shape = SightUPTheme.shapes.small,
        color = SightUPTheme.sightUPColors.neutral_0,
        border = BorderStroke(
            width = SightUPBorder.Width.sm,
            color = SightUPTheme.sightUPColors.info_100
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (title, closeIcon, dateText, editIcon, daysLeftText) = createRefs()

            Text(
                text = "Next: $nameOfTest",
                style = SightUPTheme.textStyles.body,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = SightUPSpacing.default.spacing_sm)
                    start.linkTo(parent.start, margin = SightUPSpacing.default.spacing_base)
                }
            )
            IconButton(
                onClick = {
                    onClickClose()
                },
                modifier = Modifier.constrainAs(closeIcon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.close),
                    contentDescription = "Close",
                    tint = Color.Gray,
                    modifier = Modifier
                )
            }
            Row(
                modifier = Modifier
                    .clickable {
                        onClickEdit()
                    }
                    .constrainAs(dateText) {
                        top.linkTo(title.bottom, margin = SightUPSpacing.default.spacing_xs)
                        start.linkTo(parent.start, margin = SightUPSpacing.default.spacing_base)
                        bottom.linkTo(parent.bottom, margin = SightUPSpacing.default.spacing_sm)
                        verticalBias = ONE_FLOAT
                    }
            ) {
                Text(
                    text = testDate,
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(SightUPTheme.spacing.spacing_2xs))
                Icon(
                    painter = painterResource(Res.drawable.guide_book),
                    contentDescription = "Edit",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("In ")
                    withStyle(
                        style = SpanStyle(
                            color = SightUPTheme.sightUPColors.primary_600,
                            fontSize = SightUPTheme.textStyles.large.fontSize,
                            fontWeight = SightUPTheme.textStyles.large.fontWeight
                        )
                    ) {
                        append(numberOfDays.toString())
                    }
                    append(" days")
                },
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.constrainAs(daysLeftText) {
                    top.linkTo(dateText.top)
                    bottom.linkTo(dateText.bottom)
                    start.linkTo(editIcon.end, margin = SightUPSpacing.default.spacing_lg)
                    end.linkTo(parent.end, margin = SightUPSpacing.default.spacing_base)
                    horizontalBias = ONE_FLOAT
                }
            )
        }
    }
}

@Composable
private fun AssessmentList(navController: NavController? = null) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        item {
            SDSCardAssessment(
                title = "Daily Check-In",
                hour = "9:00 am",
                btnRound = true,
                exerciseDuration = 0,
                subtitle = "Log your eye condition",
                topBar = false,
                bottomBar = true,
                eyeConditions = listOf(),
                onClickCard = {
                    navController?.navigate(HomeExample)
                },
                modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            )
        }
        items(2) {
            SDSCardAssessment(
                title = "Vision Acuity Test",
                hour = "10:00 AM",
                btnRound = true,
                exerciseDuration = 2,
                subtitle = "Take the test",
                topBar = true,
                bottomBar = it != 1,
                eyeConditions = listOf("Eye Strain", "Red Eyes"),
                onClickCard = {
                    showToast(
                        message = "Vision Acuity Test",
                        bottomPadding = 40
                    )
                },
                modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            )
        }
    }

}

@Composable
private fun HorizontalButtonList(buttons: List<String>, onClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = SightUPTheme.spacing.spacing_xs),
        contentPadding = PaddingValues(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        items(buttons) { buttonText ->
            SDSButtonArrow(
                text = buttonText,
                onClick = { onClick(buttonText) },
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun EyeWellnessSection(title: String, buttons: List<String>, onClick: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm)
    ) {
        Text(
            text = title,
            style = SightUPTheme.textStyles.subtitle,
            modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
        )
        HorizontalButtonList(
            buttons = buttons,
            onClick = onClick
        )
    }
}

@Composable
private fun EyeWellnessTipsTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Eye Wellness Tips",
            style = SightUPTheme.textStyles.h5,
            modifier = Modifier.padding(start = SightUPTheme.spacing.spacing_side_margin)
        )
        IconButton(
            onClick = {
                showToast(
                    "Info",
                    bottomPadding = 40
                )
            },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info"
            )
        }
    }
}

@Composable
private fun EyeWellnessTips() {
    val sections = listOf(
        "Dry eyes" to listOf("Apply eye drops", "Prescription medicines"),
        "Pink eyes" to listOf("Wash your hands", "Clean your eyes often"),
        "Blepharitis" to listOf("Apply eye drops", "Medicines", "Treat health problems")
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        item {
            EyeWellnessTipsTitle()
        }
        items(sections) { (title, buttons) ->
            EyeWellnessSection(
                title = title,
                buttons = buttons,
                onClick = { action -> println("Clicked on: $action") }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}