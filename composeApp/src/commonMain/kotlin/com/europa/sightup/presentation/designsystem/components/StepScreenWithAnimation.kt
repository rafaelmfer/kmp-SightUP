package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sightupkmpapp.composeapp.generated.resources.Res


@OptIn(ExperimentalResourceApi::class)
@Composable
fun StepScreenWithAnimation(
    animationPath: String,
    instructionText: String,
    speed: Float = 1f,
    modifier: Modifier = Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color = SightUPTheme.sightUPColors.neutral_0
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
                .height(316.dp)
                .background(backgroundColor),
            contentDescription = "Lottie animation"
        )

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_lg))

        // Instructional Card
        CardWithIcon(
            text = instructionText
        )
    }
}
