package com.europa.sightup.presentation.screens.test.root

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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.navigate
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
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
private fun TestList(tests: List<TestResponse>, modifier: Modifier = Modifier, navController: NavController) {
    SDSTopBar(
        title = "Vision Tests",
        iconRight = Res.drawable.guide_book,
        iconRightVisible = true,
    )

    LazyColumn(modifier = Modifier.padding(vertical = SightUPTheme.spacing.spacing_xs)) {
        items(tests) { test ->
            TestItemCard(navController = navController, test = test)
        }
    }
}

@Composable
private fun TestItemCard(test: TestResponse, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SightUPTheme.spacing.spacing_xs)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable {
                navController.navigate(
                    route = TestScreens.TestIndividual.toString(),
                    objectToSerialize = test
                )
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
                style = SightUPTheme.textStyles.h4,
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Checklist for test",
                style = SightUPTheme.textStyles.button,
            )
            Spacer(modifier = Modifier.height(8.dp))

            test.checkList.forEach { item ->
                Row {
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
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

