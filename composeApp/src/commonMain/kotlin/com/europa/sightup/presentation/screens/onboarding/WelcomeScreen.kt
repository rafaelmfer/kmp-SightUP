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
import com.europa.sightup.SightUPApp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_back
import sightupkmpapp.composeapp.generated.resources.welcome

@Composable
fun WelcomeScreen(navController: NavController? = null) {
    var currentStep by remember { mutableStateOf(1) }
    var bottomSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (currentStep > 1) {
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
        }
        Spacer(modifier = Modifier.weight(ONE_FLOAT))
        Image(
            painter = painterResource(Res.drawable.welcome),
            contentDescription = null,
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
                    start = SightUPTheme.spacing.spacing_side_margin,
                    top = SightUPTheme.spacing.spacing_xs,
                    end = SightUPTheme.spacing.spacing_side_margin,
                    bottom = SightUPTheme.spacing.spacing_lg,
                )
        ) {
            Text(
                text = "Welcome to SightUP",
                textAlign = TextAlign.Center,
                style = SightUPTheme.textStyles.h3
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_16))
            Text(
                text = "Do you take the first vision test?",
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_32))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SDSButton(
                    text = "Later",
                    onClick = {
                        navController?.navigate(SightUPApp)
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = ButtonStyle.OUTLINED
                )
                Spacer(modifier = Modifier.width(SightUPTheme.sizes.size_16))
                SDSButton(
                    text = "Take Vision Test",
                    onClick = {
                        navController
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    buttonStyle = ButtonStyle.PRIMARY
                )
            }
        }
    }

    // TODO: Call SetupProfile Flow BottomSheet if the user has not setup their profile
//    LoginSignUpScreen(
//        bottomSheetVisible = bottomSheetVisibility,
//        onBottomSheetVisibilityChange = {
//            bottomSheetVisibility = it
//        },
//        navController = navController
//    )
}