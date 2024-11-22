package com.europa.sightup.presentation.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.DailyCheckInResponse
import com.europa.sightup.data.remote.response.DailyExerciseResponse
import com.europa.sightup.presentation.designsystem.components.ExpandableItem
import com.europa.sightup.presentation.designsystem.components.ExpandableListItem
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSCardAssessment
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.designsystem.components.hideBottomSheetWithAnimation
import com.europa.sightup.presentation.navigation.ExerciseScreens.ExerciseDetails
import com.europa.sightup.presentation.screens.home.checkinbottomsheet.DailyCheckInFlowBottomSheet
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.SightUPSpacing
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.fontWeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.Moods
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.formatTime
import com.europa.sightup.utils.isUserLoggedIn
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.add
import sightupkmpapp.composeapp.generated.resources.arrow_right
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.edit
import sightupkmpapp.composeapp.generated.resources.good
import sightupkmpapp.composeapp.generated.resources.gray_nothing
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.schedule
import sightupkmpapp.composeapp.generated.resources.today

data class Condition(
    val title: String,
    val badge: String,
    val message: String,
)

@Composable
private fun IconSort(myIcon: String): Painter {
    return when (myIcon) {
        Moods.VERY_POOR.value -> painterResource(Moods.VERY_POOR.icon)
        Moods.POOR.value -> painterResource(Moods.POOR.icon)
        Moods.MODERATE.value -> painterResource(Moods.MODERATE.icon)
        Moods.GOOD.value -> painterResource(Moods.GOOD.icon)
        Moods.VERY_GOOD.value -> painterResource(Moods.VERY_GOOD.icon)
        "Add" -> painterResource(Res.drawable.add)
        else -> painterResource(Res.drawable.gray_nothing)
    }
}

