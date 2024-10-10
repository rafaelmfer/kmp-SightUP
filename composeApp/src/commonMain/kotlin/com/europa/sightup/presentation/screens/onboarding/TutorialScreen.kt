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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.compose_multiplatform
import sightupkmpapp.composeapp.generated.resources.tutorial_four_content
import sightupkmpapp.composeapp.generated.resources.tutorial_four_title
import sightupkmpapp.composeapp.generated.resources.tutorial_one_content
import sightupkmpapp.composeapp.generated.resources.tutorial_one_title
import sightupkmpapp.composeapp.generated.resources.tutorial_primary_button
import sightupkmpapp.composeapp.generated.resources.tutorial_primary_button_last_step
import sightupkmpapp.composeapp.generated.resources.tutorial_secondary_button
import sightupkmpapp.composeapp.generated.resources.tutorial_secondary_button_last_step
import sightupkmpapp.composeapp.generated.resources.tutorial_three_content
import sightupkmpapp.composeapp.generated.resources.tutorial_three_title
import sightupkmpapp.composeapp.generated.resources.tutorial_two_content
import sightupkmpapp.composeapp.generated.resources.tutorial_two_title

@Composable
fun TutorialScreen(navController: NavController? = null) {
    var currentStep by remember { mutableStateOf(1) }

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
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Gray
            )
        }
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
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_24))
            Text(
                text = descriptions[currentStep - 1],
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_32))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentStep >= 4)
                    OutlinedButton(
                        onClick = {
                            showToast(
                                message = "Not implemented yet",
                                bottomPadding = 40,
                            )
                            // TODO: Navigate to start as guest user
//                            navController?.navigate()
                        },
                        modifier = Modifier.weight(ONE_FLOAT),
                        shape = SightUPTheme.shapes.small
                    ) {
                        Text(
                            text =
                            if (currentStep >= 4) {
                                stringResource(Res.string.tutorial_secondary_button_last_step)
                            } else {
                                stringResource(Res.string.tutorial_secondary_button)
                            }
                        )
                    }
                else {
                    TextButton(
                        onClick = {
                            showToast(
                                message = "Not implemented yet",
                                bottomPadding = 40,
                            )
                            // TODO: Navigate to skip all tutorial screens
//                            navController?.navigate()
                        },
                        modifier = Modifier.weight(ONE_FLOAT),
                        shape = SightUPTheme.shapes.small
                    ) {
                        Text(text = stringResource(Res.string.tutorial_secondary_button))
                    }
                }
                Spacer(modifier = Modifier.width(SightUPTheme.sizes.size_16))
                Button(
                    onClick = {
                        if (currentStep < 4) {
                            currentStep++
                        } else {
                            showToast(
                                message = "Not implemented yet",
                                bottomPadding = 40,
                            )
                        }
                    },
                    modifier = Modifier.weight(ONE_FLOAT),
                    shape = SightUPTheme.shapes.small
                ) {
                    Text(
                        text = if (currentStep >= 4) {
                            stringResource(Res.string.tutorial_primary_button_last_step)
                        } else {
                            stringResource(Res.string.tutorial_primary_button)
                        }
                    )
                }
            }
        }
    }
}