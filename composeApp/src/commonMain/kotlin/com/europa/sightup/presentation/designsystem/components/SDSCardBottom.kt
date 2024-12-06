package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.SightUPLineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun SDSCardTestBottom(
    title: String = "Visual Acuity",
    description: String = " ",
    requirements: List<String> = listOf(
        "• ",
        "• "
    ),
    buttonText: String = "Start",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        Text(
            text = title,
            style = SightUPTheme.textStyles.h4,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        Text(
            text = description,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            lineHeight = SightUPLineHeight.default.lineHeight_xs
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        Text(
            text = "Test Overview",
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        Column(
            verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs),
        ) {
            for (requirement in requirements) {
                Text(
                    text = requirement,
                    style = SightUPTheme.textStyles.body2.copy(lineHeight = SightUPLineHeight.default.lineHeight_2xs),
                    color = SightUPTheme.sightUPColors.text_primary,
                )
            }
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
        SDSSwitchBoxContainer(
            text = "audio support",
            isChecked = isChecked,
            onCheckedChanged = onCheckedChanged,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))
        SDSButton(
            text = buttonText,
            onClick = onClick,
            buttonStyle = ButtonStyle.PRIMARY,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SDSCardExerciseBottom(
    category: String = "",
    title: String = "Circular Motion",
    motivation: String = "Let’s take a quick break and give your eyes some gentle movement!",
    duration: Int = 0,
    buttonText: String = "Start",
    onClick: () -> Unit = {},
    showGuidance: Boolean = true,
    isChecked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        if (category.isNotEmpty() && duration > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = category,
                        style = SightUPTheme.textStyles.subtitle,
                        color = SightUPTheme.sightUPColors.primary_700,
                    )
                    Spacer(Modifier.height(SightUPTheme.sizes.size_4))
                    Text(
                        text = title,
                        style = SightUPTheme.textStyles.h4,
                        color = SightUPTheme.sightUPColors.text_primary,
                    )
                }
                SDSBadgeTime(timeSeconds = duration)
            }
        } else {
            Text(
                text = title,
                style = SightUPTheme.textStyles.h5,
                color = SightUPTheme.sightUPColors.text_primary,
            )
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_12))
        Text(
            text = motivation,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        if (showGuidance) {
            SDSSwitchBoxContainer(
                text = "music and voice guidance",
                isChecked = isChecked,
                onCheckedChanged = onCheckedChanged,
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        }
        SDSButton(
            text = buttonText,
            onClick = onClick,
            buttonStyle = ButtonStyle.PRIMARY,
            modifier = Modifier.fillMaxWidth()
        )
    }
}