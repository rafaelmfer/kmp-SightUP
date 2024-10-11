package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.components.Mode
import com.europa.sightup.presentation.components.ModeSelectionCard
import com.europa.sightup.presentation.components.StepProgressBar
import com.europa.sightup.presentation.components.TitleBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.UIState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform
import sightupkmpapp.composeapp.generated.resources.test_mode_subtitle
import sightupkmpapp.composeapp.generated.resources.test_mode_title

@Composable
fun ExecutionTestScreen(
    navController: NavController,
    taskId: String?,
    ) {

    val viewModel = koinViewModel<TestViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getTests()
    }

    val state by viewModel.test.collectAsStateWithLifecycle()

    when (state) {
        is UIState.InitialState -> {}
        is UIState.Loading -> { CircularProgressIndicator() }

        is UIState.Success -> {
            val tests = (state as UIState.Success<List<TestResponse>>).data
            val selectedTest = tests.find { it.taskId == taskId }

            if (selectedTest != null) {
                TestStepProgressBarScreen(navController = navController, test = selectedTest)
            }
        }

        is UIState.Error -> {
            Text(text = "Error: ${(state as UIState.Error<List<TestResponse>>).message}")
        }
    }
}


@Composable
fun TestStepProgressBarScreen(
    navController: NavController,
    test : TestResponse
) {
    val scrollState = rememberScrollState()

    var currentStep by remember { mutableStateOf(1) }
    val numberOfSteps = 4

    var selectedMode by remember { mutableStateOf(Mode.Touch) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleBar(
            title=test.title,
            rightIcon =Icons.Default.Close,
            rightButton = true,
            onRightButtonClick = {
                println("Navigating to TestRoot")
                navController.navigate(TestScreens.TestRoot)
                                 },
            modifier = Modifier.padding(top= SightUPTheme.spacing.spacing_md ,bottom = SightUPTheme.spacing.spacing_lg)
        )

        StepProgressBar(
            numberOfSteps = numberOfSteps,
            currentStep = currentStep,
            modifier = Modifier.padding(bottom = SightUPTheme.spacing.spacing_md)
        )

        // Heading text
        Text(
            text = stringResource(Res.string.test_mode_title),
            style = SightUPTheme.textStyles.h1
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))

        Text(
            text = stringResource(Res.string.test_mode_subtitle),
            style = SightUPTheme.textStyles.body
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xl))

        ModeSelectionCard(
            mode = Mode.Touch,
            isSelected = selectedMode == Mode.Touch,
            onClick = { selectedMode = Mode.Touch }
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

        ModeSelectionCard(
            mode = Mode.Voice,
            isSelected = selectedMode == Mode.Voice,
            onClick = { selectedMode = Mode.Voice }
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

        ModeSelectionCard(
            mode = Mode.SmartWatch,
            isSelected = selectedMode == Mode.SmartWatch,
            onClick = { selectedMode = Mode.SmartWatch }
        )
        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_lg))

        HowToInfoCard()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SightUPTheme.spacing.spacing_md, bottom = SightUPTheme.spacing.spacing_base),
            shape = SightUPTheme.shapes.small,
            onClick = {
                // Increment the current step, looping back to 1 after reaching the max
                currentStep = if (currentStep < numberOfSteps) currentStep + 1 else 1
            }
        ) {
            Text("Next")
        }
    }
}

@Composable
fun HowToInfoCard(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Light Bulb",
            modifier = Modifier.size(32.dp)
                .padding(end = SightUPTheme.spacing.spacing_xs),
        )
        Text(
        text = "How to switch mode?",
        style = SightUPTheme.textStyles.small
        )
    }

    Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

    Column(
        modifier = Modifier.fillMaxWidth()
        .border(
            width = 1.dp,
            color = SightUPTheme.colors.outline,
            shape = RoundedCornerShape(8.dp))
            .padding(SightUPTheme.spacing.spacing_md),
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = SightUPTheme.spacing.spacing_base),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(Res.drawable.compose_multiplatform), contentDescription = "Smartwatch", modifier = Modifier.size(32.dp))
            Icon(painter = painterResource(Res.drawable.compose_multiplatform), contentDescription = "Voice mode", modifier = Modifier.size(32.dp))
            Icon(painter = painterResource(Res.drawable.compose_multiplatform), contentDescription = "Touch mode", modifier = Modifier.size(32.dp))
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

        Text(
            text = "• You can change modes anytime.\n• Mode buttons will appear during the test.",
            style = SightUPTheme.textStyles.caption
        )
    }

}