private fun getIconDate(daysBefore: LocalDate, state: UIState<List<DailyCheckInResponse>>): String {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    println("State content: $state")
    return when (state) {
        is UIState.Success -> {
            val list = state.data
            println("Daily Check List: $list")

            val matchedItem = list.firstOrNull { item ->
                daysBefore.toString() == item.dailyCheckDate
            }

            if (today == daysBefore) {
                println("Matched Item: $matchedItem")
                matchedItem?.dailyCheckInfo?.visionStatus ?: "Add"
            } else {
                println("Date: $daysBefore, Vision Status: ${matchedItem?.dailyCheckInfo?.visionStatus}")
                matchedItem?.dailyCheckInfo?.visionStatus ?: "Any"
            }
        }

        else -> {
            println("State is not success: $state")
            "Any"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.dailyCheckGet.collectAsStateWithLifecycle()
    val dailyExerciseState by viewModel.dailyExerciseList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUser()
        viewModel.getAllDay()
        viewModel.getDailyExerciseList()
    }

    val user = viewModel.user.value as? UIState.Success
    val name = user?.data?.userName ?: "Guest"
    val userIsLogged = isUserLoggedIn

    val scope = rememberCoroutineScope()
    var dailyCheckBottomSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }
    var dailyResultBottomSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }

    val dailyCheckInSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false },
    )

    val dailyCheckState by viewModel.dailyCheck.collectAsStateWithLifecycle()

    val dailyCheckList = (state as? UIState.Success)?.data ?: emptyList()
    val mostRecentCheck = dailyCheckList.maxByOrNull { it.dailyCheckDate }
    val dailyCheckResult = mostRecentCheck?.dailyCheckInfo
    val dailyCheckTime = dailyCheckResult?.infoTime ?: ""
    val dailyCheckIsDone = dailyCheckResult?.done ?: false // dailyCheckTime != ""

    println("->Daily Check Rdo: $dailyCheckResult")
    println("->Daily Check Time: $dailyCheckTime")
    println("->Daily Check Is Done: $dailyCheckIsDone")

    var showHome by remember { mutableStateOf(false) }

    val screenAlpha by animateFloatAsState(
        targetValue = if (showHome) 1f else 0f,
    )

    LaunchedEffect(Unit) {
        showHome = true
    }

    val exerciseList = when (val exerciseState = dailyExerciseState) {
        is UIState.Success -> {
            exerciseState.data
        }

        else -> {
            listOf()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(SightUPTheme.sightUPColors.background_light)
            .graphicsLayer(alpha = screenAlpha),
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base),
    ) {
        GreetingWithIcons(name)

        ShowCalendar(state)

        if (userIsLogged) {
            NextTestCard(
                nameOfTest = "Vision Acuity Test",
                testDate = "Nov 25, 2024",
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
        }


        AssessmentList(
            navController = navController,
            onDailyCheckClick = {
                dailyCheckBottomSheetVisibility = BottomSheetEnum.SHOW
            },
            dailyCheckIsDone = dailyCheckIsDone,
            dailyCheckTime = if (dailyCheckTime.isNotBlank()) dailyCheckTime.formatTime() else "",
            exerciseList = exerciseList
        )

        EyeWellnessTips()
    }

    SDSBottomSheet(
        isDismissible = false,
        expanded = dailyCheckBottomSheetVisibility,
        onExpandedChange = {
            dailyCheckBottomSheetVisibility = it
        },
        onDismiss = {
            dailyCheckBottomSheetVisibility = BottomSheetEnum.HIDE
        },
        sheetState = dailyCheckInSheetState,
        fullHeight = true,
        title = null,
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                DailyCheckInFlowBottomSheet(
                    onCloseIconTopBar = {
                        dailyCheckBottomSheetVisibility = BottomSheetEnum.HIDE
                    },
                    onComplete = { dailyCheckInput ->
                        viewModel.saveDailyCheck(
                            dailyCheckDate = dailyCheckInput.dailyCheckDate,
                            email = dailyCheckInput.email,
                            visionStatus = dailyCheckInput.dailyCheckInfo?.visionStatus ?: Moods.MODERATE.value,
                            condition = dailyCheckInput.dailyCheckInfo?.condition ?: listOf(),
                            causes = dailyCheckInput.dailyCheckInfo?.causes ?: listOf(),
                        )
                    },
                    modifier = Modifier
                )
                when (dailyCheckState) {
                    is UIState.Loading<*> -> {
                        CircularProgressIndicator(
                            Modifier.align(Alignment.Center)
                        )
                    }

                    is UIState.Success -> {
                        scope.hideBottomSheetWithAnimation(
                            sheetState = dailyCheckInSheetState,
                            onBottomSheetVisibilityChange = {
                                dailyCheckBottomSheetVisibility = BottomSheetEnum.HIDE
                            },
                            onFinish = {
                                dailyResultBottomSheetVisibility = BottomSheetEnum.SHOW
                            }
                        )
                    }

                    is UIState.Error -> {
                        println("Error: ${(dailyCheckState as UIState.Error).message}")
                    }

                    else -> {}
                }
            }
        }
    )

    DailyCheckStatusResultBottomSheet(
        bottomSheetVisibility = dailyResultBottomSheetVisibility,
        onBottomSheetVisibilityChange = { dailyResultBottomSheetVisibility = it },
        visionStatus = Moods.fromString(dailyCheckResult?.visionStatus ?: "").value,
        imageIcon = Moods.fromString(dailyCheckResult?.visionStatus ?: "").icon,
        conditions = dailyCheckResult?.condition ?: listOf(),
        causes = dailyCheckResult?.causes ?: listOf(),
        onDismiss = {
            viewModel.resetDailyCheckState()
            viewModel.getAllDay()
        },
    )
}

