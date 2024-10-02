package com.europa.sightup.data.repository

import com.europa.sightup.data.remote.api.JsonPlaceholderApiService
import com.europa.sightup.data.remote.response.PostResponse

class JsonPlaceholderRepository(private val api: JsonPlaceholderApiService) {

    suspend fun getPosts(): List<PostResponse> {
        return api.getPosts()
    }
}