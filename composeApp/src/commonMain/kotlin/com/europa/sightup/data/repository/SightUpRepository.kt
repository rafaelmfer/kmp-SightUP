package com.europa.sightup.data.repository

import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.local.getObject
import com.europa.sightup.data.network.NetworkClient.JWT_TOKEN
import com.europa.sightup.data.remote.api.SightUpApiService
import com.europa.sightup.data.remote.request.ProfileRequest
import com.europa.sightup.data.remote.request.assessment.DailyCheckRequest
import com.europa.sightup.data.remote.request.auth.LoginRequest
import com.europa.sightup.data.remote.request.auth.LoginWithProviderRequest
import com.europa.sightup.data.remote.request.prescription.AddPrescriptionRequest
import com.europa.sightup.data.remote.request.visionHistory.ResultRequest
import com.europa.sightup.data.remote.request.visionHistory.VisionHistoryRequest
import com.europa.sightup.data.remote.response.AddPrescriptionResponse
import com.europa.sightup.data.remote.response.DailyCheckInResponse
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.remote.response.ProfileResponse
import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.UserResponse
import com.europa.sightup.data.remote.response.assessment.DailyCheckResponse
import com.europa.sightup.data.remote.response.auth.LoginEmailResponse
import com.europa.sightup.data.remote.response.auth.LoginResponse
import com.europa.sightup.data.remote.response.visionHistory.HistoryTestResponse
import com.europa.sightup.data.remote.response.visionHistory.UserHistoryResponse
import com.europa.sightup.data.remote.response.visionHistory.VisionHistoryResponse
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

    fun getUserInfo(): UserResponse? {
        val user = kVaultStorage.getObject<UserResponse>(USER_INFO)
        return user
    }

    fun checkEmail(email: String): Flow<LoginEmailResponse> {
        return flow {
            val response = api.loginCheckEmail(email)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getAllDay(): Flow<List<DailyCheckInResponse>> {
        val userInfo = getUserInfo()

        return flow {
            val request = DailyCheckRequest("",email = userInfo?.email ?: "")
            val response = api.getAllDay(request)

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

    fun getUser(): Flow<UserResponse> {
        val userInfo = getUserInfo()
        return flow {
            val user = userInfo?.email ?: userInfo?.id ?: ""
            val response = api.getUser(user)

            saveUserInfo(response)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun setupProfile(
        userName: String? = null,
        birthday: Int? = null,
        gender: String? = null,
        goal: String? = null,
        frequency: String? = null,
        unit: String? = null,
    ): Flow<ProfileResponse> {
        val userInfo = getUserInfo()
        val request = ProfileRequest(
            userId = userInfo?.id,
            email = userInfo?.email ?: "",
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

    fun postUserTestsResult(
        appTest: Boolean,
        testId: String,
        testTitle: String,
        result: ResultRequest,
    ): Flow<VisionHistoryResponse> {

        val userInfo = getUserInfo()
        val request = VisionHistoryRequest(
            userId = userInfo?.id ?: "",
            userEmail = userInfo?.email ?: "",
            appTest = appTest,
            testId = testId,
            testTitle = testTitle,
            result = result
        )

        return flow {
            val response = api.saveTestResult(request)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getUserVisionHistory(): Flow<UserHistoryResponse> {
        val userInfo = getUserInfo()
        return flow {
            val user = userInfo?.email ?: userInfo?.id ?: ""
            val response = api.getUserTests(user)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getUserLatestVisionHistory(): Flow<HistoryTestResponse?> {
        val userInfo = getUserInfo()
        return flow {
            val user = userInfo?.email ?: userInfo?.id ?: ""
            val response = api.getUserTests(user)

            emit(response.tests.firstOrNull())
        }.flowOn(Dispatchers.IO)
    }

    fun addPrescription(request: AddPrescriptionRequest): Flow<AddPrescriptionResponse> {
        val userInfo = getUserInfo()
        request.userId = userInfo?.id ?: ""
        request.userEmail = userInfo?.email ?: ""

        return flow {
            val response = api.addPrescription(request)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTasks(): List<TaskResponse> {
        return api.getTasks()
    }

    fun getTests(): Flow<List<TestResponse>> {
        return flow {
            val response = api.getTests()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getExercises(): List<ExerciseResponse> {
        return api.getExercises()
    }

    fun saveDailyCheck(request: DailyCheckRequest): Flow<DailyCheckResponse> {
        val userInfo = getUserInfo()
        request.email = userInfo?.email ?: ""

        return flow {
            val response = api.saveDailyCheck(request)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}