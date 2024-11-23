package com.europa.sightup.presentation.screens.prescription.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.data.remote.response.visionHistory.HistoryTestResponse
import com.europa.sightup.data.remote.response.visionHistory.ResultResponse
import com.europa.sightup.data.remote.response.visionHistory.UserHistoryResponse
import com.europa.sightup.presentation.designsystem.components.SDSFilterChip
import com.europa.sightup.presentation.designsystem.components.SDSLocationBadge
import com.europa.sightup.presentation.designsystem.components.SDSTopBar
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.sizes
import com.europa.sightup.presentation.ui.theme.layout.spacing
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.UIState
import com.europa.sightup.utils.clickableWithRipple
import com.europa.sightup.utils.toFormattedDate
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.drop_drow_arrow
import sightupkmpapp.composeapp.generated.resources.information

@Composable
fun PrescriptionHistory(
    navController: NavController? = null,
    viewModel: PrescriptionHistoryViewModel = koinViewModel<PrescriptionHistoryViewModel>(),
) {
    LaunchedEffect(Unit) {
        viewModel.getVisionHistory()
    }

    val state by viewModel.history.collectAsStateWithLifecycle()

    var selectedFilter by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.background_light)
            .verticalScroll(rememberScrollState())
    ) {
        SDSTopBar(
            title = "Vision History",
            iconLeftVisible = true,
            onLeftButtonClick = {
                navController?.popBackStack()
            },
            iconRightVisible = false,
            modifier = Modifier
                .padding(horizontal = SightUPTheme.spacing.spacing_xs)
        )

        FilterChips(
            selectedFilter = selectedFilter,
            onChipClick = { text ->
                selectedFilter = text
            }
        )

        when (state) {
            is UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Success -> {
                val history = (state as UIState.Success<UserHistoryResponse>).data

                val filteredHistory = when (selectedFilter) {
                    "SightUP" -> history.tests.filter { it.appTest }
                    "Clinic" -> history.tests.filter { !it.appTest }
                    else -> history.tests
                }
                HistoryList(filteredHistory)
            }

            is UIState.Error -> {
            }

            else -> {}
        }
    }
}

@Composable
private fun FilterChips(onChipClick: (String) -> Unit, selectedFilter: String) {
    val listOfFilters = listOf(
        "All",
        "Clinic",
        "SightUP",
    )
    LazyRow(
        contentPadding = PaddingValues(
            top = SightUPTheme.spacing.spacing_sm,
            start = SightUPTheme.spacing.spacing_side_margin,
            end = SightUPTheme.spacing.spacing_side_margin,
            bottom = SightUPTheme.spacing.spacing_base
        ),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(listOfFilters) {
            SDSFilterChip(
                text = it,
                isSelected = it == selectedFilter,
                onClick = { text ->
                    onChipClick(text)
                }
            )
            if (it != listOfFilters.last()) {
                Spacer(Modifier.width(SightUPTheme.spacing.spacing_xs))
            }
        }
    }
}

@Composable
private fun HistoryList(history: List<HistoryTestResponse>) {
    val isExpanded = remember { mutableStateListOf(*BooleanArray(history.size) { false }.toTypedArray()) }

    Column(
        verticalArrangement = Arrangement.spacedBy(SightUPTheme.spacing.spacing_base),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            .padding(bottom = SightUPTheme.spacing.spacing_md)
    ) {
        history.forEachIndexed { index, test ->
            ExpandableHistoryItem(
                title = test.date.toFormattedDate(),
                appTested = test.appTest,
                results = test.result,
                isExpanded = isExpanded[index],
                onExpandedChange = { isExpanded[index] = it }
            )
        }
    }
}

@Composable
private fun ExpandableHistoryItem(
    title: String,
    appTested: Boolean,
    results: List<ResultResponse>,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SightUPTheme.shapes.small)
            .background(SightUPTheme.sightUPColors.background_default),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .defaultMinSize(
                    minHeight = SightUPTheme.sizes.size_48
                )
                .clickableWithRipple {
                    onExpandedChange(!isExpanded)
                }
                .padding(SightUPTheme.spacing.spacing_base),
        ) {
            Text(
                text = title,
                style = SightUPTheme.textStyles.body,
                color = SightUPTheme.sightUPColors.text_primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            SDSLocationBadge(appTested = appTested)
            Spacer(Modifier.width(SightUPTheme.spacing.spacing_sm))
            Icon(
                painter = painterResource(Res.drawable.drop_drow_arrow),
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier
                    .graphicsLayer(rotationZ = rotationAngle)
                    .size(SightUPTheme.sizes.size_16)
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SightUPTheme.spacing.spacing_base),
            ) {
                results.forEachIndexed { index, result ->
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
                PrescriptionNotes()
            }
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
private fun PrescriptionNotes(
    text: String = "",
) {
    if (text.isBlank()) return

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