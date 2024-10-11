package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.components.TitleBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.UIState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.koin.compose.viewmodel.koinViewModel

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
fun TestScreen(
    navController: NavController,
    state: UIState<List<TestResponse>>,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (state) {
                is UIState.InitialState -> {}
                is UIState.Loading -> {
                    CircularProgressIndicator()
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
fun TestList(tests: List<TestResponse>, modifier: Modifier = Modifier, navController: NavController) {
    TitleBar(
        title="Vision Tests",
        rightIcon = Icons.Default.Info,
        rightButton = true,
        modifier = Modifier.padding(top= SightUPTheme.spacing.spacing_md ,bottom = SightUPTheme.spacing.spacing_md))

    LazyColumn(modifier = modifier) {
        items(tests) { test ->
            TestItemCard(navController = navController, test = test)
        }
    }
}

@Composable
fun TestItemCard(test: TestResponse, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SightUPTheme.spacing.spacing_base)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable {
                navController.navigate(TestScreens.TestIndividual(test.taskId))
            }

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CoilImage(
                imageModel = { test.images },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(131.dp),

                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                },

                failure = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Image failed to load")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = test.title,
                style = SightUPTheme.textStyles.large,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = test.shortDescription,
                style = SightUPTheme.textStyles.small,
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Check List for test",
                style = SightUPTheme.textStyles.small,
            )
            Spacer(modifier = Modifier.height(8.dp))

            test.checkList.forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item,
                        style = SightUPTheme.textStyles.caption,
                    )
                }
            }
        }
    }
}

