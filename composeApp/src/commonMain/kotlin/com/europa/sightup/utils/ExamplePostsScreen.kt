package com.europa.sightup.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.local.get
import com.europa.sightup.data.remote.response.PostResponse
import com.europa.sightup.presentation.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun PostsWithState() {
    MaterialTheme {
        val kVaultStorage = koinInject<KVaultStorage>()

        val viewModel = koinViewModel<MainViewModel>()
        LaunchedEffect(Unit) {
            viewModel.getProducts()
        }
        val state by viewModel.products.collectAsStateWithLifecycle()

        PostsScreen(state, kVaultStorage = kVaultStorage)
    }
}

@Composable
fun PostsScreen(state: UIState<List<PostResponse>>, kVaultStorage: KVaultStorage? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ActionButtons(
            onSaveClick = {
                val token = "Token dkfjhksjdhfjdsfjksdbkjfbskjdbfnds"
                kVaultStorage?.save("token", token)
                println("Token saved: $token")
                println()
            },
            onGetClick = {
                val tokenRetrieved = kVaultStorage?.get("token")
                println("Token retrieved: $tokenRetrieved")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        when (state) {
            is UIState.InitialState -> {}
            is UIState.Loading -> {
                CircularProgressIndicator()
            }
            is UIState.Error -> {
                Text(text = "Error: ${state.message}")
            }
            is UIState.Success -> {
                // Exibe os dados
                val posts = state.data
                PostList(
                    posts = posts,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ActionButtons(
    onSaveClick: () -> Unit,
    onGetClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onSaveClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = "Save")
        }
        Button(
            onClick = onGetClick,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Get")
        }
    }
}

@Composable
fun PostList(posts: List<PostResponse>, modifier: Modifier = Modifier) {
    SectionHeader(title = "POSTS LIST")
    LazyColumn(modifier = modifier) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun PostItem(post: PostResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = post.body,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "ID: ${post.id}",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )
        }
    }
}