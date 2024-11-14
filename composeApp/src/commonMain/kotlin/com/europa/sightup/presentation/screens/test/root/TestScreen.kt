package com.europa.sightup.presentation.screens.test.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.SDSCardTest
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.navigate
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
    Scaffold {
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
                            .fillMaxSize()
                            .padding(it),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UIState.Error -> {
                    Text(text = "Error: ${state.message}")
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
}

@Composable
private fun TestList(tests: List<TestResponse>, modifier: Modifier = Modifier, navController: NavController) {
    SDSTopBar(
        modifier = modifier.padding(horizontal = SightUPTheme.spacing.spacing_xs),
        title = "Vision Tests",
        iconRight = Res.drawable.guide_book,
        iconRightVisible = true,
    )

    LazyColumn(
        modifier = Modifier
            .padding(vertical = SightUPTheme.spacing.spacing_xs, horizontal = SightUPTheme.spacing.spacing_side_margin),

        ) {
        items(tests) { test ->
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))
            SDSCardTest(
                navigateToTest = {
                    navController.navigate(
                        route = TestScreens.TestIndividual.toString(),
                        objectToSerialize = test
                    )
                }, test = test
            )
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))
        }
    }
}

