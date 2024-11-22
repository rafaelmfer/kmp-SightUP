package com.europa.sightup.presentation.screens.test.result

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.VisionTestTypes
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSCardTest
import com.europa.sightup.presentation.designsystem.components.SDSDialog
import com.europa.sightup.presentation.designsystem.components.SDSDivider
import com.europa.sightup.presentation.designsystem.components.SDSPagerWithDots
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.PrescriptionsScreens
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.test.active.ActiveTest
import com.europa.sightup.presentation.screens.test.active.EChart
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.clickableWithRipple
import com.europa.sightup.utils.navigate
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionResult
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.close
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.save
import sightupkmpapp.composeapp.generated.resources.tip

@Composable
fun TestResultScreen(
    appTest: Boolean = true,
    testId: String = "",
    testTitle: String = "",
    left: String = "",
    right: String = "",
    navController: NavController,
) {
    var showDialogDiscard by remember { mutableStateOf(false) }
    var showDialogSave by remember { mutableStateOf(false) }

    val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val formattedDate = formatDate(today)

    val viewModel = koinViewModel<TestResultViewModel>()
    val testResultState by viewModel.newTestResult.collectAsState()
    val testListState by viewModel.test.collectAsState()

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
    }

    // Filters the test
    LaunchedEffect(testListState) {
        if (testListState is UIState.Success) {
            val tests = (testListState as UIState.Success<List<TestResponse>>).data
            visualAcuityTestContent = tests.find { it.title == VisionTestTypes.VisionAcuity.title }
            astigmatismTestContent = tests.find { it.title == VisionTestTypes.Astigmatism.title }
        }
    }

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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SDSTopBar(
                title = "$testTitle Result",
                iconRight = Res.drawable.close,
                iconRightVisible = true,
                onRightButtonClick = {
                    showDialogDiscard = true
                },
                modifier = Modifier
                    .background(
                        if (showLoadingAnimation) SightUPTheme.sightUPColors.background_default else SightUPTheme.sightUPColors.background_light
                    )
                    .padding(horizontal = SightUPTheme.spacing.spacing_xs)
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !showLoadingAnimation,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                        expandVertically(
                            expandFrom = Alignment.Bottom,
                            animationSpec = tween(durationMillis = 500, delayMillis = 500)
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                        shrinkVertically(
                            shrinkTowards = Alignment.Bottom,
                            animationSpec = tween(durationMillis = 500, delayMillis = 500)
                        ),
            ) {
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
                        showDialogSave = true
                    },
                    modifier = Modifier.background(SightUPTheme.sightUPColors.background_default)
                )
            }
        },
        content = { paddingValues ->
            if (showLoadingAnimation) {
                AnimationEvaluatingResultsScreen(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(SightUPTheme.sightUPColors.background_default),
                    onResultReady = { showLoadingAnimation = false }
                )
            } else {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = SightUPTheme.sightUPColors.background_light)
                        .verticalScroll(scrollState)
                        .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
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
                                    },
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
                            }
                        }

                        VisionTestTypes.Astigmatism.title -> {
                            visualAcuityTestContent?.let {
                                SDSCardTest(
                                    test = it,
                                    navigateToTest = {
                                        navigateToVisualAcuityTest()
                                    },
                                )
                                Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
                            }
                        }
                    }
                }
            }
        }
    )

    SDSDialog(
        showDialog = showDialogDiscard,
        onDismiss = { showDialogDiscard = it },
        title = "Discard results?",
        onClose = {},
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_md)
            ) {
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                Text(
                    text = "Your test result will delete. Or you can skip saving the result if",
                    style = SightUPTheme.textStyles.body,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                Text(
                    text = "• Someone else took the test on your device",
                    style = SightUPTheme.textStyles.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                Text(
                    text = "• You don't need the result for this single test",
                    style = SightUPTheme.textStyles.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            }
        },
        onPrimaryClick = {
            navController.popBackStack<TestScreens.TestRoot>(inclusive = false)
        },
        buttonPrimaryText = "Discard",
        onSecondaryClick = {},
        buttonSecondaryText = "No",
    )

    SDSDialog(
        showDialog = showDialogSave,
        onDismiss = { showDialogSave = it },
        title = "Successfully saved!",
        onClose = {},
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_md)
            ) {
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                Text(
                    text = "Check your previous vision test result and differences in Prescriptions.",
                    style = SightUPTheme.textStyles.body,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            }
        },
        onPrimaryClick = {
            navController.popBackStack<TestScreens.TestRoot>(inclusive = false)
        },
        buttonPrimaryText = "Vision Tests",
        onSecondaryClick = {
            navController.navigate(PrescriptionsScreens.PrescriptionsRoot)
        },
        buttonSecondaryText = "Prescriptions",
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
            .padding(top = SightUPTheme.spacing.spacing_md)
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
                testDescription = VisionTestTypes.Astigmatism.description,
                date = date,
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
    var showDialog by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = eyeLabel,
                style = SightUPTheme.textStyles.body,
                modifier = Modifier.weight(ONE_FLOAT)
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
                    .padding(top = SightUPTheme.spacing.spacing_2xs)
                    .clickableWithRipple { showDialog = true }
            ) {
                Text(
                    text = "$astigmatismValue\u00B0",
                    style = SightUPTheme.textStyles.large,
                )
                Spacer(modifier = Modifier.width(SightUPTheme.sizes.size_8))
                Icon(
                    painter = painterResource(Res.drawable.information),
                    contentDescription = "Information",
                    modifier = Modifier.size(SightUPTheme.sizes.size_16)
                )
            }
        }
    }
    if (showDialog) {
        SDSDialog(
            showDialog = true,
            onDismiss = {
                showDialog = false
                currentPage = 0
                coroutineScope.launch {
                    pagerState.scrollToPage(0)
                }
            },
            title = if (currentPage == 0) "Results meaning" else "Examples in daily life",
            onClose = if (currentPage == 0) {
                {
                    showDialog = false
                    currentPage = 0
                }
            } else {
                null
            },
            content = { _ ->
                when (currentPage) {
                    0 -> Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SightUPTheme.spacing.spacing_md)
                    ) {
                        DialogContentTestResults(
                            imageRes = VisionTestTypes.Astigmatism.resultImage,
                            imageHeight = 68.dp,
                            text = "Your astigmatism is located at a $astigmatismValue° axis. It means the shape of your eye causes light to focus unevenly at this angle."
                        )
                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    }

                    1 -> SDSPagerWithDots(
                        pagerState = pagerState,
                        pageCount = 3,
                        contentProvider = { page ->
                            when (page) {
                                0 -> PagerTestResultContent(
                                    VisionTestTypes.Astigmatism.resultReadImage,
                                    "Reading",
                                    VisionTestTypes.Astigmatism.resultReadDescription
                                )

                                1 -> PagerTestResultContent(
                                    VisionTestTypes.Astigmatism.resultDriveImage,
                                    "Driving",
                                    VisionTestTypes.Astigmatism.resultDriveDescription
                                )

                                2 -> PagerTestResultContent(
                                    VisionTestTypes.Astigmatism.resultPhoneImage,
                                    "Using smartphone",
                                    VisionTestTypes.Astigmatism.resultPhoneDescription
                                )
                            }
                        }
                    )
                }
            },
            onPrimaryClick = {
                if (currentPage == 0) {
                    currentPage = 1
                } else {
                    currentPage = 0
                    coroutineScope.launch {
                        pagerState.scrollToPage(0)
                    }
                    showDialog = false
                }
            },
            buttonPrimaryText = if (currentPage == 0) "Next" else "Close"
        )
    }
}


