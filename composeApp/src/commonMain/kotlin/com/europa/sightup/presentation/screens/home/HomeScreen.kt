package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.ExpandableItem
import com.europa.sightup.presentation.designsystem.components.ExpandableListItem
import com.europa.sightup.presentation.designsystem.components.SDSCardAssessment
import com.europa.sightup.presentation.navigation.HomeExample
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.SightUPSpacing
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_right
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.excelent
import sightupkmpapp.composeapp.generated.resources.good
import sightupkmpapp.composeapp.generated.resources.guide_book
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.moderate
import sightupkmpapp.composeapp.generated.resources.poor
import sightupkmpapp.composeapp.generated.resources.very_poor

@Composable
fun IconSort(myIcon: String): Painter {
    return when (myIcon) {
        "vPoor" -> painterResource(Res.drawable.very_poor)
        "poor" -> painterResource(Res.drawable.poor)
        "good"-> painterResource(Res.drawable.good)
        "excellent" -> painterResource(Res.drawable.excelent)
        "moderate" -> painterResource(Res.drawable.moderate)
        else -> painterResource(Res.drawable.good)
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val name = "Linda"
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(SightUPTheme.sightUPColors.background_light)
            .padding(bottom = SightUPTheme.spacing.spacing_md),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GreetingWithIcons(name)

        ShowCalendar(
            listOf(
                "2024-10-07" to "vPoor",
                "2024-10-06" to "poor",
                "2024-10-05" to "good",
                "2024-10-04" to "excellent",
                "2024-10-03" to "moderate",
                "2024-10-02" to "good"
            )
        )

        NextTestCard(
            nameOfTest = "Vision Acuity Test",
            testDate = "Nov 09, 2024",
            numberOfDays = 2,
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
fun ShowCalendar(myList: List<Pair<String, String>>) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    when (today.month) {
        Month.JANUARY -> check(today.month.number == 1)
        Month.FEBRUARY -> check(today.month.number == 2)
        Month.MARCH -> check(today.month.number == 3)
        Month.APRIL -> check(today.month.number == 4)
        Month.MAY -> check(today.month.number == 5)
        Month.JUNE -> check(today.month.number == 6)
        Month.JULY -> check(today.month.number == 7)
        Month.AUGUST -> check(today.month.number == 8)
        Month.SEPTEMBER -> check(today.month.number == 9)
        Month.OCTOBER -> check(today.month.number == 10)
        Month.NOVEMBER -> check(today.month.number == 11)
        Month.DECEMBER -> check(today.month.number == 12)
        else -> Month.JANUARY
    }

    val month = today.month.toString().substring(0, 1).uppercase() + today.month.toString().substring(1, 3).lowercase()

    Column(
        modifier = Modifier.fillMaxWidth().padding(SightUPTheme.spacing.spacing_side_margin)
    ) {
        Text(
            month,
            modifier = Modifier.clip(RoundedCornerShape(15.dp))
                .background(SightUPTheme.sightUPColors.background_activate)
                .padding(horizontal = 40.dp, vertical = 3.dp),
            style = SightUPTheme.textStyles.footnote
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val dayAfter = today.plus(DatePeriod(days = 1))
            for (i in 5 downTo 1) {
                val daysBefore = today.minus(DatePeriod(days = i))
                Column(
                    modifier = Modifier.weight(1f).padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color.Transparent)
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = daysBefore.dayOfWeek.toString().substring(0, 1),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = daysBefore.dayOfMonth.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                    }

                    Spacer(Modifier.height(10.dp))
                    Image(
                        painter = IconSort(myList[i].second),
                        contentDescription = "description",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f).padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
                        .background(SightUPTheme.sightUPColors.background_button).padding(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = today.dayOfWeek.toString().substring(0, 1),
                        textAlign = TextAlign.Center,
                        color = Color.White,

                        )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = today.dayOfMonth.toString(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }

                Spacer(Modifier.height(10.dp))

                Image(
                    painter = IconSort(myList[0].second),
                    contentDescription = "description",
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color.Transparent)
                        .padding(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = dayAfter.dayOfWeek.toString().substring(0, 1),
                        textAlign = TextAlign.Center,
                        color = Color.Black,

                        )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = dayAfter.dayOfMonth.toString(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                }

                Spacer(Modifier.height(10.dp))

                Icon(
                    painter = painterResource(Res.drawable.good),
                    tint = Color.Transparent,
                    contentDescription = "teste",
                    modifier = Modifier
                )
            }
        }
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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

        repeat(2) { index ->
            SDSCardAssessment(
                title = "Vision Acuity Test",
                hour = "10:00 AM",
                btnRound = true,
                exerciseDuration = 2,
                subtitle = "Take the test",
                topBar = true,
                bottomBar = index != 1,
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
private fun EyeWellnessTipsTitle() {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(SightUPTheme.sightUPColors.background_light)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Eye Wellness Tips ", style = SightUPTheme.textStyles.h5
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Image(
                        painter = painterResource(Res.drawable.information),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Button(
                    {},
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    modifier = Modifier,
                    shape = SightUPTheme.shapes.small,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "View All", color = SightUPTheme.sightUPColors.text_primary
                        )
                        Spacer(Modifier.width(8.dp))
                        Image(
                            painter = painterResource(Res.drawable.arrow_right),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EyeWellnessTips() {
    Column(
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base),
        modifier = Modifier.fillMaxWidth()
            .padding(top = SightUPTheme.spacing.spacing_md)
    ) {
        EyeWellnessTipsTitle()

        val conditions = mutableStateListOf(
            Condition("Eye Strain", "Message for Eye Strain"),
            Condition("Dry Eyes", "Message for Dry Eyes"),
            Condition("Red Eyes", "Message for Red Eyes")
        )
        val items = remember {
            conditions.map { condition ->
                ExpandableItem(
                    title = condition.title, message = condition.message, isExpanded = false
                )
            }
        }
        val expandedStates = remember {
            mutableStateListOf(
                *BooleanArray(items.size) { false }.toTypedArray()
            )
        }

        items.forEachIndexed { index, item ->
            ExpandableListItem(item = item,
                isExpanded = expandedStates[index],
                onExpandedChange = { expandedStates[index] = it })
        }
    }
}