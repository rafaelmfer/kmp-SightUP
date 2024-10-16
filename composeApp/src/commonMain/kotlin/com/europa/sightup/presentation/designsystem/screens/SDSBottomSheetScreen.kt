package com.europa.sightup.presentation.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles

@Composable
fun SDSBottomSheetScreen() {
    var isFullBottomSheetVisible by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { isFullBottomSheetVisible = !isFullBottomSheetVisible }) {
                Text(text = if (isFullBottomSheetVisible) "Fechar FullBottomSheet" else "Abrir FullBottomSheet")
            }

            Button(onClick = { isBottomSheetVisible = !isBottomSheetVisible }) {
                Text(text = if (isBottomSheetVisible) "Fechar BottomSheet" else "Abrir BottomSheet")
            }
        }
    }

    if (isFullBottomSheetVisible) {
        SDSBottomSheet(
            isVisible = isFullBottomSheetVisible,
            onDismiss = { isFullBottomSheetVisible = false },
            fullHeight = true,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Login or Signup", style = SightUPTheme.textStyles.h4)
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Continue")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text("Continue with Google")
                    }
                }
            },
        )
    }

    if (isBottomSheetVisible) {
        SDSBottomSheet(
            isVisible = isBottomSheetVisible,
            onDismiss = { isBottomSheetVisible = false },
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Login or Signup", style = SightUPTheme.textStyles.h4)
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Continue")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
        )
    }
}

