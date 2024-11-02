package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.designsystem.components.SDSCardTestBottom
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.navigate
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun IndividualTestScreen(
    navController: NavController,
    test: TestResponse,
) {
    var isChecked by remember { mutableStateOf(true) }

    val imageString = test.imageInstruction.replace("illustrations/vision_test/", "illustrations%2Fvision_test%2F")

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SDSTopBar(
            title = "",
            iconLeftVisible = true,
            onLeftButtonClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_sm,
                )
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        CoilImage(
            imageModel = { imageString },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                requestSize = IntSize(
                    width = -1,
                    height = 130.dp.value.toInt(),
                ),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_side_margin,
                ),
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
        Spacer(Modifier.weight(ONE_FLOAT))
        SDSCardTestBottom(
            title = test.title,
            description = test.description,
            requirements = test.howItWorks,
            onClick = {
                navController.navigate(
                    route = TestScreens.TestTutorial.toString(),
                    objectToSerialize = test
                )
            },
            isChecked = isChecked,
            onCheckedChanged = { isChecked = it },
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_md,
                )
        )
    }
}