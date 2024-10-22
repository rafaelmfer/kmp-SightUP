package com.europa.sightup.data.repository

import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.network.NetworkClient.JWT_TOKEN
import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.remote.request.auth.LoginRequest
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.auth.LoginEmailResponse
import com.europa.sightup.data.remote.response.auth.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SightUpRepository(
    private val api: SightUpApiService,
    private val kVaultStorage: KVaultStorage,
) {

    fun checkEmail(email: String): Flow<LoginEmailResponse> {
        return flow {
            val response = api.loginCheckEmail(email)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun doLogin(email: String, password: String): Flow<LoginResponse> {
        val request = LoginRequest(email, password)

        return flow {
            val response = api.doLogin(request)
            kVaultStorage.set(JWT_TOKEN, response.accessToken)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

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