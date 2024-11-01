/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.europa.sightup.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.tooling.preview.devices.WearDevices
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.utils.WearMessageHelper

class WearMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SightUPTheme {
                ControlEScreen()
            }
        }
    }
}

@Composable
fun ControlEScreen() {
    val context = LocalContext.current
    val wearMessageHelper = remember { WearMessageHelper(context) }
    val scope = rememberCoroutineScope()

    val MESSAGE_PATH_ACTIONS = "/actions"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SightUPTheme.sightUPColors.primary_200),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SDSControlEWear(
            upButtonOnClickResult = { wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "up", scope) },
            downButtonOnClickResult = { wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "down", scope) },
            leftButtonOnClickResult = { wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "left", scope) },
            rightButtonOnClickResult = { wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "right", scope) },
        )
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ControlEScreen()
}