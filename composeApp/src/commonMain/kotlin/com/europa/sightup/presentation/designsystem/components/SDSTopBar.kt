package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_back
import sightupkmpapp.composeapp.generated.resources.guide_book

@Preview
@Composable
fun SDSTopBar(
    modifier: Modifier = Modifier,
    title: String = "Screen Title",
    iconLeftVisible: Boolean = false,
    /**
     * @param iconLeft can be a DrawableResource or an ImageVector
     */
    iconLeft: Any? = Res.drawable.arrow_back,
    onLeftButtonClick: () -> Unit = {},
    iconRightVisible: Boolean = false,
    /**
     * @param iconRight can be a DrawableResource or an ImageVector
     */
    iconRight: Any? = Res.drawable.guide_book,
    onRightButtonClick: () -> Unit = {},
) {
    val spaceModifier = Modifier.width(48.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SightUPTheme.sizes.size_48)
//            .padding(
//                horizontal = SightUPTheme.spacing.spacing_xs
//            )
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            iconLeftVisible -> {
                IconButton(
                    onClick = {
                        onLeftButtonClick()
                    }
                ) {
                    if (iconLeft is DrawableResource) {
                        if (iconLeft == Res.drawable.arrow_back) {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(iconLeft),
                                contentDescription = "Back"
                            )
                        } else {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(iconLeft),
                                contentDescription = null
                            )
                        }
                    } else {
                        Icon(
                            modifier = Modifier,
                            imageVector = iconLeft as ImageVector,
                            contentDescription = null
                        )
                    }
                }
            }

            !iconLeftVisible && iconRightVisible -> {
                Spacer(modifier = spaceModifier)
            }
        }

        Text(
            modifier = Modifier
                .padding(SightUPTheme.spacing.spacing_2xs)
                .weight(1f),
            text = title,
            style = SightUPTheme.textStyles.subtitle,
            textAlign = TextAlign.Center,
        )

        when {
            iconRightVisible -> {
                IconButton(
                    onClick = {
                        onRightButtonClick()
                    }
                ) {
                    if (iconRight is DrawableResource) {
                        if (iconRight == Res.drawable.guide_book) {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(iconRight),
                                contentDescription = "Guide Book"
                            )
                        } else {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(iconRight),
                                contentDescription = null
                            )
                        }
                    } else {
                        Icon(
                            modifier = Modifier,
                            imageVector = iconRight as ImageVector,
                            contentDescription = null
                        )
                    }
                }
            }

            !iconRightVisible && iconLeftVisible -> {
                Spacer(modifier = spaceModifier)
            }
        }
    }
}

@Preview
@Composable
fun SDSTopBarScreen() {
    val modifier = Modifier
        .background(color = SightUPTheme.sightUPColors.white)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = SightUPTheme.sightUPColors.neutral_200)
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin),
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs, Alignment.CenterVertically),
    ) {
        SDSTopBar(
            modifier = modifier,
            title = "Screen Title",
            iconLeftVisible = false,
            iconRightVisible = false,
        )

        SDSTopBar(
            modifier = modifier,
            title = "Screen Title",
            iconLeftVisible = true,
            onLeftButtonClick = {
                showToast(
                    message = "Back",
                    bottomPadding = 40
                )
            },
            iconRightVisible = true,
            onRightButtonClick = {
                showToast(
                    message = "Guide Book",
                    bottomPadding = 40
                )
            }
        )
        SDSTopBar(
            modifier = modifier,
            title = "Screen Title",
            iconLeftVisible = false,
            iconRightVisible = true,
            onRightButtonClick = {
                showToast(
                    message = "Guide Book",
                    bottomPadding = 40
                )
            }
        )
        SDSTopBar(
            modifier = modifier,
            title = "Screen Title",
            iconLeftVisible = true,
            onLeftButtonClick = {
                showToast(
                    message = "Back",
                    bottomPadding = 40
                )
            },
            iconRightVisible = false,
        )
    }
}