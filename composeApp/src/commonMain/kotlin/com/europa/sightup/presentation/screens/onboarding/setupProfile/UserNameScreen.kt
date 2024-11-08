package com.europa.sightup.presentation.screens.onboarding.setupProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.screens.onboarding.WelcomeViewModel
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.profile_note
import sightupkmpapp.composeapp.generated.resources.profile_username_content
import sightupkmpapp.composeapp.generated.resources.profile_username_hint
import sightupkmpapp.composeapp.generated.resources.profile_username_title

@Composable
fun UserNameScreen(
    viewModel: WelcomeViewModel,
) {
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_lg))
        Text(
            text = stringResource(Res.string.profile_username_title),
            style = SightUPTheme.textStyles.h5
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.profile_username_content),
            style = SightUPTheme.textStyles.body
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.profile_note),
            style = SightUPTheme.textStyles.caption
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_lg))
        // TODO update the name
        SDSInput(
            value = username,
            onValueChange = { username = it },
            label = "Username",
            hint = stringResource(Res.string.profile_username_hint),
            fullWidth = true
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_lg))
        SDSButton(
            text = "Next",
            onClick = {
                viewModel.updateProfileData(username = username)
                viewModel.outerStep++
                      },
            enabled = username.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            buttonStyle = ButtonStyle.PRIMARY
        )
    }
}
