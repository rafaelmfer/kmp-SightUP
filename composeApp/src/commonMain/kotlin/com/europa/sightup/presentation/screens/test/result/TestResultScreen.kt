package com.europa.sightup.presentation.screens.test.result

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.VisionTestTypes
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSCardTest
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.test.active.ActiveTest
import com.europa.sightup.presentation.screens.test.active.EChart
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.navigate
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.information

@Composable
fun TestResultScreen(
    appTest: Boolean = true,
    testId: String = "",
    testTitle: String = "",
    left: String = "",
    right: String = "",
    navController: NavController,
) {
    val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val formattedDate = formatDate(today)

    val viewModel = koinViewModel<TestResultViewModel>()
    val testResultState by viewModel.newTestResult.collectAsState()
    val testListState by viewModel.test.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    var visualAcuityTestContent by remember { mutableStateOf<TestResponse?>(null) }
    var astigmatismTestContent by remember { mutableStateOf<TestResponse?>(null) }
    var testType by remember { mutableStateOf("") }
    var showLoadingAnimation by remember { mutableStateOf(true) }


    LaunchedEffect(testTitle) {
        if (testTitle.contains(VisionTestTypes.VisionAcuity.title)) {
            viewModel.setActiveTest(ActiveTest.VisualAcuity)
            testType = VisionTestTypes.VisionAcuity.title
        } else if (testTitle.contains(VisionTestTypes.Astigmatism.title)) {
            viewModel.setActiveTest(ActiveTest.Astigmatism)
            testType = VisionTestTypes.Astigmatism.title
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getTests()
        delay(20000L)
        showLoadingAnimation = false
    }

    // Filters the test
    LaunchedEffect(testListState) {
        if (testListState is UIState.Success) {
            val tests = (testListState as UIState.Success<List<TestResponse>>).data
            println("Tests: $tests")
            visualAcuityTestContent = tests.find { it.title == VisionTestTypes.VisionAcuity.title }
            astigmatismTestContent = tests.find { it.title == VisionTestTypes.Astigmatism.title }
        }
    }

    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            showToast("Test saved successfully")
        }
    }

    // For the loading animation
    val isLoading = testListState is UIState.Loading || testResultState is UIState.Loading

    // Helper function to navigate to the test screen
    fun navigateToVisualAcuityTest() {
        visualAcuityTestContent?.let {
            navController.navigate(
                route = TestScreens.TestIndividual.toString(),
                objectToSerialize = it
            )
        }
    }

    fun navigateToAstigmatismTest() {
        astigmatismTestContent?.let {
            navController.navigate(
                route = TestScreens.TestIndividual.toString(),
                objectToSerialize = it
            )
        }
    }

    Scaffold(
        topBar = {
            SDSTopBar(
                title = "$testTitle Result",
                iconRight = Icons.Default.Close,
                iconRightVisible = true,
                onRightButtonClick = {
                    showToast("TODO: Warning message")
                    navController.navigate(Home)
                },
                modifier = Modifier.background(SightUPTheme.sightUPColors.background_light)
            )
        },
        bottomBar = {
            ButtonBottomBar(
                onClickTestAgain = {
                    when (testType) {
                        VisionTestTypes.VisionAcuity.title -> navigateToVisualAcuityTest()
                        VisionTestTypes.Astigmatism.title -> navigateToAstigmatismTest()
                    }
                },
                onClickSave = {
                    viewModel.saveVisionTest(
                        appTest = appTest,
                        testId = testId,
                        testTitle = testTitle,
                        left = left,
                        right = right
                    )
                }
            )
        },
        content = { paddingValues ->
            if (isLoading && showLoadingAnimation) {
                AnimationEvaluatingResultsScreen(modifier = Modifier.padding(paddingValues))
            } else {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier.padding(paddingValues)
                        .background(color = SightUPTheme.sightUPColors.background_light)
                        .padding(
                            horizontal = SightUPTheme.spacing.spacing_side_margin,
                            vertical = SightUPTheme.spacing.spacing_md
                        )
                        .verticalScroll(scrollState)
                ) {
                    when (testType) {
                        VisionTestTypes.VisionAcuity.title -> {
                            VisualAcuityCardResults(
                                rightEyeAcuity = right,
                                leftEyeAcuity = left,
                                rightEyePosition = EChart.getRowForScore(right) - 1,
                                leftEyePosition = EChart.getRowForScore(left) - 1,
                                date = formattedDate
                            )
                        }

                        VisionTestTypes.Astigmatism.title -> {
                            AstigmatismCardResults(
                                rightEyeAstigmatism = right,
                                leftEyeAstigmatism = left,
                                date = formattedDate
                            )
                        }
                    }

                    Text(
                        text = "Take the other test",
                        style = SightUPTheme.textStyles.large,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(vertical = SightUPTheme.spacing.spacing_base)
                    )

                    when (testType) {
                        VisionTestTypes.VisionAcuity.title -> {
                            astigmatismTestContent?.let {
                                SDSCardTest(
                                    test = it,
                                    navigateToTest = {
                                        navigateToAstigmatismTest()
                                    }
                                )
                            }
                        }

                        VisionTestTypes.Astigmatism.title -> {
                            visualAcuityTestContent?.let {
                                SDSCardTest(
                                    test = it,
                                    navigateToTest = {
                                        navigateToVisualAcuityTest()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun AstigmatismCardResults(
    rightEyeAstigmatism: String,
    leftEyeAstigmatism: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.large)
            .background(SightUPTheme.sightUPColors.background_default)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.large
            )
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = SightUPTheme.sightUPColors.background_default
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            TestCardHeader(
                title = "Astigmatism",
                date = date
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
            EyeAstigmatismShowResults(
                eyeLabel = "Right Eye",
                astigmatismValue = rightEyeAstigmatism,
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))
            EyeAstigmatismShowResults(
                eyeLabel = "Left Eye",
                astigmatismValue = leftEyeAstigmatism,
            )

        }

    }

}

@Composable
private fun AstigmatismResultChip(
    type: Int = 0,
    modifier: Modifier = Modifier,
) {
    var backgroundColor = SightUPTheme.sightUPColors.background_error
    var text = ""
    var textColor = SightUPTheme.sightUPColors.error_300

    when (type) {
        0 -> {
            text = "You don't have astigmatism"
            textColor = SightUPTheme.sightUPColors.success_300
            backgroundColor = SightUPTheme.sightUPColors.background_success
        }

        1 -> {
            text = "You have astigmatism at"
        }
    }

    Text(
        modifier = Modifier
            .clip(SightUPTheme.shapes.extraLarge)
            .background(backgroundColor)
            .padding(
                horizontal = SightUPTheme.spacing.spacing_sm,
                vertical = SightUPTheme.spacing.spacing_2xs
            )
            .then(modifier),
        text = text,
        color = textColor,
        style = SightUPTheme.textStyles.body2
    )
}

@Composable
private fun EyeAstigmatismShowResults(
    eyeLabel: String,
    astigmatismValue: String,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = eyeLabel,
                style = SightUPTheme.textStyles.body,
                modifier = Modifier.weight(1f)
            )
            if (astigmatismValue.toInt() > 0) {
                AstigmatismResultChip(1)
            } else {
                AstigmatismResultChip(0)
            }
        }
        if (astigmatismValue.toInt() > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$astigmatismValue\u00B0",
                    style = SightUPTheme.textStyles.large,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.information),
                    contentDescription = "Information",
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun VisualAcuityCardResults(
    rightEyeAcuity: String,
    leftEyeAcuity: String,
    rightEyePosition: Int, // from 0 a 7
    leftEyePosition: Int,
    date: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.large)
            .background(SightUPTheme.sightUPColors.background_default)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.large
            )
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = SightUPTheme.sightUPColors.background_default
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TestCardHeader(
                title = "Visual Acuity",
                date = date
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
            EyeAcuityShowResults(
                eyeLabel = "Right Eye",
                acuityValue = rightEyeAcuity,
                acuityPosition = rightEyePosition
            )

            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
            EyeAcuityShowResults(
                eyeLabel = "Left Eye",
                acuityValue = leftEyeAcuity,
                acuityPosition = leftEyePosition
            )
        }
    }
}

