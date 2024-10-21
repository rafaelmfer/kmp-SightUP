package com.europa.sightup.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.switch_container


@Composable
fun JoinInBlockScreen(
    navController: NavHostController
) {
    SightUPTheme {
        JoinInBlockLoadScreen(navController)
    }
}


@Composable
fun JoinInBlockLoadScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.End)
                    .clickable {

                    }
            )

            //Spacer(modifier = Modifier.weight(ONE_FLOAT))
            Spacer(modifier = Modifier.height(53.dp))

            Image(
                painter = painterResource(Res.drawable.switch_container),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(189.dp)
            )

            //Spacer(modifier = Modifier.weight(ONE_FLOAT))
            Spacer(modifier = Modifier.height(53.dp))

            Text(
                text = "We need you to sign-in or",
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "sign-up to continue.",
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Join now and unlock all the features we offer.",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Four exercises designed help your eyes",
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            val items = listOf("Relieve dryness and eye strain.",
                "Enhance visual perception and focus.",
                "Improve color differentiation and stretch your eye muscles.")
            LazyColumn {
                items(items) { item ->
                    Text(
                        text = "• $item",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(start = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Plus, you`ll find vision tests to assess conditions like myopia and astigmatism, and a secure space to save your vision history.",
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Ação do botão Sign Up
                },
                shape = RectangleShape,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinhando ao centro horizontalmente
                    .fillMaxWidth()

                    .padding(bottom = 16.dp, top = 100.dp) // Espaço do botão em relação à parte inferior
            ) {
                Text("Sign Up")
            }

            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    pushStringAnnotation(tag = "signIn", annotation = "SignIn")
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("Sign in")
                    }
                    pop()
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {
                        navController.navigate("sign_in_screen")
                    }
            )
        }
    }
}