package com.europa.sightup.data.repository

import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse

class SightUpRepository (private val api: SightUpApiService) {

    suspend fun getTasks(): List<TaskResponse> {
        return api.getTasks()
    }

    suspend fun getTests(): List<TestResponse> {
        return api.getTests()
    }

    suspend fun getExercises(): List<ExerciseResponse> {
        return api.getExercises()
    }
}