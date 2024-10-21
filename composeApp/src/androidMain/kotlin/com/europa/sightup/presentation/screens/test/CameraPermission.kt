package com.europa.sightup.presentation.screens.test

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

/**
 * Function to check if camera permission is granted
 */
fun isCameraPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.CAMERA
    ) == PermissionChecker.PERMISSION_GRANTED
}

/**
 * Composable to request camera permission.
 * Takes a mutable state to indicate whether permission is granted.
 */
@Composable
fun RequestCameraPermission(
    permissionGranted: MutableState<Boolean>,
    onPermissionResult: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted.value = isGranted
        onPermissionResult(isGranted)
    }

    LaunchedEffect(Unit) {
        if (!isCameraPermissionGranted(context)) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            permissionGranted.value = true
        }
    }
}
