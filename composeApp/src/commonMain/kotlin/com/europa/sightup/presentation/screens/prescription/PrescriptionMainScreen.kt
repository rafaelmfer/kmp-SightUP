package com.europa.sightup.presentation.screens.prescription

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.ButtonStyle
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.SDSButton
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.designsystem.components.hideBottomSheetWithAnimation
import com.europa.sightup.presentation.navigation.PrescriptionsScreens
import com.europa.sightup.presentation.navigation.TestScreens
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.SightUPBorder
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.ONE_FLOAT
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.isUserLoggedIn
import com.europa.sightup.utils.toFormattedDate
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.contact_lenses
import sightupkmpapp.composeapp.generated.resources.glasses
import sightupkmpapp.composeapp.generated.resources.information
import sightupkmpapp.composeapp.generated.resources.naked_eye
import sightupkmpapp.composeapp.generated.resources.vision

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionScreen(
    navController: NavController? = null,
    viewModel: PrescriptionMainViewModel = koinViewModel<PrescriptionMainViewModel>(),
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },
    )
    var addPrescriptionSheetVisibility by remember { mutableStateOf(BottomSheetEnum.HIDE) }

    var textBottomSheet by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    val userIsLogged = isUserLoggedIn
    val prescription by viewModel.prescription.collectAsState()
    val userPrescriptions by viewModel.userPrescription.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
    ) {
        SDSTopBar(
            title = "Prescriptions",
            iconLeftVisible = false,
            iconRightVisible = false,
            modifier = Modifier
        )

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        if (userIsLogged) {
            NextVisionTest(
                onClickOutlinedButton = {},
                onClickPrimaryButton = {
                    navController?.navigate(TestScreens.TestInit)
                },
            )
        } else {
            Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
            Text(
                text = "Please, login or sign up to check your prescriptions",
                style = SightUPTheme.textStyles.body,
                textAlign = TextAlign.Center,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))

        VisionPrescription(
            navController = navController,
            viewModel = viewModel,
            onClickEdit = {
                textBottomSheet = PrescriptionType.VISION.title
                addPrescriptionSheetVisibility = BottomSheetEnum.SHOW
            },
        )

        when (userPrescriptions) {
            is UIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val userPrescription = (userPrescriptions as UIState.Success).data.eyeWear

                if (userPrescription?.glasses == null) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    CardAddPrescription(
                        text = PrescriptionType.GLASSES.title,
                        onClick = {
                            textBottomSheet = PrescriptionType.GLASSES.title
                            addPrescriptionSheetVisibility = BottomSheetEnum.SHOW
                        }
                    )
                }
                if (userPrescription?.glasses != null) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    PrescriptionCard(
                        text = PrescriptionType.GLASSES.title,
                        prescription = userPrescription.glasses,
                        onClickVisionHistory = {
                        },
                        onClickEdit = {
                            textBottomSheet = PrescriptionType.GLASSES.title
                            addPrescriptionSheetVisibility = BottomSheetEnum.SHOW
                        }
                    )
                }
                if (userPrescription?.contactLens == null) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    CardAddPrescription(
                        text = PrescriptionType.CONTACT_LENSES.title,
                        onClick = {
                            textBottomSheet = PrescriptionType.CONTACT_LENSES.title
                            addPrescriptionSheetVisibility = BottomSheetEnum.SHOW
                        }
                    )
                }
                if (userPrescription?.contactLens != null) {
                    Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
                    PrescriptionCard(
                        text = PrescriptionType.CONTACT_LENSES.title,
                        prescription = userPrescription.contactLens,
                        onClickVisionHistory = {
                        },
                        onClickEdit = {
                            textBottomSheet = PrescriptionType.CONTACT_LENSES.title
                            addPrescriptionSheetVisibility = BottomSheetEnum.SHOW
                        }
                    )
                }

                Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
            }

            is UIState.Error -> {
            }

            else -> {}
        }
    }

    fun dismissSheet() {
        scope.hideBottomSheetWithAnimation(
            sheetState = sheetState,
            onBottomSheetVisibilityChange = {
                addPrescriptionSheetVisibility = BottomSheetEnum.HIDE
            },
            onFinish = {
                viewModel.resetPrescriptionState()
            }
        )
    }

    SDSBottomSheet(
        title = "Add Prescription",
        isDismissible = true,
        expanded = addPrescriptionSheetVisibility,
        onExpandedChange = {
            addPrescriptionSheetVisibility = it
        },
        sheetState = sheetState,
        fullHeight = true,
        iconRightVisible = false,
        onDismiss = {
            dismissSheet()
        },
        sheetContent = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = SightUPTheme.spacing.spacing_base,
                        end = SightUPTheme.spacing.spacing_base,
                        bottom = SightUPTheme.spacing.spacing_md,
                    )
            ) {
                AddPrescriptionContent(
                    text = textBottomSheet,
                    onClickCancel = {
                        dismissSheet()
                    },
                    onClickSave = { request ->
                        viewModel.addPrescription(request)
                    }
                )
                when (prescription) {
                    is PrescriptionRecordUIState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .zIndex(100f)
                        )
                    }

                    is PrescriptionRecordUIState.PrescriptionAdded -> {
                        dismissSheet()
                        viewModel.getUser()
                        viewModel.getLatestRecords()
                    }

                    is PrescriptionRecordUIState.Error -> {
                    }

                    else -> {

                    }
                }
            }
        }
    )
}

