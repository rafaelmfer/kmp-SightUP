package com.europa.sightup.data.repository

import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.network.NetworkClient.JWT_TOKEN
import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.remote.request.ProfileRequest
import com.europa.sightup.data.remote.request.auth.LoginRequest
import com.europa.sightup.data.remote.request.auth.LoginWithProviderRequest
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.remote.response.ProfileResponse
import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.UserResponse
import com.europa.sightup.data.remote.response.auth.LoginEmailResponse
import com.europa.sightup.data.remote.response.auth.LoginResponse
import com.europa.sightup.utils.USER_INFO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SightUpRepository(
    private val api: SightUpApiService,
    private val kVaultStorage: KVaultStorage,
) {
    private fun saveUserInfo(user: UserResponse) {
        val userString = Json.encodeToString(user)
        kVaultStorage.set(USER_INFO, userString)
    }

    private fun getUserInfo(): UserResponse {
        val userString = kVaultStorage.get(USER_INFO)
        return Json.decodeFromString(userString)
    }

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

            saveUserInfo(response.user)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun doLoginWithProvider(idToken: String): Flow<LoginResponse> {
        val request = LoginWithProviderRequest(idToken)

        return flow {
            val response = api.doLoginWithProvider(request)
            kVaultStorage.set(JWT_TOKEN, response.accessToken)

            saveUserInfo(response.user)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun setupProfile(
        userId: String? = null,
        email: String? = null,
        userName: String? = null,
        birthday: Int? = null,
        gender: String? = null,
        goal: String? = null,
        frequency: String? = null,
        unit: String? = null,
    ): Flow<ProfileResponse> {
        val request = ProfileRequest(
            userId = userId,
            email = email!!,
            userName = userName,
            birthday = birthday,
            gender = gender,
            goal = goal,
            frequency = frequency,
            unit = unit,
        )

        return flow {
            val response = api.setupProfile(request)
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