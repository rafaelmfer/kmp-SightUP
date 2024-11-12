package com.europa.sightup.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role

fun Modifier.applyIf(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) then(modifier(Modifier)) else this
}

fun Modifier.clickableWithRipple(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit,
): Modifier {
    return composed {
        clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            interactionSource = interactionSource ?: remember { MutableInteractionSource() },
            indication = ripple(color = Color(0xff5691B9)),
            onClick = onClick,
        )
    }
}