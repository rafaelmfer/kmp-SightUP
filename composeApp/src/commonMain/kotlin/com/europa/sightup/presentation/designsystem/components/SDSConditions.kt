package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.dry_eyes
import sightupkmpapp.composeapp.generated.resources.eye_strain
import sightupkmpapp.composeapp.generated.resources.irritated_eyes
import sightupkmpapp.composeapp.generated.resources.itchy_eyes
import sightupkmpapp.composeapp.generated.resources.red_eyes
import sightupkmpapp.composeapp.generated.resources.watery_eyes

@Preview
@Composable
fun SDSConditions(
    type: SDSConditionsEnum = SDSConditionsEnum.EYE_STRAIN,
    modifier: Modifier = Modifier,
) {
    var backgroundColor = SightUPTheme.sightUPColors.neutral_0
    var text = ""
    var textColor = SightUPTheme.sightUPColors.neutral_0

    when (type) {
        SDSConditionsEnum.EYE_STRAIN -> {
            backgroundColor = SightUPTheme.sightUPColors.background_purple
            text = stringResource(Res.string.eye_strain)
            textColor = SightUPTheme.sightUPColors.purple_300
        }

        SDSConditionsEnum.DRY_EYES -> {
            backgroundColor = SightUPTheme.sightUPColors.background_warning
            text = stringResource(Res.string.dry_eyes)
            textColor = SightUPTheme.sightUPColors.warning_300
        }

        SDSConditionsEnum.RED_EYES -> {
            backgroundColor = SightUPTheme.sightUPColors.background_error
            text = stringResource(Res.string.red_eyes)
            textColor = SightUPTheme.sightUPColors.error_300
        }

        SDSConditionsEnum.IRRITATED_EYES -> {
            backgroundColor = SightUPTheme.sightUPColors.background_success
            text = stringResource(Res.string.irritated_eyes)
            textColor = SightUPTheme.sightUPColors.success_300
        }

        SDSConditionsEnum.WATERY_EYES -> {
            backgroundColor = SightUPTheme.sightUPColors.background_info
            text = stringResource(Res.string.watery_eyes)
            textColor = SightUPTheme.sightUPColors.info_300
        }

        SDSConditionsEnum.ITCHY_EYES -> {
            backgroundColor = SightUPTheme.sightUPColors.background_orange
            text = stringResource(Res.string.itchy_eyes)
            textColor = SightUPTheme.sightUPColors.orange_300
        }

        else -> {}
    }

    Text(
        modifier = Modifier
            .clip(SightUPTheme.shapes.large)
            .background(backgroundColor)
            .padding(
                horizontal = SightUPTheme.spacing.spacing_sm,
                vertical = SightUPTheme.spacing.spacing_xs
            )
            .then(modifier),
        text = text,
        color = textColor,
        style = SightUPTheme.textStyles.caption.copy(
            fontWeight = FontWeight.Bold
        )
    )
}

@Preview
@Composable
fun SDSConditionsScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(
            space = SightUPTheme.spacing.spacing_base,
            alignment = Alignment.CenterVertically
        )
    ) {
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.EYE_STRAIN
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.DRY_EYES
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.RED_EYES
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.IRRITATED_EYES
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.WATERY_EYES
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.ITCHY_EYES
        )
    }
}