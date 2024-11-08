package com.europa.sightup.presentation.screens.prescription

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.europa.sightup.data.remote.request.prescription.AddPrescriptionInfoRequest
import com.europa.sightup.data.remote.request.prescription.AddPrescriptionRequest
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSInput
import com.europa.sightup.presentation.designsystem.components.SDSLocationBadge
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.clickableWithRipple
import com.europa.sightup.utils.getTodayDateString
import com.europa.sightup.utils.toFormattedDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.cancel
import sightupkmpapp.composeapp.generated.resources.contact_lenses
import sightupkmpapp.composeapp.generated.resources.glasses
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.naked_eye
import sightupkmpapp.composeapp.generated.resources.save
import sightupkmpapp.composeapp.generated.resources.schedule

@Composable
fun AddPrescriptionContent(
    text: String = "Glasses",
    onClickCancel: () -> Unit = {},
    onClickSave: (AddPrescriptionRequest) -> Unit = {},
) {
    val prescriptionType = remember { PrescriptionType.fromString(text) }
    val icon = remember {
        when (prescriptionType) {
            PrescriptionType.VISION -> Res.drawable.naked_eye
            PrescriptionType.GLASSES -> Res.drawable.glasses
            PrescriptionType.CONTACT_LENSES -> Res.drawable.contact_lenses
        }
    }

    val dateLabel = remember {
        when (prescriptionType) {
            PrescriptionType.VISION -> "Vision Test"
            PrescriptionType.GLASSES,
            PrescriptionType.CONTACT_LENSES,
                -> "Prescription Date"
        }
    }

    var prescriptionDate by remember { mutableStateOf(getTodayDateString()) }

    var manufacturer by remember { mutableStateOf("Coopervision") }
    var productName by remember { mutableStateOf("Biofinity Toric 6pk") }
    var replacement by remember { mutableStateOf("1 month daily wear") }

    var visualAcuityLeft by remember { mutableStateOf("20/200") }
    var visualAcuityRight by remember { mutableStateOf("20/200") }

    var astigmatismLeft by remember { mutableStateOf("180") }
    var astigmatismRight by remember { mutableStateOf("180") }

    var sphLeft by remember { mutableStateOf("-3.50") }
    var sphRight by remember { mutableStateOf("-3.50") }

    var cylLeft by remember { mutableStateOf("-0.75") }
    var cylRight by remember { mutableStateOf("-0.75") }

    var pdLeft by remember { mutableStateOf("-0.75") }
    var pdRight by remember { mutableStateOf("-0.75") }

    var addLeft by remember { mutableStateOf("-0.75") }
    var addRight by remember { mutableStateOf("-0.75") }

    var prismLeft by remember { mutableStateOf("-0.75") }
    var prismRight by remember { mutableStateOf("-0.75") }

    var baseLeft by remember { mutableStateOf("-0.75") }
    var baseRight by remember { mutableStateOf("-0.75") }

    var notes by remember {
        mutableStateOf(
            "Lens Materials: Plastic (CR-39)\n" +
                "Lens Coatings: Blue Light Filter Coating"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(SightUPTheme.sizes.size_8))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(SightUPTheme.sizes.size_48)
                    .padding(
                        top = SightUPTheme.spacing.spacing_xs,
                        bottom = SightUPTheme.spacing.spacing_xs,
                    ),
            )
            Text(
                text = prescriptionType.title,
                style = SightUPTheme.textStyles.h5,
                color = SightUPTheme.sightUPColors.text_primary
            )
            if (prescriptionType == PrescriptionType.VISION) {
                Spacer(Modifier.width(SightUPTheme.spacing.spacing_base))
                SDSLocationBadge(false)
            }
        }
        DatePickerField(label = dateLabel, initialDate = prescriptionDate, onDateChange = { prescriptionDate = it })

        if (prescriptionType == PrescriptionType.CONTACT_LENSES) {
            Spacer(Modifier.height(SightUPTheme.sizes.size_16))
            DatePickerField(label = "Expiration", initialDate = "2025-01-12T00:00:00.000Z".toFormattedDate())
            Spacer(Modifier.height(SightUPTheme.sizes.size_24))
            HorizontalDivider(color = SightUPTheme.sightUPColors.divider)
            Spacer(Modifier.height(SightUPTheme.sizes.size_24))
            Column(
                verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base),
                modifier = Modifier.fillMaxWidth()
            ) {
                ManufactureInformation("Manufacture", manufacturer, onValueChange = { manufacturer = it })
                ManufactureInformation("Product Name", productName, onValueChange = { productName = it })
                ManufactureInformation("Replacement", replacement, onValueChange = { replacement = it })
            }
        }

        Spacer(modifier = Modifier.height(SightUPTheme.sizes.size_24))
        HorizontalDivider(color = SightUPTheme.sightUPColors.divider)
        Spacer(Modifier.height(SightUPTheme.sizes.size_24))
        PrescriptionInputWithTitle(
            label = "Visual Acuity",
            leftValue = visualAcuityLeft,
            rightValue = visualAcuityRight,
            onLeftValueChange = { visualAcuityLeft = it },
            onRightValueChange = { visualAcuityRight = it }
        )
        PrescriptionInputRow(
            label = "Astigmatism",
            leftValue = astigmatismLeft,
            rightValue = astigmatismRight,
            onLeftValueChange = { astigmatismLeft = it },
            onRightValueChange = { astigmatismRight = it }
        )
        PrescriptionInputRow(
            label = "SPH",
            leftValue = sphLeft,
            rightValue = sphRight,
            onLeftValueChange = { sphLeft = it },
            onRightValueChange = { sphRight = it }
        )
        PrescriptionInputRow(
            label = "CYL",
            leftValue = cylLeft,
            rightValue = cylRight,
            onLeftValueChange = { cylLeft = it },
            onRightValueChange = { cylRight = it }
        )
        PrescriptionInputRow(
            label = "PD",
            leftValue = pdLeft,
            rightValue = pdRight,
            onLeftValueChange = { pdLeft = it },
            onRightValueChange = { pdRight = it }
        )
        PrescriptionInputRow(
            label = "ADD",
            leftValue = addLeft,
            rightValue = addRight,
            onLeftValueChange = { addLeft = it },
            onRightValueChange = { addRight = it }
        )
        PrescriptionInputRow(
            label = "Prism",
            leftValue = prismLeft,
            rightValue = prismRight,
            onLeftValueChange = { prismLeft = it },
            onRightValueChange = { prismRight = it }
        )
        PrescriptionInputRow(
            label = "Base",
            leftValue = baseLeft,
            rightValue = baseRight,
            onLeftValueChange = { baseLeft = it },
            onRightValueChange = { baseRight = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SDSInput(
            value = notes,
            onValueChange = { notes = it },
            label = "Note",
            hint = "Notes",
            fullWidth = true,
            singleLine = false,
            minLines = 2
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SDSButton(
                text = stringResource(Res.string.cancel),
                onClick = onClickCancel,
                buttonStyle = ButtonStyle.OUTLINED
            )
            SDSButton(
                text = stringResource(Res.string.save),
                onClick = {
                    val prescription = AddPrescriptionRequest(
                        userId = "",
                        userEmail = "",
                        prescriptionType = prescriptionType.title,
                        appTest = false,
                        prescriptionDate = prescriptionDate,
                        manufacturer = manufacturer,
                        productName = productName,
                        replacement = replacement,
                        prescriptionInfo = listOf(
                            AddPrescriptionInfoRequest("Visual Acuity", left = visualAcuityLeft, right = visualAcuityRight),
                            AddPrescriptionInfoRequest("Astigmatism", left = astigmatismLeft, right = astigmatismRight),
                            AddPrescriptionInfoRequest("SPH", left = sphLeft, right = sphRight),
                            AddPrescriptionInfoRequest("CYL", left = cylLeft, right = cylRight),
                            AddPrescriptionInfoRequest("PD", left = pdLeft, right = pdRight),
                            AddPrescriptionInfoRequest("ADD", left = addLeft, right = addRight),
                            AddPrescriptionInfoRequest("Prism", left = prismLeft, right = prismRight),
                            AddPrescriptionInfoRequest("Base", left = baseLeft, right = baseRight)
                        ),
                        notes = notes
                    )
                    onClickSave(prescription)
                }
            )
        }
    }
}

