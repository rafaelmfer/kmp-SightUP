package com.europa.sightup.presentation.screens.test

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.coroutines.delay

@Composable
fun IndividualTestScreen(
    navController: NavController,
    test: TestResponse,
) {
    BackgroundImageScreen(navController, test)
}

@Composable
fun BackgroundImageScreen(
    navController: NavController,
    test: TestResponse,
) {
    Scaffold {
        Box(
        ) {
            CoilImage(
                imageModel = { test.images },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }
            BottomCard(navController, test, modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun BottomCard(
    navController: NavController,
    test: TestResponse,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200L)
        visible = true
    }

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 300.dp, // Se desplaza desde 300.dp hacia 0.dp
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = offsetY)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(SightUPTheme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = SightUPTheme.spacing.spacing_lg,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    start = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_base
                )
        ) {
            Text(text = test.title, style = SightUPTheme.textStyles.h1)
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            Text(text = test.description, style = SightUPTheme.textStyles.body)
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            Text("How it works")
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))

            test.howItWorks.forEach { item ->
                Text(text = "• $item", style = SightUPTheme.textStyles.caption)
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SightUPTheme.spacing.spacing_md),
                shape = SightUPTheme.shapes.small,
                onClick = { navController.navigate(TestScreens.TestExecution(test.taskId)) }
            ) {
                Text(text = "Start", style = SightUPTheme.textStyles.small)
            }
        }
    }
}