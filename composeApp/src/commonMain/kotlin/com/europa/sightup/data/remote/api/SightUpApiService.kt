package com.europa.sightup.data.remote.api

import com.europa.sightup.data.remote.request.ProfileRequest
import com.europa.sightup.data.remote.request.assessment.DailyCheckRequest
import com.europa.sightup.data.remote.request.auth.LoginRequest
import com.europa.sightup.data.remote.request.auth.LoginWithProviderRequest
import com.europa.sightup.data.remote.request.prescription.AddPrescriptionRequest
import com.europa.sightup.data.remote.request.visionHistory.VisionHistoryRequest
import com.europa.sightup.data.remote.response.AddPrescriptionResponse
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.remote.response.ProfileResponse
import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.UserResponse
import com.europa.sightup.data.remote.response.assessment.DailyCheckResponse
import com.europa.sightup.data.remote.response.auth.LoginEmailResponse
import com.europa.sightup.data.remote.response.auth.LoginResponse
import com.europa.sightup.data.remote.response.visionHistory.UserHistoryResponse
import com.europa.sightup.data.remote.response.visionHistory.VisionHistoryResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface SightUpApiService {

    @POST("api/auth/loginEmail")
    suspend fun loginCheckEmail(
        @Query("email") email: String,
    ): LoginEmailResponse

    @POST("api/auth/login")
    suspend fun doLogin(
        @Body request: LoginRequest,
    ): LoginResponse

    @POST("api/auth/loginWithProvider")
    suspend fun doLoginWithProvider(
        @Body request: LoginWithProviderRequest,
    ): LoginResponse

    @GET("api/user/{user}")
    suspend fun getUser(
        @Path("user") user: String
    ): UserResponse

    @POST("api/user/setupProfile")
    suspend fun setupProfile(
        @Body request: ProfileRequest,
    ): ProfileResponse

    @POST("api/user/visionHistory")
    suspend fun saveTestResult(
        @Body request: VisionHistoryRequest,
    ): VisionHistoryResponse

    @GET("api/user/visionHistory/{user}")
    suspend fun getUserTests(
        @Path("user") user: String,
    ): UserHistoryResponse

    @POST("api/user/prescriptions")
    suspend fun addPrescription(
        @Body request: AddPrescriptionRequest,
    ): AddPrescriptionResponse

    @GET("api/tasks/allTasks")
    suspend fun getTasks(): List<TaskResponse>

    @GET("api/tasks/Tests")
    suspend fun getTests(): List<TestResponse>

    @GET("api/tasks/exercise")
    suspend fun getExercises(): List<ExerciseResponse>

    @POST("api/user/dailyCheck")
    suspend fun saveDailyCheck(
        @Body request: DailyCheckRequest,
    ): DailyCheckResponse
}