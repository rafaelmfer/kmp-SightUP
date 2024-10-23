package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.lineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.navigate
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun IndividualTestScreen(
    navController: NavController,
    test: TestResponse,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        SDSTopBar(
            title = "",
            iconLeftVisible = true,
            onLeftButtonClick = { navController.popBackStack() }
        )

        CoilImage(
            imageModel = { test.images },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp)).weight(.8f)
        )

        BottomCard(navController, test = test, modifier = Modifier.weight(1f))
    }
}

@Composable
fun BottomCard(
    navController: NavController,
    test: TestResponse,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
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
            Text(text = test.title, style = SightUPTheme.textStyles.h2)
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            Text(text = test.description, style = SightUPTheme.textStyles.body, lineHeight = SightUPTheme.lineHeight.lineHeight_2xs)
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_base))

            Text("Test Overview")
            Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_xs))

            test.howItWorks.forEach { item ->
                Text(text = "• $item", style = SightUPTheme.textStyles.caption, lineHeight = SightUPTheme.lineHeight.lineHeight_2xs)
            }

            SDSButton(
                text= "Start",
                onClick = {
                    navController.navigate(
                        route = TestScreens.TestTutorial.toString(),
                        objectToSerialize = test
                    )},
                modifier = Modifier.fillMaxWidth().padding(top = SightUPTheme.spacing.spacing_md),
                textStyle = SightUPTheme.textStyles.button,
            )
        }
    }
}