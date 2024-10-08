package com.europa.sightup.data.remote.api

import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse
import de.jensklingenberg.ktorfit.http.GET

interface SightUpApiService {
    @GET("tasks/allTasks")
    suspend fun getTasks(): List<TaskResponse>

    @GET("tasks/Tests")
    suspend fun getTests(): List<TestResponse>
}