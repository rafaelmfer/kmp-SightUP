package com.europa.sightup.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    title: String,
    leftIcon:ImageVector? = null,
    rightIcon: ImageVector? = null,
    leftButton: Boolean = false,
    rightButton: Boolean = false,
    onLeftButtonClick: (() -> Unit)? = null,
    onRightButtonClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        if (leftButton) {
            IconButton(
                onClick = { onLeftButtonClick?.invoke() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                if (leftIcon != null) {
                    Icon(
                        leftIcon,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Text(
            text = title,
            style = SightUPTheme.textStyles.subtitle,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        if (rightButton) {
            IconButton(
                onClick = { onRightButtonClick?.invoke() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                if (rightIcon != null) {
                    Icon(
                        rightIcon,
                        contentDescription = "Close",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
