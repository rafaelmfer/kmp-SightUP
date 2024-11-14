package com.europa.sightup.presentation.screens.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.platformspecific.audioplayer.CMPAudioPlayer
import com.europa.sightup.platformspecific.getLocalFilePathFor
import com.europa.sightup.presentation.designsystem.components.SDSCardTestBottom
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.screens.test.active.ActiveTest
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
    var isAudioPaused by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val imageString = test.imageInstruction.replace("illustrations/vision_test/", "illustrations%2Fvision_test%2F")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SDSTopBar(
            title = "",
            iconLeftVisible = true,
            onLeftButtonClick = {
                navController.popBackStack<TestScreens.TestRoot>(inclusive = false)
            },
            modifier = Modifier
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_xs,
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
                    height = 90.dp.value.toInt(),
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
            isChecked = isAudioPaused,
            onCheckedChanged = { checked -> isAudioPaused = checked },
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_md,
                )
        )

        CMPAudioPlayer(
            modifier = Modifier.height(0.dp),
            url = if (test.title.equals(ActiveTest.Astigmatism.name, ignoreCase = true)) {
                getLocalFilePathFor(ActiveTest.Astigmatism.startAudio)
            } else {
                getLocalFilePathFor(ActiveTest.VisualAcuity.startAudio)

            },
            isPause = !isAudioPaused,
            totalTime = { },
            currentTime = { },
            isSliding = false,
            sliderTime = null,
            isRepeat = true,
            loadingState = { },
            didEndAudio = { }
        )
    }
}