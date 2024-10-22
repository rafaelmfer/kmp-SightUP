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
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun SDSConditions(
    modifier: Modifier = Modifier,
    type: SDSConditionsEnum = SDSConditionsEnum.STRAIN,
) {
    when (type) {
        SDSConditionsEnum.STRAIN -> {
            Text(
                modifier = Modifier
                    .clip(SightUPTheme.shapes.large)
                    .background(SightUPTheme.sightUPColors.purple_100)
                    .padding(
                        horizontal = SightUPTheme.spacing.spacing_sm,
                        vertical = SightUPTheme.spacing.spacing_xs
                    )
                    .then(modifier),
                text = "Eye Strain",
                color = SightUPTheme.sightUPColors.purple_200,
                style = SightUPTheme.textStyles.caption
            )
        }

        SDSConditionsEnum.DRY -> {
            Text(
                modifier = Modifier
                    .clip(SightUPTheme.shapes.large)
                    .background(SightUPTheme.sightUPColors.purple_100)
                    .padding(
                        horizontal = SightUPTheme.spacing.spacing_sm,
                        vertical = SightUPTheme.spacing.spacing_xs
                    )
                    .then(modifier),
                text = "Dry Eyes",
                color = SightUPTheme.sightUPColors.purple_200,
                style = SightUPTheme.textStyles.caption
            )
        }

        SDSConditionsEnum.RED -> {
            Text(
                modifier = Modifier
                    .clip(SightUPTheme.shapes.large)
                    .background(SightUPTheme.sightUPColors.error_100)
                    .padding(
                        horizontal = SightUPTheme.spacing.spacing_sm,
                        vertical = SightUPTheme.spacing.spacing_xs
                    )
                    .then(modifier),
                text = "Red Eyes",
                color = SightUPTheme.sightUPColors.error_300,
                style = SightUPTheme.textStyles.caption
            )
        }

        SDSConditionsEnum.IRRITATED -> {
            Text(
                modifier = Modifier
                    .clip(SightUPTheme.shapes.large)
                    .background(SightUPTheme.sightUPColors.success_100)
                    .padding(
                        horizontal = SightUPTheme.spacing.spacing_sm,
                        vertical = SightUPTheme.spacing.spacing_xs
                    )
                    .then(modifier),
                text = "Irritated Eyes",
                color = SightUPTheme.sightUPColors.success_200,
                style = SightUPTheme.textStyles.caption
            )
        }

        SDSConditionsEnum.WATERY -> {
            Text(
                modifier = Modifier
                    .clip(SightUPTheme.shapes.large)
                    .background(SightUPTheme.sightUPColors.primary_100)
                    .padding(
                        horizontal = SightUPTheme.spacing.spacing_sm,
                        vertical = SightUPTheme.spacing.spacing_xs
                    )
                    .then(modifier),
                text = "Watery Eyes",
                color = SightUPTheme.sightUPColors.primary_300,
                style = SightUPTheme.textStyles.caption
            )
        }

        SDSConditionsEnum.ITCHY -> {
            Text(
                modifier = Modifier
                    .clip(SightUPTheme.shapes.large)
                    .background(SightUPTheme.sightUPColors.warning_100)
                    .padding(
                        horizontal = SightUPTheme.spacing.spacing_sm,
                        vertical = SightUPTheme.spacing.spacing_xs
                    )
                    .then(modifier),
                text = "Itchy Eyes",
                color = SightUPTheme.sightUPColors.warning_300,
                style = SightUPTheme.textStyles.caption
            )
        }
    }
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
            type = SDSConditionsEnum.STRAIN
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.DRY
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.RED
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.IRRITATED
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.WATERY
        )
        SDSConditions(
            modifier = Modifier,
            type = SDSConditionsEnum.ITCHY
        )
    }
}