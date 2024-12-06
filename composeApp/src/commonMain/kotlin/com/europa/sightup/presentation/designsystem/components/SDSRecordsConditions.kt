package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.europa.sightup.presentation.designsystem.components.RecordsConditions.Companion.getConditionConfig
import com.europa.sightup.presentation.designsystem.components.RecordsConditions.ConditionType
import com.europa.sightup.presentation.designsystem.components.RecordsConditions.RecordType
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.color.SightUPColorPalette
import com.europa.sightup.presentation.ui.theme.color.SightUPContextColor
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.excelent
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.moderate
import sightupkmpapp.composeapp.generated.resources.poor

sealed interface RecordsConditions {

    companion object {
        fun recordFromString(value: String): RecordType {
            return RecordType.entries.find {
                it.title.equals(value, ignoreCase = true)
            } ?: RecordType.VISION
        }

        fun conditionFromString(value: String): ConditionType {
            return ConditionType.entries.find {
                it.title.equals(value, ignoreCase = true)
            } ?: ConditionType.NO_SIGNIFICANT_CHANGE
        }

        fun getConditionConfig(type: RecordType, condition: ConditionType): ConditionConfig {
            val message = when (type) {
                RecordType.VISION -> when (condition) {
                    ConditionType.NO_SIGNIFICANT_CHANGE -> "There is no significant change."
                    ConditionType.SLIGHT_DECLINE -> "Your vision has slightly declined."
                    ConditionType.DETERIORATED -> "Your vision has deteriorated. Recommend to see a doctor."
                }

                RecordType.GLASSES -> when (condition) {
                    ConditionType.NO_SIGNIFICANT_CHANGE -> "Your glasses are still effective. No change needed."
                    ConditionType.SLIGHT_DECLINE -> "Vision changes detected—update glasses recommended."
                    ConditionType.DETERIORATED -> "New glasses needed for better correction."
                }

                RecordType.CONTACT_LENSES -> when (condition) {
                    ConditionType.NO_SIGNIFICANT_CHANGE -> "Your lenses are fine. No change needed."
                    ConditionType.SLIGHT_DECLINE -> "Vision changes detected—update lenses recommended."
                    ConditionType.DETERIORATED -> "Your lenses need replacing for better vision."
                }
            }

            val icon = when (condition) {
                ConditionType.NO_SIGNIFICANT_CHANGE -> Res.drawable.excelent
                ConditionType.SLIGHT_DECLINE -> Res.drawable.moderate
                ConditionType.DETERIORATED -> Res.drawable.poor
            }

            val colors = when (condition) {
                ConditionType.NO_SIGNIFICANT_CHANGE -> ConditionConfig.Colors(
                    backgroundColor = SightUPContextColor.background_success,
                    borderColor = SightUPContextColor.border_success,
                    textColor = SightUPColorPalette.success_300,
                    iconTint = SightUPColorPalette.success_300
                )

                ConditionType.SLIGHT_DECLINE -> ConditionConfig.Colors(
                    backgroundColor = SightUPContextColor.background_info,
                    borderColor = SightUPContextColor.border_info,
                    textColor = SightUPColorPalette.info_300,
                    iconTint = SightUPColorPalette.info_300
                )

                ConditionType.DETERIORATED -> ConditionConfig.Colors(
                    backgroundColor = SightUPContextColor.background_warning,
                    borderColor = SightUPContextColor.border_warning,
                    textColor = SightUPColorPalette.warning_300,
                    iconTint = SightUPColorPalette.warning_300
                )
            }

            return ConditionConfig(
                message = message,
                backgroundColor = colors.backgroundColor,
                borderColor = colors.borderColor,
                textColor = colors.textColor,
                icon = icon,
                iconTint = colors.iconTint
            )
        }
    }

    enum class RecordType(val title: String) {
        VISION("Vision"),
        GLASSES("Glasses"),
        CONTACT_LENSES("Contact Lenses");
    }

    enum class ConditionType(val title: String) {
        NO_SIGNIFICANT_CHANGE("Normal"),
        SLIGHT_DECLINE("Changed"),
        DETERIORATED("Deteriorated");
    }

    data class ConditionConfig(
        val message: String,
        val backgroundColor: Color,
        val borderColor: Color,
        val textColor: Color,
        val icon: DrawableResource,
        val iconTint: Color,
    ) {
        data class Colors(
            val backgroundColor: Color,
            val borderColor: Color,
            val textColor: Color,
            val iconTint: Color,
        )
    }
}

@Composable
fun SDSRecordsConditions(
    type: RecordType = RecordsConditions.recordFromString(""),
    condition: ConditionType = RecordsConditions.conditionFromString(""),
    modifier: Modifier = Modifier,
) {
    val config = remember(type, condition) { getConditionConfig(type, condition) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.small)
            .background(config.backgroundColor),
    ) {
        Image(
            painter = painterResource(config.icon),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = SightUPTheme.spacing.spacing_xs,
                    top = SightUPTheme.spacing.spacing_sm,
                    bottom = SightUPTheme.spacing.spacing_sm,
                )
                .size(SightUPTheme.sizes.size_48)
                .padding(SightUPTheme.spacing.spacing_xs),
        )
        Text(
            text = config.message,
            color = config.textColor,
            style = SightUPTheme.textStyles.body2.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(
                    horizontal = SightUPTheme.spacing.spacing_xs,
                    vertical = SightUPTheme.spacing.spacing_sm
                )
                .weight(ONE_FLOAT),
        )
        Icon(
            painter = painterResource(Res.drawable.information),
            contentDescription = null,
            tint = config.iconTint,
            modifier = Modifier
                .padding(
                    end = SightUPTheme.spacing.spacing_sm,
                    top = SightUPTheme.spacing.spacing_sm,
                    bottom = SightUPTheme.spacing.spacing_sm,
                )
                .size(SightUPTheme.sizes.size_16)
        )
    }
}

@Composable
fun SDSRecordsConditionsPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm, Alignment.CenterVertically),
        modifier = Modifier.fillMaxSize(),
    ) {
        SDSRecordsConditions(
            type = RecordType.VISION,
            condition = ConditionType.NO_SIGNIFICANT_CHANGE,
            modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_sm)
        )
        SDSRecordsConditions(
            type = RecordType.GLASSES,
            condition = ConditionType.SLIGHT_DECLINE,
            modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_sm)
        )
        SDSRecordsConditions(
            type = RecordType.CONTACT_LENSES,
            condition = ConditionType.DETERIORATED,
            modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_sm)
        )
    }
}