@Composable
private fun EyeAcuityShowResults(
    eyeLabel: String,
    acuityValue: String,
    acuityPosition: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = eyeLabel,
                style = SightUPTheme.textStyles.body,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = acuityValue,
                style = SightUPTheme.textStyles.large,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(Res.drawable.information),
                contentDescription = "Information",
                modifier = Modifier.size(14.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        VisionAcuitySlider(
            selectedIndex = acuityPosition,
        )
    }
}

@Composable
private fun VisionAcuitySlider(
    selectedIndex: Int,
    modifier: Modifier = Modifier,
) {
    val circleCount = 8
    val lineThickness = 4.dp
    val selectedColor = SightUPTheme.colors.primary
    val unselectedColor = SightUPTheme.sightUPColors.primary_200
    val lineColor = SightUPTheme.sightUPColors.primary_200

    var containerWidth by remember { mutableStateOf(0) }

    val offsetYTooltip = 36.dp
    Box(
        modifier = modifier
            .padding(top = offsetYTooltip)
            .onGloballyPositioned { coordinates ->
                containerWidth = coordinates.size.width
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            val width = size.width
            val height = size.height
            val spaceBetween = width / circleCount

            // Long line
            drawLine(
                color = lineColor,
                start = Offset(0f, height / 2),
                end = Offset(width, height / 2),
                strokeWidth = lineThickness.toPx(),
                cap = StrokeCap.Round
            )

            for (i in 0 until circleCount) {
                val xOffset = i * spaceBetween + (spaceBetween / 2)
                val color = if (i == selectedIndex) selectedColor else unselectedColor
                val circleSize = if (i == selectedIndex) 16.dp else 8.dp

                drawCircle(
                    color = color,
                    radius = circleSize.toPx() / 2,
                    center = Offset(xOffset, height / 2)
                )
            }
        }

        // Handmade tooltip
        if (selectedIndex in 0 until circleCount && containerWidth > 0) {
            val spaceBetween = containerWidth.toFloat() / circleCount.toFloat()
            val xOffset = spaceBetween * selectedIndex.toFloat() - (spaceBetween / 4.0)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset { IntOffset(xOffset.toInt(), -offsetYTooltip.roundToPx()) }
            ) {
                Box(
                    modifier = Modifier
                        .background(selectedColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Here",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                Canvas(
                    modifier = Modifier.size(width = 12.dp, height = 8.dp)
                ) {
                    val trianglePath = androidx.compose.ui.graphics.Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        quadraticTo(
                            size.width / 2, size.height * 1.2f,
                            0f, 0f
                        )
                        close()
                    }
                    drawPath(trianglePath, color = selectedColor)
                }
            }
        }

        // Labels for results
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0 until circleCount) {
                val label = when (i) {
                    0 -> "20/200"
                    3 -> "20/50"
                    circleCount - 1 -> "20/10"
                    else -> ""
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (label.isNotEmpty()) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TestCardHeader(
    title: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = date,
            style = SightUPTheme.textStyles.body2
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = title,
                style = SightUPTheme.textStyles.subtitle
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(Res.drawable.information),
                contentDescription = "Information",
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun ButtonBottomBar(
    onClickTestAgain: () -> Unit,
    onClickSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SDSButton(
            text = "Test again",
            onClick = onClickTestAgain,
            buttonStyle = ButtonStyle.OUTLINED,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(SightUPTheme.spacing.spacing_base))
        SDSButton(
            text = "Save",
            onClick = onClickSave,
            buttonStyle = ButtonStyle.PRIMARY,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AnimationEvaluatingResultsScreen(
    modifier: Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize().then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Evaluating the results now",
            style = SightUPTheme.textStyles.h3,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

fun formatDate(date: LocalDate): String {
    val month = date.month.name.take(3)
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val year = date.year.toString()
    return "${month.toString().toLowerCase().capitalize()} $day, $year"
}