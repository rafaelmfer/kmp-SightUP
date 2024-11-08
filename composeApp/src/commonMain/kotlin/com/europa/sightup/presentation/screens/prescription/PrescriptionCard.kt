package com.europa.sightup.presentation.screens.prescription

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.europa.sightup.data.remote.response.UserPrescriptionResponse
import com.europa.sightup.data.remote.response.visionHistory.HistoryTestResponse
import com.europa.sightup.data.remote.response.visionHistory.ResultResponse
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.RecordsConditions
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSLocationBadge
import com.europa.sightup.presentation.designsystem.components.SDSRecordsConditions
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.toFormattedDate
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.contact_lenses
import sightupkmpapp.composeapp.generated.resources.glasses
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.menu
import sightupkmpapp.composeapp.generated.resources.naked_eye

@Composable
fun PrescriptionCardPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PrescriptionCard("Vision")
    }
}

enum class PrescriptionType(val title: String) {
    VISION("Vision"),
    GLASSES("Glasses"),
    CONTACT_LENSES("Contact Lenses");

    companion object {
        fun fromString(value: String): PrescriptionType {
            return PrescriptionType.entries.find {
                it.title.equals(value, ignoreCase = true)
            } ?: VISION
        }
    }
}

@Composable
fun PrescriptionCard(
    text: String,
    history: HistoryTestResponse? = null,
    prescription: UserPrescriptionResponse? = null,
    onClickVisionHistory: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val prescriptionType = remember { PrescriptionType.fromString(text) }
    val icon = remember {
        when (prescriptionType) {
            PrescriptionType.VISION -> Res.drawable.naked_eye
            PrescriptionType.GLASSES -> Res.drawable.glasses
            PrescriptionType.CONTACT_LENSES -> Res.drawable.contact_lenses
        }
    }

    var expanded by remember { mutableStateOf(false) }
    val results = remember { mutableStateOf(history?.result ?: prescription?.prescriptionInfo) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.small)
            .background(SightUPTheme.sightUPColors.background_default)
            .border(
                width = SightUPBorder.Width.sm,
                color = SightUPTheme.sightUPColors.border_card,
                shape = SightUPTheme.shapes.small
            )
            .padding(SightUPTheme.spacing.spacing_base)
            .then(modifier)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(SightUPTheme.spacing.spacing_xs)
                )
                Text(
                    text = text,
                    style = SightUPTheme.textStyles.h5,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier
                )
                if (prescriptionType == PrescriptionType.VISION && history != null) {
                    Spacer(Modifier.width(SightUPTheme.spacing.spacing_base))
                    SDSLocationBadge(history.appTest)
                }
            }
            IconButton(
                onClick = onClickEdit,
                modifier = Modifier,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.menu),
                    contentDescription = "Edit",
                    tint = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.size(SightUPTheme.sizes.size_24)
                )
            }
        }

        when (prescriptionType) {
            PrescriptionType.VISION -> {
                history?.let {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = SightUPTheme.sightUPColors.text_primary,
                                    fontSize = SightUPTheme.textStyles.button.fontSize,
                                    fontWeight = SightUPTheme.textStyles.button.fontWeight
                                )
                            ) {
                                append("Latest Vision Test: ")
                            }
                            append(history.date.toFormattedDate())
                        },
                        style = SightUPTheme.textStyles.body2,
                        color = SightUPTheme.sightUPColors.text_primary,
                        modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_xs)
                    )
                }
            }

            PrescriptionType.GLASSES -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = SightUPTheme.sightUPColors.text_primary,
                                fontSize = SightUPTheme.textStyles.button.fontSize,
                                fontWeight = SightUPTheme.textStyles.button.fontWeight
                            )
                        ) {
                            append("Prescription Date: ")
                        }
                        append(prescription?.prescriptionDate?.toFormattedDate())
                    },
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_xs)
                )
            }

            PrescriptionType.CONTACT_LENSES -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = SightUPTheme.sightUPColors.text_primary,
                                fontSize = SightUPTheme.textStyles.button.fontSize,
                                fontWeight = SightUPTheme.textStyles.button.fontWeight
                            )
                        ) {
                            append("Prescription Date: ")
                        }
                        append(prescription?.prescriptionDate?.toFormattedDate())
                    },
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_xs)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = SightUPTheme.sightUPColors.text_primary,
                                fontSize = SightUPTheme.textStyles.button.fontSize,
                                fontWeight = SightUPTheme.textStyles.button.fontWeight
                            )
                        ) {
                            append("Expiration Date: ")
                        }
                        append("September 27, 2024")
                    },
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier.padding(top = SightUPTheme.spacing.spacing_xs)
                )

            }
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        SDSRecordsConditions(
            type = RecordsConditions.recordFromString(text),
            condition = RecordsConditions.conditionFromString("Normal"),
            modifier = Modifier
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        if (prescriptionType == PrescriptionType.VISION) {
            SDSButton(
                text = "Vision History",
                onClick = onClickVisionHistory,
                buttonStyle = ButtonStyle.OUTLINED,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        HorizontalDivider(
            color = SightUPTheme.sightUPColors.border_default,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        if (prescriptionType == PrescriptionType.CONTACT_LENSES) {
            ManufactureInformation()
            Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            HorizontalDivider(
                color = SightUPTheme.sightUPColors.border_default,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            results.value?.take(3)?.forEachIndexed { index, result ->
                if (index == 0) {
                    PrescriptionInfoLineWithTitle(
                        label = result.testTitle,
                        leftValue = result.left,
                        rightValue = result.right
                    )
                } else {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_side_margin))
                    PrescriptionInfoLine(
                        label = result.testTitle,
                        leftValue = result.left,
                        rightValue = result.right
                    )
                }
            }

            // Render remaining items if there are more than 3 and expand is enabled
            results.value?.let {
                if (it.size > 3) {
                    PrescriptionMoreInfo(
                        isExpanded = expanded,
                        resultResponse = it.subList(3, it.size)
                    )

                    SDSButton(
                        text = if (expanded) "Close" else "View More",
                        onClick = { expanded = !expanded },
                        buttonStyle = ButtonStyle.TEXT,
                        contentColor = SightUPTheme.sightUPColors.text_primary,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = SightUPTheme.spacing.spacing_side_margin)
                    )
                }
            }
        }
    }
}

