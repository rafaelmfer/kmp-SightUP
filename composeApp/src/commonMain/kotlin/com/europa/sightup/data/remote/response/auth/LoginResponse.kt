package com.europa.sightup.data.remote.response.auth

import com.europa.sightup.data.remote.response.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("message") val message: String? = null,
    @SerialName("accessToken") val accessToken: String? = null,
    @SerialName("user") val user: UserResponse? = null,
    @SerialName("createProfile") val createProfile: Boolean = false,
)