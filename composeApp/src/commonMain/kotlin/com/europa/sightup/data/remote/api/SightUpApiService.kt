package com.europa.sightup.data.remote.api

import com.europa.sightup.data.remote.request.ProfileRequest
import com.europa.sightup.data.remote.request.auth.LoginRequest
import com.europa.sightup.data.remote.request.auth.LoginWithProviderRequest
import com.europa.sightup.data.remote.response.ExerciseResponse
import com.europa.sightup.data.remote.response.ProfileResponse
import com.europa.sightup.data.remote.response.TaskResponse
import com.europa.sightup.data.remote.response.TestResponse
import com.europa.sightup.data.remote.response.auth.LoginEmailResponse
import com.europa.sightup.data.remote.response.auth.LoginResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
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

    @POST("api/user/setupProfile")
    suspend fun setupProfile(
        @Body request: ProfileRequest,
    ): ProfileResponse

    @GET("api/tasks/allTasks")
    suspend fun getTasks(): List<TaskResponse>

    @GET("api/tasks/Tests")
    suspend fun getTests(): List<TestResponse>

    @GET("api/tasks/exercise")
    suspend fun getExercises(): List<ExerciseResponse>
}