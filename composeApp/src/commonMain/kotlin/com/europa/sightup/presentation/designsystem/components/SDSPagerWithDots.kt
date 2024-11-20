package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SDSPagerWithDots(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = SightUPTheme.sightUPColors.primary_700,
    unselectedColor: Color = SightUPTheme.sightUPColors.primary_200,
    contentProvider: @Composable (Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            contentProvider(page)
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_sm))

        // Dots for page indicators
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (isSelected) selectedColor else unselectedColor
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(SightUPTheme.spacing.spacing_md))
    }
}

@Composable
fun PagerTestResultContent(
    image: DrawableResource,
    title: String,
    text: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Image(
            painter = painterResource(image),
            contentDescription = title,
            modifier = Modifier.fillMaxWidth()
                .height(130.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = SightUPTheme.textStyles.body,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_sm))

        Text(
            text = text,
            style = SightUPTheme.textStyles.body,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