@Composable
private fun DatePickerField(
    label: String,
    initialDate: String,
    onDateChange: (String) -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = SightUPTheme.textStyles.body2,
            color = SightUPTheme.sightUPColors.text_primary
        )
        Row(
            modifier = Modifier
                .clip(SightUPTheme.shapes.small)
                .background(SightUPTheme.sightUPColors.background_default)
                .border(
                    width = SightUPBorder.Width.sm,
                    color = SightUPTheme.sightUPColors.border_default,
                    shape = SightUPTheme.shapes.small
                )
                .clickableWithRipple {
                    //TODO: Implement Date Picker
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = initialDate,
                style = SightUPTheme.textStyles.body,
                color = SightUPTheme.sightUPColors.text_primary
            )
            Icon(
                modifier = Modifier.size(SightUPTheme.sizes.size_16),
                painter = painterResource(Res.drawable.schedule),
                contentDescription = "Calendar",
                tint = SightUPTheme.sightUPColors.text_primary
            )
        }
    }
}

@Composable
private fun ManufactureInformation(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = SightUPTheme.textStyles.body2,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier
        )
        SDSInput(
            value = value,
            onValueChange = onValueChange,
            fullWidth = false,
            textAlign = TextAlign.End,
            modifier = Modifier
                .width(180.dp)
        )
    }
}

