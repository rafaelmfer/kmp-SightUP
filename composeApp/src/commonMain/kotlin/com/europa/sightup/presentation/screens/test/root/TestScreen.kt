package com.europa.sightup.presentation.screens.test.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSCardTest
import com.europa.sightup.presentation.designsystem.components.SDSDialog
import com.europa.sightup.presentation.designsystem.components.SDSSwitchBoxContainer
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.test.TutorialBox
import com.europa.sightup.test.state.rememberTutorialBoxState
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.navigate
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.guide_book

@Composable
fun TestScreenWithState(
    navController: NavController,
) {
    val viewModel = koinViewModel<TestViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getTests()
    }
    val state by viewModel.test.collectAsStateWithLifecycle()

    TestScreen(navController, state)
}

@Composable
private fun TestScreen(
    navController: NavController,
    state: UIState<List<TestResponse>>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (state) {
            is UIState.InitialState -> {}
            is UIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${state.message}")
                }
            }

            is UIState.Success -> {
                val tests = state.data
                TestList(
                    tests = tests,
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun TestList(tests: List<TestResponse>, modifier: Modifier = Modifier, navController: NavController) {
    var showFirstDialog by remember { mutableStateOf(false) }

    var showTutorial by remember { mutableStateOf(false) }
    val state = rememberTutorialBoxState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.currentTargetIndex) {
        listState.animateScrollToItem(state.currentTargetIndex)
    }

    TutorialBox(
        modifier = Modifier.fillMaxSize(),
        state = state,
        showTutorial = showTutorial,
        onTutorialCompleted = {
            showTutorial = false
        },
        onTutorialIndexChanged = {
            coroutineScope.launch {
                listState.animateScrollToItem(it + 1)
            }
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SightUPTheme.sizes.size_48)
                    .padding(horizontal = SightUPTheme.spacing.spacing_xs),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(48.dp))

                Text(
                    modifier = Modifier
                        .padding(SightUPTheme.spacing.spacing_2xs)
                        .weight(1f),
                    text = "Vision Tests",
                    style = SightUPTheme.textStyles.subtitle,
                    textAlign = TextAlign.Center,
                )

                IconButton(
                    modifier = Modifier
                        .markForTutorial(
                            index = 4,
                            cornerRadius = SightUPBorder.Radius.full,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SightUPTheme.spacing.spacing_side_margin)
                                        .clip(SightUPTheme.shapes.small)
                                        .background(
                                            color = SightUPTheme.sightUPColors.background_default,
                                            shape = SightUPTheme.shapes.small
                                        )
                                        .padding(SightUPTheme.spacing.spacing_md)
                                ) {
                                    Text(
                                        text = "Vision Test Tutorial",
                                        style = SightUPTheme.textStyles.h4,
                                        color = SightUPTheme.sightUPColors.text_primary
                                    )
                                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                    Text(
                                        text = "Click tutorial icon anytime for instruction.",
                                        style = SightUPTheme.textStyles.body,
                                        color = SightUPTheme.sightUPColors.text_primary
                                    )
                                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                                    SDSButton(
                                        modifier = Modifier.align(Alignment.End),
                                        text = "Close",
                                        onClick = {
                                            state.currentTargetIndex += 2
                                        }
                                    )
                                }

                            }
                        ),
                    onClick = {
                        showFirstDialog = true
                    }
                ) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(Res.drawable.guide_book),
                        contentDescription = "Guide Book"
                    )
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
                contentPadding = PaddingValues(
                    top = SightUPTheme.spacing.spacing_base,
                    bottom = SightUPTheme.spacing.spacing_base
                ),
                verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base)
            ) {
                itemsIndexed(items = tests, key = { _, test -> test.id }) { index, test ->
                    SDSCardTest(
                        navigateToTest = {
                            navController.navigate(
                                route = TestScreens.TestIndividual.toString(),
                                objectToSerialize = test
                            )
                        },
                        test = test,
                        modifier = Modifier
                            .markForTutorial(
                                index = index,
                                cornerRadius = SightUPBorder.Radius.lg,
                                content = {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(SightUPTheme.spacing.spacing_side_margin)
                                            .clip(SightUPTheme.shapes.small)
                                            .background(
                                                color = SightUPTheme.sightUPColors.background_default,
                                                shape = SightUPTheme.shapes.small
                                            )
                                            .padding(SightUPTheme.spacing.spacing_md)
                                    ) {
                                        if (index == 0) {
                                            Text(
                                                text = "Visual Acuity Test",
                                                style = SightUPTheme.textStyles.h4,
                                                color = SightUPTheme.sightUPColors.text_primary
                                            )
                                            Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                            Text(
                                                text = "Visual Acuity test measures the sharpness of your vision.  Results are shown as 20/20 or 20/200, comparing your vision to normal eyesight in a North America measurement.",
                                                style = SightUPTheme.textStyles.body,
                                                color = SightUPTheme.sightUPColors.text_primary
                                            )
                                            Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                                            SDSButton(
                                                modifier = Modifier.align(Alignment.End),
                                                text = "Next",
                                                onClick = {
                                                    state.currentTargetIndex += 1
                                                }
                                            )
                                        } else {
                                            Text(
                                                text = "Astigmatism Test",
                                                style = SightUPTheme.textStyles.h4,
                                                color = SightUPTheme.sightUPColors.text_primary
                                            )
                                            Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                            Text(
                                                text = "Astigmatism test measures uneven curvature in the cornea or lens that can distort your vision. It indicates the location of astigmatism. The number ranges from 1 to 180. Axis does not indicate the strength of an eyeglasses prescription.",
                                                style = SightUPTheme.textStyles.body,
                                                color = SightUPTheme.sightUPColors.text_primary
                                            )
                                            Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                                            SDSButton(
                                                modifier = Modifier.align(Alignment.End),
                                                text = "Next",
                                                onClick = {
                                                    state.currentTargetIndex += 1
                                                }
                                            )
                                        }
                                    }
                                }
                            )
                    )
                }
            }
        }

        SDSDialog(
            showDialog = showFirstDialog,
            onDismiss = { showFirstDialog = it },
            title = "About vision test",
            onClose = null,
            content = { _ ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SightUPTheme.spacing.spacing_md)
                ) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                    Text(
                        text = "SightUP vision test helps you understand your eye health and whether you need a professional exam.",
                        color = SightUPTheme.sightUPColors.text_primary,
                        style = SightUPTheme.textStyles.body
                    )
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                }
            },
            onPrimaryClick = {
                showTutorial = true
                showFirstDialog = false
            },
            buttonPrimaryText = "Next",
        )


        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val screenHeight = maxHeight

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = screenHeight * 0.2f)
                    .markForTutorial(
                        index = 2,
                        cornerRadius = SightUPBorder.Radius.sm,
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SightUPTheme.spacing.spacing_side_margin)
                                    .clip(SightUPTheme.shapes.small)
                                    .background(
                                        color = SightUPTheme.sightUPColors.background_default,
                                        shape = SightUPTheme.shapes.small
                                    )
                                    .padding(SightUPTheme.spacing.spacing_md)
                            ) {
                                Text(
                                    text = "Which test is right for you?",
                                    style = SightUPTheme.textStyles.h4,
                                    color = SightUPTheme.sightUPColors.text_primary
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "Visual Acuity test helps with:",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.body
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "• Routine eye exam",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.caption
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "• Driver license renewal",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.caption
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "Astigmatism test helps with:",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.body
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "• Blurred and Wavy vision",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.caption
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "• Halos and Glare",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.caption
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "Both tests help with:",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.body
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "• Comprehensive evaluation",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.caption
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                                SDSButton(
                                    modifier = Modifier.align(Alignment.End),
                                    text = "Next",
                                    onClick = {
                                        state.currentTargetIndex += 1
                                    }
                                )
                            }
                        }
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = screenHeight * 0.3f)
                    .markForTutorial(
                        index = 3,
                        cornerRadius = SightUPBorder.Radius.sm,
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SightUPTheme.spacing.spacing_side_margin)
                                    .clip(SightUPTheme.shapes.small)
                                    .background(
                                        color = SightUPTheme.sightUPColors.background_default,
                                        shape = SightUPTheme.shapes.small
                                    )
                                    .padding(SightUPTheme.spacing.spacing_md),
                            ) {
                                Text(
                                    text = "Audio Support",
                                    style = SightUPTheme.textStyles.h4,
                                    color = SightUPTheme.sightUPColors.text_primary
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                Text(
                                    text = "Turn on the audio guide if you need spoken instructions during the test.",
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    style = SightUPTheme.textStyles.body
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
                                SDSSwitchBoxContainer(
                                    text = "Audio Support",
                                    isChecked = false,
                                    onCheckedChanged = { }
                                )
                                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                                SDSButton(
                                    modifier = Modifier.align(Alignment.End),
                                    text = "Next",
                                    onClick = {
                                        state.currentTargetIndex += 1
                                    }
                                )
                            }
                        }
                    )
            ) {

            }
        }


    }
}