@Composable
private fun VisionPrescription(
    navController: NavController? = null,
    viewModel: PrescriptionMainViewModel,
    onClickEdit: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        viewModel.getLatestRecords()
    }

    val records by viewModel.records.collectAsStateWithLifecycle()

    when (records) {
        is VisionRecordUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is VisionRecordUIState.NoData -> {
            CardAddPrescription(PrescriptionType.VISION.title)
        }

        is VisionRecordUIState.VisionRecord -> {
            PrescriptionCard(
                text = PrescriptionType.VISION.title,
                history = (records as VisionRecordUIState.VisionRecord).history,
                onClickVisionHistory = {
                    navController?.navigate(PrescriptionsScreens.PrescriptionsHistory)
                },
                onClickEdit = onClickEdit
            )
        }

        is VisionRecordUIState.Error -> {
        }

        else -> {}
    }
}

@Composable
private fun NextVisionTest(
    onClickOutlinedButton: () -> Unit = {},
    onClickPrimaryButton: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
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
            .padding(16.dp)
            .then(modifier)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Next SightUP Vision Test",
                style = SightUPTheme.textStyles.body,
                color = SightUPTheme.sightUPColors.text_primary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(Res.drawable.information),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
            )
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_xs))
        Text(
            text = "2024-12-16T00:00:00.000Z".toFormattedDate("MMM dd, yyyy"),
            style = SightUPTheme.textStyles.large,
            color = SightUPTheme.sightUPColors.text_primary,
            modifier = Modifier
        )
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_base))
        Row(
            horizontalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SDSButton(
                text = "Reschedule",
                onClick = onClickOutlinedButton,
                buttonStyle = ButtonStyle.OUTLINED,
                modifier = Modifier
                    .weight(ONE_FLOAT),
            )
            SDSButton(
                text = "Vision Test Now",
                onClick = onClickPrimaryButton,
                modifier = Modifier
                    .weight(ONE_FLOAT),
            )
        }
    }
}

@Composable
private fun CardAddPrescription(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val icon = remember {
        when (text) {
            PrescriptionType.VISION.title -> Res.drawable.naked_eye
            PrescriptionType.GLASSES.title -> Res.drawable.glasses
            PrescriptionType.CONTACT_LENSES.title -> Res.drawable.contact_lenses
            else -> Res.drawable.vision
        }
    }

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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
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
        }
        Spacer(Modifier.height(SightUPTheme.spacing.spacing_md))
        SDSButton(
            text = "Add Prescription",
            onClick = onClick,
            buttonStyle = ButtonStyle.OUTLINED,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}