package com.europa.sightup.test.tutorialscope

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.europa.sightup.test.components.TutorialText
import com.europa.sightup.test.state.TutorialBoxState
import com.europa.sightup.test.state.TutorialBoxTarget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TutorialBoxScope(
    private val state: TutorialBoxState,
) {

    /**
     * markForTutorial will adds a tag in the content to TutorialBox draws the [content]
     * using the order of the [index].
     *
     * But if [content] is not defined or is null, the TutorialBox will use the
     * [TutoriaBox(tutorialTarget: @Composable (index: Int) -> Unit]
     */
    fun Modifier.markForTutorial(
        index: Int,
        content: (@Composable BoxScope.() -> Unit)? = null,
        cornerRadius: Dp = 0.dp,
    ): Modifier = tutorialTarget(
        state = state,
        index = index,
        content = content,
        cornerRadius = cornerRadius
    )

    /**
     * markForTutorial will adds a tag in the content to TutorialBox draws a simple Compose
     * with [title] and [description] using the order of the [index].
     */
    fun Modifier.markForTutorial(
        index: Int,
        title: String,
        description: String? = null,
    ): Modifier = markForTutorial(
        index = index,
        content = {
            TutorialText(title = title, description = description)
        }
    )

    @Composable
    internal fun TutorialCompose(
        state: TutorialBoxState,
        constraints: Constraints,
        onTutorialCompleted: () -> Unit,
        onTutorialIndexChanged: (Int) -> Unit,
        customTutorialTarget: @Composable (index: Int) -> Unit,
    ) {
        val coroutineScope = rememberCoroutineScope()

        TutorialFocusBox(currentContent = state.currentTarget)

        TutorialTarget(
            currentContent = state.currentTarget?.copy(
                content = { customTutorialTarget(state.currentTargetIndex) }
            ),
            constraints = constraints
        )

        TutorialClickHandler {
            coroutineScope.launch {
                onTutorialIndexChanged(state.currentTargetIndex)
                delay(500)
                state.currentTargetIndex++
                if (state.currentTargetIndex >= state.tutorialTargets.size) {
                    onTutorialCompleted()
                }
            }
        }
    }

    @Composable
    internal fun TutorialCompose(
        state: TutorialBoxState,
        constraints: Constraints,
        onTutorialCompleted: () -> Unit,
        onTutorialIndexChanged: (Int) -> Unit,
    ) {
        val coroutineScope = rememberCoroutineScope()

        TutorialFocusBox(currentContent = state.currentTarget)

        TutorialTarget(
            currentContent = state.currentTarget,
            constraints = constraints
        )

        TutorialClickHandler {
            coroutineScope.launch {
                onTutorialIndexChanged(state.currentTargetIndex)
                delay(500)
                state.currentTargetIndex++
                if (state.currentTargetIndex >= state.tutorialTargets.size) {
                    onTutorialCompleted()
                }
            }
        }
    }

    @Composable
    private fun TutorialFocusBox(currentContent: TutorialBoxTarget?) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = currentContent,
            transitionSpec = {
                fadeIn(tween(500)) togetherWith fadeOut(tween(500))
            }) { state ->
            state?.let { content ->
                Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
                    val cornerRadius = content.cornerRadius.toPx()
                    val offSetInRoot = content.coordinates.positionInRoot()
                    val contentSize = content.coordinates.size

                    val pathToClip = Path().apply {
                        addRoundRect(
                            RoundRect(
                                left = offSetInRoot.x,
                                top = offSetInRoot.y,
                                right = offSetInRoot.x + contentSize.width.toFloat(),
                                bottom = offSetInRoot.y + contentSize.height.toFloat(),
                                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                            )
                        )
                    }
                    clipPath(pathToClip, clipOp = ClipOp.Difference) {
                        drawRect(
                            SolidColor(
                                value = Color.Black.copy(alpha = 0.6f)
                            ),
                            topLeft = Offset(0f, 0f)
                        )
                    }
                })
            }
        }
    }

    @Composable
    private fun TutorialTarget(currentContent: TutorialBoxTarget?, constraints: Constraints) {
        currentContent?.let { tutorialContent ->
            val composeWidth = remember(tutorialContent) {
                tutorialContent.coordinates.size.width
            }
            val composeHeight = remember(tutorialContent) {
                tutorialContent.coordinates.size.height
            }
            val x = remember(tutorialContent) {
                (tutorialContent.coordinates.positionInRoot().x.toInt())
            }
            val y = remember(tutorialContent) {
                (tutorialContent.coordinates.positionInRoot().y.toInt())
            }

            var tutorialSize by remember {
                mutableStateOf(IntSize.Zero)
            }

            val xWithDisplacement by remember(x, composeWidth, tutorialSize) {
                derivedStateOf {
                    val displacement = calculateDisplacementToMid(
                        startX = x,
                        composeWidth = composeWidth,
                        tutorialComposeWidth = tutorialSize.width
                    )

                    val xWithDisplacement = x + displacement

                    if (xWithDisplacement < 0) 0
                    else if (xWithDisplacement + tutorialSize.width > constraints.maxWidth) xWithDisplacement
                    else xWithDisplacement
                }
            }

            val outOfBoundsStart by remember(xWithDisplacement) {
                mutableStateOf(xWithDisplacement < 0)
            }

            val outOfBoundsEnd by remember(xWithDisplacement) {
                mutableStateOf(xWithDisplacement + tutorialSize.width > constraints.maxWidth)
            }

            val outOfBoundsTop by remember(xWithDisplacement) {
                mutableStateOf(y < 0)
            }

            val outOfBoundsBottom by remember(xWithDisplacement) {
                mutableStateOf(y + tutorialSize.height > constraints.maxHeight)
            }

            val xToDraw by remember(xWithDisplacement, tutorialSize, constraints) {
                derivedStateOf {
                    val xSafeRight =
                        xWithDisplacement - ((xWithDisplacement + tutorialSize.width) - constraints.maxWidth)

                    val safeX = if (outOfBoundsStart) 0
                    else if (outOfBoundsEnd) xSafeRight
                    else xWithDisplacement

                    safeX
                }
            }

            val yToDraw by remember(y, tutorialSize, constraints) {
                derivedStateOf {
                    val spaceAbove = y // Espaço disponível acima do item destacado
                    val spaceBelow =
                        constraints.maxHeight - (y + composeHeight) // Espaço disponível abaixo do item destacado

                    if (spaceBelow >= tutorialSize.height + 16) {
                        // Espaço suficiente abaixo do item destacado
                        y + composeHeight + 16 // Posiciona o texto logo abaixo do item
                    } else if (spaceAbove >= tutorialSize.height + 16) {
                        // Espaço suficiente acima do item destacado
                        y - tutorialSize.height - 16 // Posiciona o texto logo acima do item
                    } else {
                        // Sem espaço suficiente acima ou abaixo, posiciona o mais próximo possível
                        if (spaceBelow > spaceAbove) {
                            y + composeHeight + 16 // Abaixo com o espaço disponível
                        } else {
                            y - tutorialSize.height - 16 // Acima com o espaço disponível
                        }
                    }
                }
            }

            val xAnimated = remember { Animatable(0f) }
            val yAnimated = remember { Animatable(0f) }
            var visible by remember(tutorialContent.index) { mutableStateOf(false) }

            LaunchedEffect(key1 = xToDraw, key2 = yToDraw) {
                xAnimated.animateTo(xToDraw.toFloat(), tween(50, delayMillis = 0))
                yAnimated.animateTo(yToDraw.toFloat(), tween(50, delayMillis = 0))
                visible = true
            }

            AnimatedVisibility(
                modifier = Modifier
                    .onSizeChanged { tutorialSize = it }
                    .offset {
                        IntOffset(
                            x = xAnimated.value.roundToInt(),
                            y = yAnimated.value.roundToInt()
                        )
                    },
                enter = fadeIn(tween(200, delayMillis = 100, easing = FastOutLinearInEasing)),
                exit = fadeOut(tween(50, delayMillis = 0)),
                visible = visible
            ) {
                if (visible) {
                    Box {
                        tutorialContent.content?.invoke(this)
                    }
                }
            }
        }
    }

    @Composable
    private fun TutorialClickHandler(onTutorialClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                    onClick = onTutorialClick
                )
        )
    }
}

