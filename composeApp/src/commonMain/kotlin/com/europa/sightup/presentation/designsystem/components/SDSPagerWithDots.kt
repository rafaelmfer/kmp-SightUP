package com.europa.sightup.presentation.designsystem.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing

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
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = SightUPTheme.spacing.spacing_2xs)
                        .size(SightUPTheme.sizes.size_8)
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