@Composable
fun ShowCalendar(state: UIState<List<DailyCheckInResponse>>) {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        Text(
            month,
            modifier = Modifier
                .clip(SightUPTheme.shapes.extraLarge)
                .background(SightUPTheme.sightUPColors.background_activate)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            style = SightUPTheme.textStyles.footnote
        )

        Spacer(Modifier.height(SightUPTheme.sizes.size_4))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val dayAfter = today.plus(DatePeriod(days = 1))

            for (i in 5 downTo 1) {
                val daysBefore = today.minus(DatePeriod(days = i))

                Column(
                    modifier = Modifier
                        .width(SightUPTheme.sizes.size_40),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .width(SightUPTheme.sizes.size_40)
                            .clip(SightUPTheme.shapes.small)
                            .background(Color.Transparent)
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = daysBefore.dayOfWeek.toString().substring(0, 1),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            style = SightUPTheme.textStyles.caption,
                        )
                        Spacer(Modifier.height(SightUPTheme.sizes.size_6))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = daysBefore.dayOfMonth.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            style = SightUPTheme.textStyles.body2,
                        )
                    }
                    Spacer(Modifier.height(SightUPTheme.sizes.size_4))
                    Image(
                        painter = IconSort(getIconDate(daysBefore, state)),
                        contentDescription = "description",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(SightUPTheme.sizes.size_32)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .width(SightUPTheme.sizes.size_40),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .width(SightUPTheme.sizes.size_40)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SightUPTheme.sightUPColors.background_button)
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = today.dayOfWeek.toString().substring(0, 1),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = SightUPTheme.fontWeight.fontWeight_regular,
                        style = SightUPTheme.textStyles.caption,
                    )
                    Spacer(Modifier.height(SightUPTheme.sizes.size_6))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = today.dayOfMonth.toString(),
                        fontWeight = SightUPTheme.fontWeight.fontWeight_regular,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = SightUPTheme.textStyles.body2,
                    )
                }

                Spacer(Modifier.height(SightUPTheme.sizes.size_4))

                Image(
                    painter = IconSort(getIconDate(today, state)),
                    contentDescription = "description",
                    modifier = if (getIconDate(today, state) == "Add")
                        Modifier
                            .padding(horizontal = 4.dp)
                            .padding(8.dp)
                            .size(16.dp)
                    else Modifier
                        .padding(horizontal = 4.dp)
                        .size(SightUPTheme.sizes.size_32)
                )
            }

            Column(
                modifier = Modifier
                    .width(SightUPTheme.sizes.size_40),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .width(SightUPTheme.sizes.size_40)
                        .background(Color.Transparent)
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = dayAfter.dayOfWeek.toString().substring(0, 1),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = SightUPTheme.textStyles.caption,
                    )
                    Spacer(Modifier.height(SightUPTheme.sizes.size_6))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = dayAfter.dayOfMonth.toString(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = SightUPTheme.textStyles.body2,
                    )
                }

                Spacer(Modifier.height(SightUPTheme.sizes.size_4))

                Icon(
                    painter = painterResource(Res.drawable.good),
                    tint = Color.Transparent,
                    contentDescription = "test",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(SightUPTheme.sizes.size_32)
                )
            }
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
    }
}

@Composable
private fun GreetingWithIcons(name: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
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

        Box(
            modifier = Modifier
                .constrainAs(todayIcon) {
                    top.linkTo(greetingText.top)
                    bottom.linkTo(greetingText.bottom)
                    start.linkTo(greetingText.end, margin = 8.dp)
                }
                .alpha(0f) // Not sure if we are going to have this button anymore
        ) {
            Icon(
                painter = painterResource(Res.drawable.today),
                contentDescription = "Today",
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_32)
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .constrainAs(calendarIcon) {
                    top.linkTo(todayIcon.top)
                    bottom.linkTo(todayIcon.bottom)
                    start.linkTo(todayIcon.end)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                painter = painterResource(Res.drawable.schedule),
                contentDescription = "Calendar",
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_32)
            )
        }
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
            .background(SightUPTheme.sightUPColors.background_info),
        shape = SightUPTheme.shapes.small,
        color = SightUPTheme.sightUPColors.background_info,
        border = BorderStroke(
            width = SightUPBorder.Width.sm,
            color = SightUPTheme.sightUPColors.border_info
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (title, closeIcon, dateText, editIcon, daysLeftText) = createRefs()

            Text(
                text = "Next: $nameOfTest",
                style = SightUPTheme.textStyles.body2.copy(
                    fontWeight = FontWeight.Bold
                ),
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
                    modifier = Modifier
                        .size(SightUPTheme.sizes.size_16)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        onClickEdit()
                    }
                    .constrainAs(dateText) {
                        top.linkTo(title.bottom, margin = SightUPSpacing.default.spacing_xs)
                        bottom.linkTo(parent.bottom, margin = SightUPSpacing.default.spacing_sm)
                        start.linkTo(parent.start, margin = SightUPSpacing.default.spacing_base)
                        verticalBias = ONE_FLOAT
                    }
            ) {
                Text(
                    text = testDate,
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier
                )
                Spacer(Modifier.width(SightUPTheme.sizes.size_4))
                Icon(
                    painter = painterResource(Res.drawable.edit),
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(SightUPTheme.sizes.size_16)
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
                    verticalBias = ONE_FLOAT
                }
            )
        }
    }
}

