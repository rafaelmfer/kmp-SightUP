/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.europa.sightup.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.europa.sightup.presentation.ui.theme.ButtonStyle
import com.europa.sightup.presentation.ui.theme.SDSButtonWear
import com.europa.sightup.presentation.ui.theme.SDSControlEyeClockWear
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.utils.WearMessageHelper

class WearMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SightUPTheme {
                // ControlEScreen()
                ControlAstigmatismScreen()
            }
        }
    }

    fun show(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
fun ControlAstigmatismScreen() {
    val context = LocalContext.current
    val activity = LocalContext.current as WearMainActivity
    val wearMessageHelper = remember { WearMessageHelper(context) }
    val scope = rememberCoroutineScope()

    val MESSAGE_PATH_ACTIONS = "/actions"

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SDSControlEyeClockWear(
            buttonOneOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 1.toString(), scope)
                activity.show("Button One Clicked")
            },
            buttonTwoOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 2.toString(), scope)
                activity.show("Button Two Clicked")
            },
            buttonThreeOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 3.toString(), scope)
                activity.show("Button Three Clicked")
            },
            buttonFourOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 4.toString(), scope)
            },
            buttonFiveOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 5.toString(), scope)
            },
            buttonSixOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 6.toString(), scope)
            },
            buttonSevenOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 7.toString(), scope)
            },
            buttonEightOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 8.toString(), scope)
            },
            buttonNineOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 9.toString(), scope)
            },
            buttonTenOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 10.toString(), scope)
            },
            buttonElevenOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 11.toString(), scope)
            },
            buttonTwelveOnClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, 12.toString(), scope)
            },
        )

        SDSButtonWear(
            buttonStyle = ButtonStyle.OUTLINED,
            text = "All lines same",
            onClick = {
                wearMessageHelper.sendWearMessage(MESSAGE_PATH_ACTIONS, "all lines", scope)
            },
        )

    }
}


@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ControlEScreen()
}