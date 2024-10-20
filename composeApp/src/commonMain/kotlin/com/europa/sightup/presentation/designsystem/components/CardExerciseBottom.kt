package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.clock
import sightupkmpapp.composeapp.generated.resources.info




@Composable
fun CardExerciseBottom(
    audio: Boolean,
    typeExercise: String,
    duration: String,
    title: String,
    description: String,
    subDescriptionTitle: String,
    subDescription: ArrayList<String>,
    onClick: ()->Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp, 24.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box() {
                    Column() {
                        if (audio) {
                            Text(
                                text = typeExercise,
                                color = Color(0xFF235E86),
                                style = SightUPTheme.textStyles.body.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                        }
                        Text(
                            text = title,
                            style = SightUPTheme.textStyles.h2.copy(
                                fontSize = 26.sp
                            )
                        )
                    }
                }


                if (!audio) {
                    var isChecked by remember { mutableStateOf(false) }
                    CustomSwitch(
                        isChecked = isChecked,
                        onCheckedChange = { isChecked = it },
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(50.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Image(
                                painter = painterResource(Res.drawable.clock),
                                contentDescription = "Descrição da imagem",
                                modifier = Modifier
                                    .width(15.dp)
                                    .height(15.dp)
                                    .padding(start = 3.dp)
                            )


                            Text(
                                text = " " + duration,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                                fontSize = 12.sp,
                                color = Color(0xFF1E1E1E)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = description,
                style = SightUPTheme.textStyles.subtitle.copy(
                    fontWeight = FontWeight.Normal
                )
            )

            if (subDescriptionTitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = subDescriptionTitle,
                    style = SightUPTheme.textStyles.subtitle.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            if(subDescription.isNotEmpty()) {
                for(subDesc in subDescription){
                    Text(text = "• $subDesc")
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            SDSButton(
                "Start",
                onClick = onClick,
                buttonStyle = ButtonStyle.PRIMARY,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}