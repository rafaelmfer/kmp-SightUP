package com.europa.sightup.data.remote.api

import com.europa.sightup.data.remote.response.PostResponse
import de.jensklingenberg.ktorfit.http.GET

interface JsonPlaceholderApiService {

    @GET("/posts")
    suspend fun getPosts(): List<PostResponse>
}