internal fun calculateDisplacementToMid(
    startX: Int, composeWidth: Int, tutorialComposeWidth: Int,
): Int {
    val xMidComponent = startX + (composeWidth / 2)
    val xMidTutorial = startX + (tutorialComposeWidth / 2)
    var displacementToMid = kotlin.math.abs(xMidComponent - xMidTutorial)
    if (xMidComponent < xMidTutorial) displacementToMid *= -1
    return displacementToMid
}

internal fun Modifier.tutorialTarget(
    state: TutorialBoxState,
    index: Int,
    content: (@Composable BoxScope.() -> Unit)? = null,
): Modifier = onGloballyPositioned { coordinates ->
    state.tutorialTargets[index] = TutorialBoxTarget(
        index = index,
        coordinates = coordinates,
        content = content
    )
}

/**
 * markForTutorial will adds a tag in the content to TutorialBox draws the [content]
 * using the order of the [index].
 *
 * In some complex layouts you may need to pass [state] between layers
 *
 * But if [content] is not defined or is null, the TutorialBox will use the
 * [TutoriaBox(tutorialTarget: @Composable (index: Int) -> Unit]
 */
fun Modifier.markForTutorial(
    state: TutorialBoxState,
    index: Int,
    content: (@Composable BoxScope.() -> Unit)? = null,
    cornerRadius: Dp = 0.dp,
): Modifier = tutorialTarget(
    state = state,
    index = index,
    content = content,
    cornerRadius = cornerRadius
)

internal fun Modifier.tutorialTarget(
    state: TutorialBoxState,
    index: Int,
    content: (@Composable BoxScope.() -> Unit)? = null,
    cornerRadius: Dp = 0.dp,
): Modifier = onGloballyPositioned { coordinates ->
    state.tutorialTargets[index] = TutorialBoxTarget(
        index = index,
        coordinates = coordinates,
        content = content,
        cornerRadius = cornerRadius,
    )
}