@Composable
private fun AssessmentList(
    navController: NavController? = null,
    onDailyCheckClick: () -> Unit = {},
    dailyCheckIsDone: Boolean = false,
    dailyCheckTime: String = "",
    exerciseList: List<DailyExerciseResponse> = listOf(),
) {
    val userIsLogged = isUserLoggedIn

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SDSCardAssessment(
            title = "Daily Check-In",
            isDone = dailyCheckIsDone,
            time = dailyCheckTime,
            exerciseDuration = 0,
            subtitle = "Log your eye condition",
            lineUp = false,
            lineDown = true,
            eyeConditions = listOf(),
            onClickCard = onDailyCheckClick,
            modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
        )

        if (userIsLogged) {
            exerciseList.forEachIndexed { index, exercise ->
                SDSCardAssessment(
                    title = "${exercise.title} (${index + 1}/${exerciseList.size})",
                    time = exercise.timeSchedule,
                    isDone = exercise.done,
                    exerciseDuration = exercise.duration,
                    eyeConditions = exercise.eyeCondition,
                    lineUp = true,
                    lineDown = index != exerciseList.lastIndex,
                    onClickCard = {
                        navController?.navigate(
                            ExerciseDetails(
                                exerciseId = exercise.id,
                                exerciseName = exercise.title,
                                category = exercise.category,
                                motivation = exercise.motivation,
                                duration = exercise.duration,
                                imageInstruction = exercise.imageInstruction,
                                video = exercise.video,
                                finishTitle = exercise.finishTitle,
                                advice = exercise.advice
                            )
                        )
                    },
                    modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                )
            }
        } else {
            SDSCardAssessment(
                title = "Circular Motion",
                time = "1:00 pm",
                isDone = false,
                exerciseDuration = 60,
                eyeConditions = listOf("Eye Strain", "Dry Eyes", "Red Eyes"),
                lineUp = true,
                lineDown = false,
                onClickCard = {
                    navController?.navigate(
                        ExerciseDetails(
                            exerciseId = "6702f88243321fa1eb25b51f",
                            exerciseName = "Circular Motion",
                            category = "Movement",
                            motivation = "Let’s take a quick break and give your eyes some gentle movement!",
                            duration = 60,
                            imageInstruction = "https://firebasestorage.googleapis.com/v0/b/sightup-3b463.firebasestorage.app/o/illustrations%2Feye_exercises%2Fcircular_motion.png?alt=media&token=4b5ad4e1-ba36-4780-9bb4-1925ffee2aa1",
                            video = "https://firebasestorage.googleapis.com/v0/b/sightup-3b463.firebasestorage.app/o/animations%2Fcircular_motion%2FCircular%20Motion_Exercise_fv.mp4?alt=media&token=d3d5ff28-af58-4c01-a94d-1fa1e679c895",
                            finishTitle = "Great job! You have completed the Eye Movement Exercise.",
                            advice = "For better results, repeat this exercise twice a day."
                        )
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
        modifier = Modifier
            .fillMaxWidth()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Eye Wellness Tips",
                        style = SightUPTheme.textStyles.h5
                    )
                    Spacer(Modifier.width(SightUPTheme.sizes.size_4))
                    Image(
                        painter = painterResource(Res.drawable.information),
                        contentDescription = null,
                        modifier = Modifier
                            .size(SightUPTheme.sizes.size_16)
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SightUPTheme.sightUPColors.background_light,
                        contentColor = SightUPTheme.sightUPColors.text_primary
                    ),
                    modifier = Modifier,
                    shape = SightUPTheme.shapes.small,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "View All",
                        color = SightUPTheme.sightUPColors.text_primary,
                        style = SightUPTheme.textStyles.caption.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.width(SightUPTheme.sizes.size_8))
                    Image(
                        painter = painterResource(Res.drawable.arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(SightUPTheme.sizes.size_16)
                    )
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
            .padding(bottom = SightUPTheme.spacing.spacing_base)
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        EyeWellnessTipsTitle()

        val conditions = mutableStateListOf(
            Condition(
                "Apply eye drops",
                "Eye Strain",
                "Use of eye drops like artificial tears. You can also try to blink more often when using a screen, which may prevent the symptom from occurring."
            ),
            Condition(
                "Apply eye drops",
                "Dry Eyes",
                "Eye drops like artificial tears can get  without a prescription. There are also over-the-counter moisturizing  gels and ointments that may help your eyes feel better."
            ),
            Condition(
                "Wash your hands",
                "Red Eyes",
                "If you have discharge, wash the area around your eyes 2 or 3 times a day. Use a clean, wet washcloth or a fresh cotton ball each time while cleaning. "
            )
        )

        val items = remember {
            conditions.map { condition ->
                ExpandableItem(
                    title = condition.title, badge = condition.badge, message = condition.message, isExpanded = false
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DailyCheckStatusResultBottomSheet(
    bottomSheetVisibility: BottomSheetEnum,
    onBottomSheetVisibilityChange: (BottomSheetEnum) -> Unit,
    onDismiss: () -> Unit = {},
    visionStatus: String = Moods.MODERATE.value,
    imageIcon: DrawableResource = Moods.MODERATE.icon,
    conditions: List<String> = listOf(),
    causes: List<String> = listOf(),
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },
    )

    SDSBottomSheet(
        isDismissible = true,
        expanded = bottomSheetVisibility,
        onExpandedChange = {
            onBottomSheetVisibilityChange(it)
        },
        onDismiss = {
            onBottomSheetVisibilityChange(BottomSheetEnum.HIDE)
        },
        sheetState = sheetState,
        fullHeight = true,
        title = null,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SDSTopBar(
                    modifier = Modifier,
                    title = "Status",
                    iconLeftVisible = false,
                    iconRightVisible = false
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                    Spacer(Modifier.height(SightUPTheme.sizes.size_24))
                    Image(
                        painter = painterResource(imageIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                            .size(SightUPTheme.sizes.size_72)
                    )
                    Spacer(Modifier.height(SightUPTheme.sizes.size_8))
                    Text(
                        text = visionStatus,
                        textAlign = TextAlign.Center,
                        style = SightUPTheme.textStyles.body.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                            .fillMaxWidth()
                    )
                    Spacer(Modifier.height(SightUPTheme.sizes.size_32))

                    if (conditions.isNotEmpty()) {
                        Text(
                            text = "Conditions",
                            style = SightUPTheme.textStyles.subtitle,
                            modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                        )
                        Spacer(Modifier.height(SightUPTheme.sizes.size_12))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm),
                            verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm),
                            modifier = Modifier
                                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                                .fillMaxWidth()
                        ) {
                            for (condition in conditions) {
                                Text(
                                    text = condition,
                                    style = SightUPTheme.textStyles.body,
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    modifier = Modifier
                                        .clip(SightUPTheme.shapes.small)
                                        .background(
                                            color = SightUPTheme.sightUPColors.background_default,
                                            shape = SightUPTheme.shapes.small
                                        )
                                        .border(
                                            width = SightUPBorder.Width.sm,
                                            color = SightUPTheme.sightUPColors.border_card,
                                            shape = SightUPTheme.shapes.small
                                        )
                                        .padding(
                                            horizontal = SightUPTheme.spacing.spacing_base,
                                            vertical = 9.dp
                                        )
                                )
                            }
                        }
                        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
                        Text(
                            text = "Cause(s)",
                            style = SightUPTheme.textStyles.subtitle,
                            modifier = Modifier.padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                        )
                        Spacer(Modifier.height(SightUPTheme.sizes.size_12))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm),
                            verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm),
                            modifier = Modifier
                                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                                .fillMaxWidth()
                        ) {
                            for (cause in causes) {
                                Text(
                                    text = cause,
                                    style = SightUPTheme.textStyles.body,
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    modifier = Modifier
                                        .clip(SightUPTheme.shapes.small)
                                        .background(
                                            color = SightUPTheme.sightUPColors.background_default,
                                            shape = SightUPTheme.shapes.small
                                        )
                                        .border(
                                            width = SightUPBorder.Width.sm,
                                            color = SightUPTheme.sightUPColors.border_card,
                                            shape = SightUPTheme.shapes.small
                                        )
                                        .padding(
                                            horizontal = SightUPTheme.spacing.spacing_base,
                                            vertical = 9.dp
                                        )
                                )
                            }
                        }
                        Spacer(Modifier.height(SightUPTheme.sizes.size_12))
                        EyeWellnessTips()
                    }

                    Spacer(Modifier.height(SightUPTheme.sizes.size_24))

                    SDSButton(
                        text = stringResource(Res.string.close),
                        onClick = {
                            scope.hideBottomSheetWithAnimation(
                                sheetState = sheetState,
                                onBottomSheetVisibilityChange = {
                                    onBottomSheetVisibilityChange(BottomSheetEnum.HIDE)
                                },
                                onFinish = {
                                    onDismiss()
                                }
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                            .fillMaxWidth()
                            .padding(bottom = SightUPTheme.spacing.spacing_md)
                    )
                }
            }
        }
    )
}