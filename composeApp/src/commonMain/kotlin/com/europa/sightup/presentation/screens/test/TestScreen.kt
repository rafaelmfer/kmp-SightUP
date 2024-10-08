package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.utils.UIState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TestScreenWithState(
    navController: NavController
) {
    MaterialTheme {
        val kVaultStorage = koinInject<KVaultStorage>()

        val viewModel = koinViewModel<TestViewModel>()
        LaunchedEffect(Unit) {
            viewModel.getTests()
        }
        val state by viewModel.test.collectAsStateWithLifecycle()

        val token = "Token dkfjhksjdhfjdsfjksdbkjfbskjdbfnds"
        kVaultStorage.set("token", token)
        println("Token saved: $token")

        TestScreen(navController, state, kVaultStorage = kVaultStorage)
    }
}

@Composable
fun TestScreen(
    navController: NavController,
    state: UIState<List<TestResponse>>,
    kVaultStorage: KVaultStorage? = null
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                    )
                }
            }
        }
    }
}

@Composable
fun TestList(tests: List<TestResponse>, modifier: Modifier = Modifier) {
    SectionHeaderWithIcon(title = "Vision Tests")
    LazyColumn(modifier = modifier) {
        items(tests) { test ->
            TestItemCard(test = test)
        }
    }
}

@Composable
fun SectionHeaderWithIcon(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Get more information",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun TestItemCard(test: TestResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp,bottom = 8.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
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
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = test.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Check List for test",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = test.checkList[0],
                    fontSize = 11.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = test.checkList[1],
                    fontSize = 11.sp
                )
            }
        }
    }
}

