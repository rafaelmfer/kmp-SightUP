package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.network.NetworkClient.JWT_TOKEN
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.navigation.Home
import com.europa.sightup.presentation.navigation.OnboardingScreens.WelcomeScreen
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.USER_INFO
import com.europa.sightup.utils.slideInFromLeft
import com.europa.sightup.utils.slideInFromRight
import com.europa.sightup.utils.slideOutToLeft
import com.europa.sightup.utils.slideOutToRight
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
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
    val kVault = koinInject<KVaultStorage>()

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

    var navigateTo by remember { mutableStateOf<Any?>(null) }
    val fadeOutDuration by remember { mutableStateOf(500) }
    val screenAlpha by animateFloatAsState(
        targetValue = if (navigateTo == null) 1f else 0f,
        animationSpec = tween(durationMillis = fadeOutDuration)
    )

    LaunchedEffect(navigateTo) {
        if (navigateTo != null) {
            delay((fadeOutDuration / 1.25).toLong())
            navigateTo?.let { destination ->
                //TODO: verify if the user has profile to send that info to WelcomeScreen to open BottomSheets
                navController?.navigate(destination) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = screenAlpha)
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
            )
        }
        Spacer(modifier = Modifier.weight(ONE_FLOAT))

        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInFromRight() togetherWith slideOutToLeft()
                } else {
                    slideInFromLeft() togetherWith slideOutToRight()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { step ->

            Image(
                painter = images[step - 1],
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
                    .fillMaxWidth()
            )
        }

        Spacer(Modifier.weight(ONE_FLOAT))

        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInFromRight() togetherWith slideOutToLeft()
                } else {
                    slideInFromLeft() togetherWith slideOutToRight()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { step ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            ) {
                Text(
                    text = titles[step - 1],
                    textAlign = TextAlign.Center,
                    style = SightUPTheme.textStyles.h3
                )
                Spacer(Modifier.height(SightUPTheme.sizes.size_24))
                Text(
                    text = descriptions[step - 1],
                    textAlign = TextAlign.Center,
                    style = SightUPTheme.textStyles.body
                )
            }
        }

        Spacer(Modifier.height(SightUPTheme.sizes.size_32))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterHorizontally),
        ) {
            SDSButton(
                text = if (currentStep >= 4) {
                    stringResource(Res.string.tutorial_secondary_button_last_step)
                } else {
                    stringResource(Res.string.tutorial_secondary_button)
                },
                onClick = {
                    if (currentStep < 4) {
                        currentStep = 4
                    } else {
                        kVault.run {
                            remove(USER_INFO)
                            remove(JWT_TOKEN)
                        }
                        navigateTo = Home
                    }
                },
                modifier = Modifier.weight(ONE_FLOAT),
                contentColor = if (currentStep >= 4) SightUPTheme.sightUPColors.primary_600 else SightUPTheme.sightUPColors.text_primary,
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
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
    }

    LoginSignUpScreen(
        bottomSheetVisible = bottomSheetVisibility,
        onBottomSheetVisibilityChange = {
            bottomSheetVisibility = it
        },
        onSuccessfulLogin = {
            navigateTo = WelcomeScreen
        },
        navController = navController
    )
}