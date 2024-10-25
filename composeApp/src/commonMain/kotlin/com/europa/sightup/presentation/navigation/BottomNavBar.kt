package com.europa.sightup.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.navigateToRootScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class BottomNavItem(
    val screen: Any,
    val label: String,
    val icon: DrawableResource,
) {
    fun getQualifiedScreenName(): String {
        return screen::class.qualifiedName!!
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentSelectedScreen: Any,
    listBottomScreens: List<BottomNavItem>,
) {
    val selectedColor = SightUPTheme.sightUPColors.primary_600
    val unselectedColor = SightUPTheme.sightUPColors.neutral_400

    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = selectedColor,
        unselectedIconColor = unselectedColor,
        selectedTextColor = selectedColor,
        unselectedTextColor = unselectedColor,
        indicatorColor = Color.Transparent
    )

    NavigationBar(
        modifier = Modifier,
        containerColor = SightUPTheme.sightUPColors.white
    ) {
        listBottomScreens.forEach { item ->
            NavigationBarItem(
                selected = currentSelectedScreen == item.getQualifiedScreenName(),
                onClick = {
                    navController.navigateToRootScreen(item.screen)
                },
                alwaysShowLabel = true,
                label = {
                },
                icon = {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            modifier = Modifier.size(SightUPTheme.sizes.size_32),
                            contentDescription = item.label,
                            tint = if (currentSelectedScreen == item.getQualifiedScreenName()) selectedColor else unselectedColor
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            style = SightUPTheme.textStyles.footnote,
                            color = if (currentSelectedScreen == item.getQualifiedScreenName()) selectedColor else unselectedColor
                        )
                    }
                },
                colors = colors
            )
        }
    }
}