package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.pone

@Composable
fun CardExercise(
    exercise: ExerciseResponse,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = SightUPBorder.Width.sm,
                color = Color.LightGray,
                shape = RoundedCornerShape(SightUPBorder.Radius.lg)
            )
            .padding(SightUPTheme.spacing.spacing_base)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = SightUPTheme.sightUPColors.background_default
        )
    ) {
        Image(
            painter = painterResource(Res.drawable.pone),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.category ?: "Movement",
                style = SightUPTheme.textStyles.h4,
                color = SightUPTheme.sightUPColors.text_primary,
            )
            SDSBadgeTime(
                timeMinutes = exercise.duration,
            )
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_2xs))
        Text(
            text = exercise.title ?: "Circular Motion",
            style = SightUPTheme.textStyles.subtitle,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
        Text(
            text = exercise.description ?: "Enhance the range of motion and improve blood circulation around the eyes.",
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
        Text(
            text = "Helps with:",
            style = SightUPTheme.textStyles.subtitle,
            color = SightUPTheme.sightUPColors.primary_700,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        LazyRow {
            items(exercise.helps) { condition ->
                SDSConditions(
                    type = SDSConditionsEnum.fromString(condition),
                )
                if (condition != exercise.helps.last()) {
                    Spacer(Modifier.width(SightUPTheme.spacing.spacing_xs))
                }
            }
        }
    }
}