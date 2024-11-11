package com.europa.sightup.presentation.screens.home.checkinbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.color.SightUPContextColor
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.clickableWithRipple
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.complete

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CauseScreen(
    title: String = "What are the cause(s)?",
    subtitle: String = "Select all that applies",
    onClickNext: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val causes = listOf(
        "Smoke",
        "Age",
        "Long distance drive",
        "Bad sleeping quality",
        "Poor Lighting",
        "Skincare",
        "Fatigue",
        "Cosmetics",
        "New environment",
        "Contact lenses",
        "Pollen",
        "Medication",
        "Infection",
        "New eyewear",
        "Long screen time",
        "Outdated eyewear",
        "Foreign bodies"
    )

    val selectedCauses = remember { mutableStateListOf<String>() }
    val backgroundUnselected by remember { mutableStateOf(SightUPContextColor.background_default) }
    val backgroundSelected by remember { mutableStateOf(SightUPContextColor.background_activate) }

    fun toggleCause(cause: String, isSelected: Boolean) {
        if (isSelected) {
            selectedCauses.add(cause)
        } else {
            selectedCauses.remove(cause)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        val (topColumn, middleColumn, button) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                style = SightUPTheme.textStyles.h5
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_8))
            Text(
                text = subtitle,
                modifier = Modifier.fillMaxWidth(),
                style = SightUPTheme.textStyles.body
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(middleColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topColumn.bottom, margin = 32.dp)
                    bottom.linkTo(button.top, margin = 32.dp)
                },
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm),
            verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm)
        ) {
            causes.forEach { cause ->
                CauseItem(
                    cause = cause,
                    isSelected = selectedCauses.contains(cause),
                    onToggle = { isSelected ->
                        toggleCause(cause, isSelected)
                    },
                    backgroundUnselected = backgroundUnselected,
                    backgroundSelected = backgroundSelected
                )
            }
        }

        SDSButton(
            text = stringResource(Res.string.complete),
            onClick = {
                onClickNext(selectedCauses)
            },
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@Composable
fun CauseItem(
    cause: String,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit,
    backgroundUnselected: Color,
    backgroundSelected: Color,
) {
    Text(
        text = cause,
        style = SightUPTheme.textStyles.body,
        color = if (isSelected) SightUPTheme.sightUPColors.primary_700 else SightUPTheme.sightUPColors.text_primary,
        modifier = Modifier
            .clip(SightUPTheme.shapes.small)
            .clickableWithRipple { onToggle(!isSelected) }
            .background(if (isSelected) backgroundSelected else backgroundUnselected)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.small
            )
            .padding(horizontal = SightUPTheme.spacing.spacing_base, vertical = 9.dp)
    )
}