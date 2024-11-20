package com.europa.sightup.presentation.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.clickableWithRipple
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.drop_drow_arrow

data class ExpandableItem(
    val title: String,
    val badge: String,
    val message: String,
    var isExpanded: Boolean = false,
)

@Composable
fun ExpandableListItem(
    item: ExpandableItem,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = SightUPTheme.sizes.size_48
            )
            .clip(SightUPTheme.shapes.small)
            .clickableWithRipple {
                onExpandedChange(!isExpanded)
            }
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.small
            )
            .background(SightUPTheme.sightUPColors.background_default)
            .padding(horizontal = SightUPTheme.spacing.spacing_base, vertical = SightUPTheme.spacing.spacing_xs),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.title,
                style = SightUPTheme.textStyles.body,
                color = SightUPTheme.sightUPColors.text_primary
            )
            Spacer(Modifier.width(SightUPTheme.sizes.size_16))
            SDSConditions(
                type = SDSConditionsEnum.fromString(item.badge)
            )
            Spacer(Modifier.weight(ONE_FLOAT))
            Icon(
                painter = painterResource(Res.drawable.drop_drow_arrow),
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier
                    .graphicsLayer(rotationZ = rotationAngle)
                    .size(SightUPTheme.sizes.size_16),
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SightUPTheme.spacing.spacing_sm)
            ) {
                Text(
                    text = item.message,
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary
                )
                Spacer(Modifier.height(SightUPTheme.sizes.size_8))
            }
        }
    }
}