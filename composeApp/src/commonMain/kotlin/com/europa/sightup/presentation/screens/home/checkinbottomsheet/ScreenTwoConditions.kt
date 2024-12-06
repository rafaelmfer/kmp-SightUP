package com.europa.sightup.presentation.screens.home.checkinbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

data class Condition(val name: String, val description: String)

@Composable
fun ScreenTwoConditions(
    title: String = "What condition(s) is bothering you today?",
    subtitle: String = "Select all that applies",
    onClickNext: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val conditions = listOf(
        Condition("Eye Strain", "Tired or overworked eyes"),
        Condition("Dry eyes", "Lack of moisture"),
        Condition("Red Eyes", "Bloodshot or irritated"),
        Condition("Irritated Eyes", "Uncomfortable, inflamed sensation"),
        Condition("Itchy Eyes", "Urge to rub")
    )

    val selectedConditions = remember { mutableStateListOf<String>() }
    val backgroundUnselected by remember { mutableStateOf(SightUPContextColor.background_default) }
    val backgroundSelected by remember { mutableStateOf(SightUPContextColor.background_activate) }

    fun toggleCondition(condition: String, isSelected: Boolean) {
        if (isSelected) {
            selectedConditions.add(condition)
        } else {
            selectedConditions.remove(condition)
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
                .constrainAs(topColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                style = SightUPTheme.textStyles.h5
            )
            Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_8))
            Text(
                text = subtitle,
                modifier = Modifier.fillMaxWidth(),
                style = SightUPTheme.textStyles.body
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(middleColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topColumn.bottom, margin = 32.dp)
                    bottom.linkTo(button.top, margin = 32.dp)
                    width = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            conditions.forEach { condition ->
                ConditionItem(
                    condition = condition,
                    isSelected = selectedConditions.contains(condition.name),
                    onToggle = { isSelected ->
                        toggleCondition(condition.name, isSelected)
                    },
                    backgroundUnselected = backgroundUnselected,
                    backgroundSelected = backgroundSelected
                )
            }
        }

        SDSButton(
            text = "Next(2/3)",
            onClick = {
                onClickNext(selectedConditions)
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
fun ConditionItem(
    condition: Condition,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit,
    backgroundUnselected: Color,
    backgroundSelected: Color,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_2xs),
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.small)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.small
            )
            .clickableWithRipple { onToggle(!isSelected) }
            .background(if (isSelected) backgroundSelected else backgroundUnselected)
            .padding(
                horizontal = SightUPTheme.spacing.spacing_base,
                vertical = SightUPTheme.spacing.spacing_xs
            )
    ) {
        Text(
            text = condition.name,
            modifier = Modifier.fillMaxWidth(),
            style = if (isSelected) SightUPTheme.textStyles.body.copy(
                fontWeight = FontWeight.Bold
            ) else SightUPTheme.textStyles.body,
            textAlign = TextAlign.Center,
            color = if (isSelected) SightUPTheme.sightUPColors.primary_700 else SightUPTheme.sightUPColors.text_primary
        )
        Text(
            text = condition.description,
            modifier = Modifier.fillMaxWidth(),
            style = SightUPTheme.textStyles.body2,
            textAlign = TextAlign.Center,
            color = if (isSelected) SightUPTheme.sightUPColors.primary_700 else SightUPTheme.sightUPColors.text_primary
        )
    }
}
