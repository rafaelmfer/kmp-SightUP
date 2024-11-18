package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.fontWeight
import com.europa.sightup.presentation.ui.theme.typography.lineHeight
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.clickableWithRipple
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.check
import sightupkmpapp.composeapp.generated.resources.information

@Composable
fun SDSCardTest(
    test: TestResponse,
    navigateToTest: () -> Unit,
    clickInfoIcon: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.large)
            .background(SightUPTheme.sightUPColors.background_default)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.large
            )
            .clickableWithRipple { navigateToTest() }
            .padding(SightUPTheme.spacing.spacing_base)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = SightUPTheme.sightUPColors.background_default
        )
    ) {
        CoilImage(
            imageModel = { test.images },
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
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableWithRipple { clickInfoIcon() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = test.title,
                style = SightUPTheme.textStyles.h4,
                color = SightUPTheme.sightUPColors.text_primary,
            )
            Spacer(Modifier.width(SightUPTheme.spacing.spacing_xs))

            Icon(
                painter = painterResource(Res.drawable.information),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_2xs))

        Text(
            text = "Checklist for test",
            style = SightUPTheme.textStyles.body2,
            lineHeight = SightUPTheme.lineHeight.lineHeight_2xs,
            fontWeight = SightUPTheme.fontWeight.fontWeight_bold,
            color = SightUPTheme.sightUPColors.primary_700,
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))

        test.checkList.forEach { item ->
            Row(
                modifier = Modifier.padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.check),
                    contentDescription = null,
                    tint = SightUPTheme.sightUPColors.primary_800,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item,
                    style = SightUPTheme.textStyles.caption,
                    lineHeight = SightUPTheme.lineHeight.lineHeight_3xl,
                )
            }
        }
    }
}