package com.europa.sightup.data.remote.response.auth

import com.europa.sightup.data.remote.response.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("user") val user: UserResponse,
    @SerialName("createProfile") val createProfile: Boolean,
)