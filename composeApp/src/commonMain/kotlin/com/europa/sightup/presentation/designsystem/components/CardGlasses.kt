package com.europa.sightup.presentation.designsystem.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.info

@Composable
fun CardGlasses(Card: String, CardName: String) {
    var isVisible by remember { mutableStateOf(true) }
    var isExpanded by remember { mutableStateOf(false) }
    var loadVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loadVisible = true
    }

    AnimatedVisibility(
        visible = loadVisible,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 3000)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 3000)
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                if (CardName === "Vision") {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Vision",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .border(
                                        1.dp,
                                        Color.Black,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .padding(
                                        start = 12.dp,
                                        end = 12.dp,
                                        top = 4.dp,
                                        bottom = 4.dp
                                    )
                            ) {
                                Text(
                                    text = "SightUP",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Vision Test on Sep 27, 2024",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                text = "Left",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W700,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Right",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W700,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Visual Acuity",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(Res.drawable.info),
                                    contentDescription = "Icon next to text",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "20/200",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "20/200",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "SPH",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(Res.drawable.info),
                                    contentDescription = "Icon next to text",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "-3.50",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "-0.75",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = Card,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Vision Test on Sep 27, 2024",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        if (Card === "ContactLenses") {
                            Text(
                                text = "Expiration is on Sep 27, 20244",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                        Text(
                            text = "You have no significant vision problems at this time.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W700,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        if (Card === "ContactLenses") {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Manufacture",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 8.dp).width(150.dp)
                                )
                                Text(
                                    text = "Coopervision",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W700,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Product Name",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 8.dp).width(150.dp)
                                )
                                Text(
                                    text = "Biofinity Toric 6pk",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W700,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Replacement",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 8.dp).width(150.dp)
                                )
                                Text(
                                    text = "1 month daily wear",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W700,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                text = "Left",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W700,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Right",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W700,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "SPH",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(Res.drawable.info),
                                    contentDescription = "Icon next to text",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "-3.50",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "-0.75",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "AXIS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(Res.drawable.info),
                                    contentDescription = "Icon next to text",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "-3.50",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "-0.75",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "CYL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(Res.drawable.info),
                                    contentDescription = "Icon next to text",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "-0.75",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "-0.75",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(
                                animationSpec = tween(durationMillis = 3000)
                            ) + fadeIn(
                                animationSpec = tween(durationMillis = 3000)
                            ),
                            exit = shrinkVertically(
                                animationSpec = tween(durationMillis = 3000)
                            ) + fadeOut(
                                animationSpec = tween(durationMillis = 3000)
                            )
                        ) {
                            Column(
                                modifier = Modifier

                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "PD",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.W400
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(Res.drawable.info),
                                            contentDescription = "Icon next to text",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "-0.75",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "-0.75",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "ADD",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.W400
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(Res.drawable.info),
                                            contentDescription = "Icon next to text",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "-0.75",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "-0.75",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "ADD",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.W400
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(Res.drawable.info),
                                            contentDescription = "Icon next to text",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "-075",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "-075",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Prism",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.W400
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(Res.drawable.info),
                                            contentDescription = "Icon next to text",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "-",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "-",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Base",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.W400
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(Res.drawable.info),
                                            contentDescription = "Icon next to text",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "-",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "-",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Text(
                                    text = "Note",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Lens Materials: Plastic (CR-39)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(bottom = 0.dp)
                                )
                                Text(
                                    text = "Lens Coatings: Blue Light Filter Coating",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                                Text(
                                    text = "Lens Coatings: Blue Light Filter Coating",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                            }
                        }
                        SDSButton(
                            if (isVisible) "View more" else "Close",
                            onClick = {
                                isVisible = !isVisible
                                isExpanded = !isExpanded

                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            buttonStyle = ButtonStyle.TEXT,
                            textStyle = SightUPTheme.textStyles.body.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            enabled = true,
                        )
                    }
                }
            }
        }
    }
}