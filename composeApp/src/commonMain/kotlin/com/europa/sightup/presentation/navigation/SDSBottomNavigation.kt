package com.europa.sightup.presentation.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.navigateToRootScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Data class that represents a bottom navigation item.
 *
 * @param screen is the screen that the item will navigate to.
 * @param label is the label that will be displayed in the bottom navigation bar.
 * @param icon is the icon that will be displayed in the bottom navigation bar.
 */

data class SDSBottomNavItem(
    val screen: Any,
    val label: String,
    val icon: DrawableResource,
)

/**
 * SDSBottomNavigationBar is a composable that represents the bottom navigation bar.
 *
 * @param navController is the navigation controller that will be used to navigate between screens.
 * @param currentSelectedScreen is the current screen that is selected.
 * @param listBottomScreens is the list of bottom navigation items.
 */

// Only use if we can not use the default BottomNavigationBar from Material3 because of the custom design
@Composable
fun SDSBottomNavigationBar(
    navController: NavHostController,
    currentSelectedScreen: Any,
    listBottomScreens: List<SDSBottomNavItem>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(NavigationBarDefaults.windowInsets)
            .defaultMinSize(minHeight = 56.dp)
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        listBottomScreens.forEach { item ->
            SDSBottomNavItem(
                isSelected = currentSelectedScreen == item.screen,
                icon = item.icon,
                label = item.label,
                onClick = {
                    navController.navigateToRootScreen(item.screen)
                          },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SDSBottomNavItem(
    isSelected: Boolean,
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedIconColor by animateColorAsState(
        targetValue = if (isSelected) Color.Blue else Color.Black,
        animationSpec = tween(durationMillis = 300)
    )

    val animatedTextColor by animateColorAsState(
        targetValue = if (isSelected) Color.Blue else Color.Black,
        animationSpec = tween(durationMillis = 300)
    )

    val animationProgress by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.6f,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(SightUPTheme.shapes.small)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = animatedIconColor,
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer(alpha = animationProgress)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = SightUPTheme.textStyles.footnote,
            color = animatedTextColor
        )
    }
}