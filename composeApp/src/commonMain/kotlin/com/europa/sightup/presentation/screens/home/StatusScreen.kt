package com.europa.sightup.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.ExpandableItem
import com.europa.sightup.presentation.designsystem.components.ExpandableListItem
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_right
import sightupkmpapp.composeapp.generated.resources.excelent
import sightupkmpapp.composeapp.generated.resources.good
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.moderate
import sightupkmpapp.composeapp.generated.resources.poor
import sightupkmpapp.composeapp.generated.resources.very_poor

data class Condition(
    val title: String,
    val message: String,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatusScreen(
    title: String = "moderate",

    imageIcon: DrawableResource = when (title) {
        "excellent" -> Res.drawable.excelent
        "veryPoor" -> Res.drawable.very_poor
        "poor" -> Res.drawable.poor
        "moderate" -> Res.drawable.moderate
        "good" -> Res.drawable.good
        else -> Res.drawable.moderate // Ícone padrão para outros casos
    },
    _conditions: List<Condition> = mutableStateListOf(
        Condition("Eye Strain", "Message for Eye Strain"),
        Condition("Dry Eyes", "Message for Dry Eyes"),
        Condition("Red Eyes", "Message for Red Eyes")
    ),
    causes: List<String> = mutableStateListOf(
        "Smoke",
        "Poor Lighting",
        "Long distance drive",
        "New eyewear",
        "Long screen time",
        "Foreign bodies"
    ),
) {
    val conditions = _conditions
    val items = remember {
        conditions.map { condition ->
            ExpandableItem(
                title = condition.title,
                message = condition.message,
                isExpanded = false
            )
        }
    }
    val expandedStates = remember { mutableStateListOf(*BooleanArray(items.size) { false }.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(SightUPTheme.sightUPColors.background_light)

    ) {
        SDSTopBar(
            modifier = Modifier,
            title = "Status",
            iconLeftVisible = false,
            onLeftButtonClick = {

            },
            iconRightVisible = false
        ) {}

        Spacer(Modifier.height(24.dp))

        Image(
            painter = painterResource(imageIcon),
            contentDescription = null,
            //contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .width(72.dp)
                .height(72.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(700),
            style = SightUPTheme.textStyles.body
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_sm)
        ) {
            if (conditions.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        "Conditions",
                        style = SightUPTheme.textStyles.subtitle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow {
                        for (condition in conditions) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)  // Espaço externo ao Box
                                    .background(
                                        color = SightUPTheme.sightUPColors.background_default,
                                        shape = RoundedCornerShape(8.dp)  // Borda arredondada
                                    )
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 9.dp
                                    )  // Espaço interno entre o texto e as bordas
                            ) {

                                Text(
                                    text = condition.title,
                                    style = SightUPTheme.textStyles.body
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        "Cause(s)",

                        style = SightUPTheme.textStyles.subtitle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow {
                        for (cause in causes) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)  // Espaço externo ao Box
                                    .background(
                                        color = SightUPTheme.sightUPColors.background_default,
                                        shape = RoundedCornerShape(8.dp)  // Borda arredondada
                                    )
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 9.dp
                                    )   // Espaço interno entre o texto e as bordas
                            ) {
                                Text(
                                    text = cause,
                                    style = SightUPTheme.textStyles.body
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, // Garante que o botão vá para a direita
                        verticalAlignment = Alignment.CenterVertically // Centraliza verticalmente os elementos
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Eye Wellness Tips ",
                                style = SightUPTheme.textStyles.subtitle
                            )

                            Spacer(modifier = Modifier.width(4.dp)) // Adiciona um pequeno espaço entre o texto e o ícone

                            Image(
                                painter = painterResource(Res.drawable.information),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                        Button(
                            {},
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            modifier = Modifier,
                            shape = SightUPTheme.shapes.small,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically // Centraliza o conteúdo verticalmente
                            ) {
                                Text(
                                    text = "View All",
                                    color = SightUPTheme.sightUPColors.text_primary
                                )
                                Spacer(Modifier.width(8.dp))
                                Image(
                                    painter = painterResource(Res.drawable.arrow_right),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            }
                        }
                    }

                }
                itemsIndexed(items) { index, item ->
                    ExpandableListItem(
                        item = item,
                        isExpanded = expandedStates[index],
                        onExpandedChange = { expandedStates[index] = it }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_24))

        SDSButton(
            "Close",
            {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}