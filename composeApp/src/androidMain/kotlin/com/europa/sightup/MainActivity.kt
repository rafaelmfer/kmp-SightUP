package com.europa.sightup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.europa.sightup.data.remote.response.PostResponse
import com.europa.sightup.utils.PostsScreen
import com.europa.sightup.utils.PostsWithState
import com.europa.sightup.utils.UIState
import com.mmk.kmpnotifier.permission.permissionUtil
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {

    private val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionUtil.askNotificationPermission()
        setContent {
//            App()
            PostsWithState()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppAndroidPreview() {
    KoinApplication(application = {}) {
        App()
    }
}

@Preview(showSystemUi = true)
@Composable
fun PostsPreview() {
    KoinApplication(
        application = {
            // your preview config here
        }
    ) {
        val posts = listOf(
            PostResponse(userId = 1, id = 1, title = "Title", body = "Body"),
            PostResponse(userId = 2, id = 2, title = "Title 2", body = "Body 2"),
            PostResponse(userId = 3, id = 3, title = "Title 3", body = "Body 3"),
            PostResponse(userId = 4, id = 4, title = "Title 4", body = "Body 4")
        )
        PostsScreen(UIState.Success(posts))
    }
}

