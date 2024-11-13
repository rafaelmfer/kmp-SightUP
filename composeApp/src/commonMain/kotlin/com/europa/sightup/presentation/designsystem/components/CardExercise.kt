package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.presentation.designsystem.components.data.SDSConditionsEnum
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.share

@Composable
fun CardExercise(
    exercise: ExerciseResponse,
    iconWhite: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.large)
            .clickable { onClick() }
            .background(SightUPTheme.sightUPColors.background_default)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.large
            )
            .padding(SightUPTheme.spacing.spacing_base)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = SightUPTheme.sightUPColors.background_default
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            CoilImage(
                imageModel = { exercise.images },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(SightUPTheme.shapes.medium)
                    .border(
                        width = SightUPBorder.Width.sm,
                        color = SightUPTheme.sightUPColors.border_card,
                        shape = SightUPTheme.shapes.medium
                    )
                    .height(160.dp),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                },
                failure = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Image failed to load")
                    }
                }
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(SightUPTheme.spacing.spacing_xs)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.share),
                    contentDescription = null,
                    tint = if (iconWhite) SightUPTheme.sightUPColors.neutral_0 else SightUPTheme.sightUPColors.text_primary,
                )
            }
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.category,
                style = SightUPTheme.textStyles.h4,
                color = SightUPTheme.sightUPColors.text_primary,
            )
            SDSBadgeTime(
                timeSeconds = exercise.duration,
            )
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_2xs))
        Text(
            text = exercise.title,
            style = SightUPTheme.textStyles.subtitle,
            color = SightUPTheme.sightUPColors.text_primary,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
        Text(
            text = exercise.description,
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