@Composable
private fun ManufactureInformation() {
    Column(
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_xs),
    ) {
        Row {
            Text(
                text = "Manufacture",
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "Coopervision",
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .weight(2f)
            )
        }
        Row {
            Text(
                text = "Product Name",
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "Biofinity Toric 6pk",
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .weight(2f)
            )
        }
        Row {
            Text(
                text = "Replacement",
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "1 month daily wear",
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier
                    .weight(2f)
            )
        }
    }
}

@Composable
private fun PrescriptionInfoLineWithTitle(
    label: String = "VA",
    leftValue: String = "20/20",
    rightValue: String = "20/20",
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val (inputDescription, labelLeft, inputLeft, labelRight, inputRight) = createRefs()
        Text(
            text = "Left",
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier.constrainAs(labelLeft) {
                top.linkTo(parent.top)
                end.linkTo(inputLeft.end)
            }
        )
        Text(
            text = "Right",
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier.constrainAs(labelRight) {
                top.linkTo(parent.top)
                end.linkTo(inputRight.end)
            }
        )
        Row(
            modifier = Modifier.constrainAs(inputDescription) {
                top.linkTo(inputLeft.top)
                bottom.linkTo(inputLeft.bottom)
                start.linkTo(parent.start)
                end.linkTo(inputLeft.start, margin = 16.dp)
                width = Dimension.fillToConstraints
                horizontalChainWeight = 1f
            },
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_2xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier,
            )
            Icon(
                painter = painterResource(Res.drawable.information),
                contentDescription = "Information",
                tint = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.size(SightUPTheme.sizes.size_16)
            )
        }
        Text(
            text = leftValue,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .constrainAs(inputLeft) {
                    top.linkTo(labelLeft.bottom, margin = 20.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(inputDescription.end)
                    end.linkTo(inputRight.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    horizontalChainWeight = 0.5f
                }
        )
        Text(
            text = rightValue,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            textAlign = TextAlign.End,
            modifier = Modifier.constrainAs(inputRight) {
                top.linkTo(labelRight.bottom, margin = 20.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(inputLeft.end)
                end.linkTo(parent.end)
                horizontalBias = 1f
                width = Dimension.fillToConstraints
                horizontalChainWeight = 0.5f
            },
        )
    }
}

@Composable
private fun PrescriptionInfoLine(
    label: String = "SPH",
    leftValue: String = "20/20",
    rightValue: String = "20/20",
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val (inputDescription, inputLeft, inputRight) = createRefs()
        Row(
            modifier = Modifier.constrainAs(inputDescription) {
                top.linkTo(inputLeft.top)
                bottom.linkTo(inputLeft.bottom)
                start.linkTo(parent.start)
                end.linkTo(inputLeft.start, margin = 16.dp)
                width = Dimension.fillToConstraints
                horizontalChainWeight = 1f
            },
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_2xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = SightUPTheme.textStyles.body2,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier,
            )
            Icon(
                painter = painterResource(Res.drawable.information),
                contentDescription = "Information",
                tint = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.size(SightUPTheme.sizes.size_16)
            )
        }
        Text(
            text = leftValue,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .constrainAs(inputLeft) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(inputDescription.end)
                    end.linkTo(inputRight.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    horizontalChainWeight = 0.5f
                }
        )
        Text(
            text = rightValue,
            style = SightUPTheme.textStyles.body,
            color = SightUPTheme.sightUPColors.text_primary,
            textAlign = TextAlign.End,
            modifier = Modifier.constrainAs(inputRight) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(inputLeft.end)
                end.linkTo(parent.end)
                horizontalBias = 1f
                width = Dimension.fillToConstraints
                horizontalChainWeight = 0.5f
            },
        )
    }
}

@Composable
private fun PrescriptionMoreInfo(
    isExpanded: Boolean = false,
    resultResponse: List<ResultResponse> = listOf(),
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically(
            tween(1000)
        ) + fadeIn(
            tween(1000)
        ),
        exit = shrinkVertically(
            tween(2000)
        ) + fadeOut(
            tween(2000)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_side_margin),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SightUPTheme.spacing.spacing_side_margin)
        ) {
            resultResponse.forEach {
                PrescriptionInfoLine(
                    label = it.testTitle,
                    leftValue = it.left,
                    rightValue = it.right,
                )
            }

            Column {
                Text(
                    text = "Notes",
                    style = SightUPTheme.textStyles.body2,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier
                )
                Spacer(Modifier.height(SightUPTheme.spacing.spacing_2xs))
                Text(
                    text = "Lens Materials: Plastic (CR-39)\nLens Coatings: Blue Light Filter Coating",
                    style = SightUPTheme.textStyles.body,
                    color = SightUPTheme.sightUPColors.text_primary,
                    modifier = Modifier
                )
            }
        }
    }
}