package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StepScreenWithAnimation(
    animationPath: String,
    instructionText: String,
    speed: Float = 1f,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(animationPath).decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = speed,
        iterations = Compottie.IterateForever
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize().then(modifier),
    ) {

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(316.dp),
            contentDescription = "Animation"
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_lg))

        // Instructional Card
        CardWithIcon(
            text = instructionText
        )
    }
}

// TODO: Delete this after adding the animations
@Composable
fun StepScreenWithImage(
    image: DrawableResource,
    instructionText: String,
    speed: Float = 1f,
    modifier: Modifier = Modifier,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize().then(modifier),
    ) {

        Image(
            painter = painterResource(image),
            modifier = Modifier
                .fillMaxWidth()
                .height(316.dp),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_lg))

        // Instructional Card
        CardWithIcon(
            text = instructionText
        )
    }
}
