package com.europa.sightup.presentation.screens.prescription

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.europa.sightup.presentation.components.CardVision
import com.europa.sightup.presentation.designsystem.components.CardGlasses
import com.europa.sightup.presentation.screens.exercise.ExerciseViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.edit


// ##################################################################################################
// NavigationGraph.kt --- devolver a rota do exercise e nao esquecer de apagar a rota do prescription
// ##################################################################################################

@Composable
fun PrescriptionScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<ExerciseViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getExercises()
    }
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp).verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Prescriptions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 45.dp)
        )
        Text(
            text = "You have no significant vision problems at this time.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEAECEE))
                .padding(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Next SightUP Vision Test",
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Sep 27, 2024",
                fontSize = 18.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(Res.drawable.edit),
                contentDescription = "Descrição da imagem",
                modifier = Modifier
                    .size(16.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.weight(1f)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(Color.White)

            ) {
                Text(
                    text = "Vision History",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
                    .border(
                        1.dp,
                        Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "Vision Test Now",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            }
        }
        CardVision()
        Spacer(modifier = Modifier.height(10.dp))
        CardGlasses("Glasses", "Glasses")
        Spacer(modifier = Modifier.height(10.dp))
        CardGlasses("ContactLenses", "ContactLenses")
        Spacer(modifier = Modifier.height(10.dp))
    }
}




