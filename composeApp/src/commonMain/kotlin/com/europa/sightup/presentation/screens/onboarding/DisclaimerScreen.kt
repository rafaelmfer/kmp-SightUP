package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.navigation.OnboardingScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform
import sightupkmpapp.composeapp.generated.resources.disclaimer_button
import sightupkmpapp.composeapp.generated.resources.disclaimer_content
import sightupkmpapp.composeapp.generated.resources.disclaimer_title

@Preview
@Composable
fun DisclaimerScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SightUPTheme.spacing.spacing_side_margin,
                end = SightUPTheme.spacing.spacing_side_margin,
            )
    ) {
        Spacer(modifier = Modifier.weight(ONE_FLOAT))
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(ONE_FLOAT))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = SightUPTheme.spacing.spacing_xs,
                    bottom = SightUPTheme.spacing.spacing_lg
                )
        ) {
            Text(
                text = stringResource(Res.string.disclaimer_title),
                style = SightUPTheme.textStyles.h3,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_24))
            Text(
                text = stringResource(Res.string.disclaimer_content),
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_32))
            SDSButton(
                text = stringResource(Res.string.disclaimer_button),
                onClick = {
                    navController?.navigate(OnboardingScreens.Tutorial)
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}