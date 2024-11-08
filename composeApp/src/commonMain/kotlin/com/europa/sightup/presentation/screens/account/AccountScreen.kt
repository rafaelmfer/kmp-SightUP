package com.europa.sightup.presentation.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_right

@Composable
fun AccountScreen(navController: NavHostController? = null) {
    val itemsButtons = listOf(
        "Profile",
        "Preferences",
        "Notifications",
        "Terms and Conditions",
        "Logout"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
    ) {
        item {
            SDSTopBar(
                title = "Account",
                iconLeftVisible = false,
                iconRightVisible = false,
            )
            Spacer(Modifier.height(SightUPTheme.sizes.size_8))
        }
        itemsIndexed(itemsButtons) { index, buttonText ->
            ButtonOption(
                item = buttonText,
                onClick = {
                    when (buttonText) {
                        "Profile" -> {

                        }

                        "Preferences" -> {

                        }

                        "Notifications" -> {

                        }

                        "Terms and Conditions" -> {

                        }

                        "Logout" -> {

                        }
                    }
                }
            )
            if (index != itemsButtons.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
                    color = SightUPTheme.sightUPColors.divider,
                    thickness = SightUPBorder.Width.sm
                )
            }
        }
    }
}

@Composable
private fun ButtonOption(item: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = SightUPTheme.sightUPColors.text_primary,
            containerColor = SightUPTheme.sightUPColors.background_light
        ),
        shape = SightUPTheme.shapes.extraSmall,
        contentPadding = PaddingValues(SightUPTheme.sizes.size_20),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = SightUPTheme.sizes.size_48),
    ) {
        Text(
            text = item,
            color = SightUPTheme.sightUPColors.text_primary,
            style = SightUPTheme.textStyles.body
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        Icon(
            painter = painterResource(Res.drawable.arrow_right),
            contentDescription = null
        )
    }
}