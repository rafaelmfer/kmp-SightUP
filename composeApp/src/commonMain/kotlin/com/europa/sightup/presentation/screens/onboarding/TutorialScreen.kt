package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.navigation.WelcomeScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_back
import sightupkmpapp.composeapp.generated.resources.tutorial_four
import sightupkmpapp.composeapp.generated.resources.tutorial_four_content
import sightupkmpapp.composeapp.generated.resources.tutorial_four_title
import sightupkmpapp.composeapp.generated.resources.tutorial_one
import sightupkmpapp.composeapp.generated.resources.tutorial_one_content
import sightupkmpapp.composeapp.generated.resources.tutorial_one_title
import sightupkmpapp.composeapp.generated.resources.tutorial_primary_button
import sightupkmpapp.composeapp.generated.resources.tutorial_primary_button_last_step
import sightupkmpapp.composeapp.generated.resources.tutorial_secondary_button
import sightupkmpapp.composeapp.generated.resources.tutorial_secondary_button_last_step
import sightupkmpapp.composeapp.generated.resources.tutorial_three
import sightupkmpapp.composeapp.generated.resources.tutorial_three_content
import sightupkmpapp.composeapp.generated.resources.tutorial_three_title
import sightupkmpapp.composeapp.generated.resources.tutorial_two
import sightupkmpapp.composeapp.generated.resources.tutorial_two_content
import sightupkmpapp.composeapp.generated.resources.tutorial_two_title

@Composable
fun TutorialScreen(navController: NavController? = null) {
    var currentStep by remember { mutableStateOf(1) }
    var bottomSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }

    val images = listOf(
        painterResource(Res.drawable.tutorial_one),
        painterResource(Res.drawable.tutorial_two),
        painterResource(Res.drawable.tutorial_three),
        painterResource(Res.drawable.tutorial_four),
    )

    val titles = listOf(
        stringResource(Res.string.tutorial_one_title),
        stringResource(Res.string.tutorial_two_title),
        stringResource(Res.string.tutorial_three_title),
        stringResource(Res.string.tutorial_four_title)
    )

    val descriptions = listOf(
        stringResource(Res.string.tutorial_one_content),
        stringResource(Res.string.tutorial_two_content),
        stringResource(Res.string.tutorial_three_content),
        stringResource(Res.string.tutorial_four_content)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            onClick = {
                if (currentStep > 1) {
                    currentStep--
                } else {
                    navController?.popBackStack()
                }
            },
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_xs,
                    top = SightUPTheme.spacing.spacing_xs
                )
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = "Back",
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.weight(ONE_FLOAT))
        Image(
            painter = images[currentStep - 1],
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SightUPTheme.spacing.spacing_side_margin,
                    top = SightUPTheme.spacing.spacing_xs,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_lg,
                )
        ) {
            Text(
                text = titles[currentStep - 1],
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.h3
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_24))
            Text(
                text = descriptions[currentStep - 1],
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_32))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
            ) {
                SDSButton(
                    text = if (currentStep >= 4) {
                        stringResource(Res.string.tutorial_secondary_button_last_step)
                    } else {
                        stringResource(Res.string.tutorial_secondary_button)
                    },
                    onClick = {
                        navController?.navigate(WelcomeScreen)
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = if (currentStep >= 4) ButtonStyle.OUTLINED else ButtonStyle.TEXT
                )
                Spacer(Modifier.width(SightUPTheme.sizes.size_16))
                SDSButton(
                    text = if (currentStep >= 4) {
                        stringResource(Res.string.tutorial_primary_button_last_step)
                    } else {
                        stringResource(Res.string.tutorial_primary_button, currentStep.toString())
                    },
                    onClick = {
                        if (currentStep < 4) {
                            currentStep++
                        } else {
                            bottomSheetVisibility = BottomSheetEnum.SHOW
                        }
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = ButtonStyle.PRIMARY
                )
            }
        }
    }

    LoginSignUpScreen(
        bottomSheetVisible = bottomSheetVisibility,
        onBottomSheetVisibilityChange = {
            bottomSheetVisibility = it
        },
        onSuccessfulLogin = {
            //TODO: verify if the user has profile to send that info to WelcomeScreen to open BottomSheets
            navController?.navigate(
                WelcomeScreen
            ) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        navController = navController
    )
}