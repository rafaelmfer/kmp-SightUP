package com.europa.sightup.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.login_trigger


@Composable
fun JoinInBlockScreen(
    navController: NavHostController,
) {
    SightUPTheme {
        JoinInBlockLoadScreen()
    }
}


@Composable
fun JoinInBlockLoadScreen(
    onLoginClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.login_trigger),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )
        Text(
            text = "Did you enjoy this exercise?",
            style = SightUPTheme.textStyles.h4,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_4))
        Text(
            text = "Sign-up now and enjoy all the features.",
            style = SightUPTheme.textStyles.subtitle,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_32))
        val items = listOf(
            "Four exercises designed help your eyes:",
            "• Relieve dryness and eye strain.",
            "• Enhance visual perception and focus.",
            "• Improve color differentiation and stretch your eye muscles."
        )
        for (item in items) {
            Text(
                text = item,
                style = SightUPTheme.textStyles.body,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_8))
        Text(
            text = "Plus, you'll find vision tests to assess conditions like myopia and astigmatism, and a secure space to save your vision history.",
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        SDSButton(
            text = "Login/Signup",
            onClick = {
                onLoginClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
