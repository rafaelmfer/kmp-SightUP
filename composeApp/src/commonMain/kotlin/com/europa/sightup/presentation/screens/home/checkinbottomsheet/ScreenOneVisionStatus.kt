package com.europa.sightup.presentation.screens.home.checkinbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.europa.sightup.platformspecific.getScreenSizeInInches
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.color.SightUPColorPalette
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.Moods
import com.europa.sightup.utils.capitalizeWords
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScreenOneVisionStatus(
    onClickNext: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val moods = listOf(
        Moods.VERY_POOR,
        Moods.POOR,
        Moods.MODERATE,
        Moods.GOOD,
        Moods.VERY_GOOD
    )

    var activeMood by remember { mutableStateOf(Moods.MODERATE) }
    val activeColor = SightUPColorPalette.primary_700

    val moodAnimation = when (activeMood) {
        Moods.VERY_POOR -> Moods.VERY_POOR.icon
        Moods.POOR -> Moods.POOR.icon
        Moods.MODERATE -> Moods.MODERATE.icon
        Moods.GOOD -> Moods.GOOD.icon
        Moods.VERY_GOOD -> Moods.VERY_GOOD.icon
        else -> Moods.MODERATE.icon
    }

    val screenSizeInInches = getScreenSizeInInches()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        val (title, image, slider, button) = createRefs()

        Text(
            "How would you describe your eye comfort today?",
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            },
            style = SightUPTheme.textStyles.h5
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.constrainAs(image) {
                top.linkTo(title.bottom, margin = 32.dp)
                bottom.linkTo(slider.top, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                if (screenSizeInInches > 4.9f) height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
        ) {
            Image(
                painter = painterResource(moodAnimation),
                modifier = Modifier
                    .padding(horizontal = 60.dp)
                    .fillMaxWidth(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        MoodSlider(
            moods = moods,
            activeMood = activeMood,
            activeColor = activeColor,
            onMoodSelected = { activeMood = it },
            modifier = Modifier.constrainAs(slider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(button.top, margin = 32.dp)
            }
        )

        SDSButton(
            text = "Next(1/3)",
            onClick = {
                onClickNext(activeMood.value)
            },
            modifier = Modifier.constrainAs(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 24.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun MoodSlider(
    moods: List<Moods>,
    activeMood: Moods,
    activeColor: Color,
    onMoodSelected: (Moods) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color(0xFFD3E3EE))
                    .align(Alignment.Center)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                moods.forEach { mood ->
                    MoodButton(
                        mood = mood,
                        isSelected = mood == activeMood,
                        activeColor = activeColor,
                        onClick = { onMoodSelected(mood) }
                    )
                }
            }
        }
        Spacer(Modifier.height(SightUPTheme.sizes.size_20))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            moods.forEach { mood ->
                MoodLabel(
                    mood = mood,
                    isSelected = mood == activeMood,
                    activeColor = activeColor
                )
            }
        }
    }
}

@Composable
fun MoodButton(
    mood: Moods,
    isSelected: Boolean,
    activeColor: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) activeColor else SightUPTheme.sightUPColors.primary_200
        ),
        content = {}
    )
}

@Composable
fun MoodLabel(
    mood: Moods,
    isSelected: Boolean,
    activeColor: Color,
) {
    Text(
        text = mood.value.capitalizeWords(),
        style = if (isSelected) SightUPTheme.textStyles.caption.copy(fontWeight = FontWeight.Bold)
        else SightUPTheme.textStyles.caption,
        color = if (isSelected) activeColor else SightUPTheme.sightUPColors.text_tertiary
    )
}