@Composable
private fun PagerTestResultContent(
    image: DrawableResource,
    title: String,
    text: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Image(
            painter = painterResource(image),
            contentDescription = title,
            modifier = Modifier.fillMaxWidth()
                .height(130.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = SightUPTheme.textStyles.body,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Text(
            text = text,
            style = SightUPTheme.textStyles.body,
            modifier = Modifier
                .fillMaxWidth(),
        )
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
            .padding(top = SightUPTheme.spacing.spacing_md)
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
                testDescription = VisionTestTypes.VisionAcuity.description,
                date = date,
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
    val showDialog = remember { mutableStateOf(false) }
    val currentPage = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val (topNum, bottomNum) = acuityValue.split("/").let {
        val top = it.getOrNull(0)?.toFloatOrNull()
        val bottom = it.getOrNull(1)?.toFloatOrNull()
        top to bottom
    }

    val decimalRtoL = if (topNum != null && bottomNum != null && bottomNum != 0f) {
        val ratio = topNum / bottomNum
        ratio.toString().take(3) // workaround to take only 3 decimals
    } else {
        null
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().clickableWithRipple { showDialog.value = true }
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

    if (showDialog.value) {
        SDSDialog(
            showDialog = true,
            onDismiss = {
                showDialog.value = false
                currentPage.value = 0
                coroutineScope.launch {
                    pagerState.scrollToPage(0)
                }
            },
            title = if (currentPage.value == 0) "Results meaning" else "Examples in daily life",
            onClose = if (currentPage.value == 0) {
                {
                    showDialog.value = false
                    currentPage.value = 0
                }
            } else {
                null
            },
            content = { _ ->
                when (currentPage.value) {
                    0 -> Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = SightUPTheme.spacing.spacing_md),
                    ) {
                        DialogContentTestResults(
                            imageRes = VisionTestTypes.VisionAcuity.resultImage,
                            imageHeight = 68.dp,
                            text = "You need to be ${topNum?.toInt()} feet away to see what most people can see from ${bottomNum?.toInt()} feet.",
                        )
                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

                        SDSDivider()

                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.tip),
                                contentDescription = "description",
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = "Measurement notation",
                                style = SightUPTheme.textStyles.body,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth().padding(start = SightUPTheme.spacing.spacing_2xs)
                            )
                        }

                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Snellen [Feet]",
                                style = SightUPTheme.textStyles.body2,
                            )
                            Text(
                                text = acuityValue,
                                style = SightUPTheme.textStyles.button,
                                color = SightUPTheme.sightUPColors.primary_700,
                            )
                        }

                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Decimal",
                                style = SightUPTheme.textStyles.body2,
                            )
                            Text(
                                text = decimalRtoL ?: "",
                                style = SightUPTheme.textStyles.button,
                                color = SightUPTheme.sightUPColors.primary_700,
                            )
                        }
                        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    }


                    1 -> SDSPagerWithDots(
                        pagerState = pagerState,
                        pageCount = 3,
                        contentProvider = { page ->
                            when (page) {
                                0 -> PagerTestResultContent(
                                    VisionTestTypes.VisionAcuity.resultReadImage,
                                    "Reading",
                                    VisionTestTypes.VisionAcuity.resultReadDescription
                                )

                                1 -> PagerTestResultContent(
                                    VisionTestTypes.VisionAcuity.resultDriveImage,
                                    "Driving",
                                    VisionTestTypes.VisionAcuity.resultDriveDescription
                                )

                                2 -> PagerTestResultContent(
                                    VisionTestTypes.VisionAcuity.resultPhoneImage,
                                    "Using smartphone",
                                    VisionTestTypes.VisionAcuity.resultPhoneDescription
                                )
                            }
                        }
                    )
                }
            },
            onPrimaryClick = {
                if (currentPage.value == 0) {
                    currentPage.value = 1
                } else {
                    currentPage.value = 0
                    coroutineScope.launch {
                        pagerState.scrollToPage(0)
                    }
                    showDialog.value = false
                }
            },
            buttonPrimaryText = if (currentPage.value == 0) "Next" else "Close"
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
                    val trianglePath = Path().apply {
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
    testDescription: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = date,
            style = SightUPTheme.textStyles.body2
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
                .clickableWithRipple { showDialog.value = true }
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

    SDSDialog(
        showDialog = showDialog.value,
        onDismiss = { showDialog.value = it },
        title = "About the test",
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_md)
            ) {
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                Text(
                    text = testDescription,
                    style = SightUPTheme.textStyles.body,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            }
        },
        onPrimaryClick = { showDialog.value = false },
        buttonPrimaryText = "Okay",
    )
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
            .padding(SightUPTheme.spacing.spacing_md)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SDSButton(
            text = "Test again",
            onClick = onClickTestAgain,
            buttonStyle = ButtonStyle.OUTLINED,
            modifier = Modifier.weight(ONE_FLOAT)
        )
        Spacer(modifier = Modifier.width(SightUPTheme.spacing.spacing_base))
        SDSButton(
            text = stringResource(Res.string.save),
            onClick = onClickSave,
            buttonStyle = ButtonStyle.PRIMARY,
            modifier = Modifier.weight(ONE_FLOAT)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AnimationEvaluatingResultsScreen(
    modifier: Modifier = Modifier,
    onResultReady: () -> Unit,
) {
    val animationPath = "files/evaluate_animation.json"

    val compositionResult: LottieCompositionResult = rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animationPath).decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        iterations = Compottie.IterateForever
    )

    if (compositionResult.isComplete) {

        LaunchedEffect(Unit) {
            delay(5000L)
            onResultReady()
        }

        Column(
            modifier = Modifier.fillMaxSize().then(modifier),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberLottiePainter(
                    composition = compositionResult.value,
                    progress = { progress }
                ),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SightUPTheme.sightUPColors.background_default)
                    .height(SightUPTheme.sizes.size_100),
                contentDescription = null
            )
            Text(
                text = "Evaluating the results now",
                color = SightUPTheme.sightUPColors.text_primary,
                style = SightUPTheme.textStyles.h5,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun DialogContentTestResults(
    imageRes: DrawableResource,
    imageHeight: Dp = 130.dp,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        Image(
            painter = painterResource(imageRes),
            contentDescription = "Test representation",
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Inside
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Text(
            text = text,
            style = SightUPTheme.textStyles.body,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun formatDate(date: LocalDate): String {
    val month = date.month.name.take(3)
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val year = date.year.toString()
    return "${month.lowercase().capitalize()} $day, $year"
}