@Composable
private fun PrescriptionInputWithTitle(
    label: String,
    leftValue: String,
    rightValue: String,
    onLeftValueChange: (String) -> Unit,
    onRightValueChange: (String) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val (inputDescription, labelLeft, inputLeft, labelRight, inputRight) = createRefs()
        Text(
            text = "Left",
            style = SightUPTheme.textStyles.body2,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier.constrainAs(labelLeft) {
                top.linkTo(parent.top)
                start.linkTo(inputLeft.start)
                end.linkTo(inputLeft.end)
            }
        )
        Text(
            text = "Right",
            style = SightUPTheme.textStyles.body2,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier.constrainAs(labelRight) {
                top.linkTo(parent.top)
                start.linkTo(inputRight.start)
                end.linkTo(inputRight.end)
            }
        )
        Row(
            modifier = Modifier.constrainAs(inputDescription) {
                top.linkTo(inputLeft.top)
                bottom.linkTo(inputLeft.bottom)
                start.linkTo(parent.start)
                end.linkTo(inputLeft.start)
                width = Dimension.fillToConstraints
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
        SDSInput(
            value = leftValue,
            onValueChange = onLeftValueChange,
            fullWidth = false,
            textAlign = TextAlign.End,
            modifier = Modifier
                .width(92.dp)
                .constrainAs(inputLeft) {
                    top.linkTo(labelLeft.bottom, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(inputDescription.end)
                    end.linkTo(inputRight.start, margin = 16.dp)
                },
        )
        SDSInput(
            value = rightValue,
            onValueChange = onRightValueChange,
            fullWidth = false,
            textAlign = TextAlign.End,
            modifier = Modifier
                .width(92.dp)
                .constrainAs(inputRight) {
                    top.linkTo(labelRight.bottom, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(inputLeft.end)
                    end.linkTo(parent.end)
                    horizontalBias = 1f
                },
        )
    }
}

@Composable
private fun PrescriptionInputRow(
    label: String,
    leftValue: String,
    rightValue: String,
    onLeftValueChange: (String) -> Unit,
    onRightValueChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SightUPTheme.spacing.spacing_xs),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_2xs),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier,
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
        Spacer(Modifier.height(SightUPTheme.sizes.size_4))
        Row(
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base)
        ) {
            SDSInput(
                value = leftValue,
                onValueChange = onLeftValueChange,
                fullWidth = false,
                textAlign = TextAlign.End,
                modifier = Modifier.width(92.dp)
            )
            SDSInput(
                value = rightValue,
                onValueChange = onRightValueChange,
                fullWidth = false,
                textAlign = TextAlign.End,
                modifier = Modifier.width(92.dp)
            )
        }
    }
}