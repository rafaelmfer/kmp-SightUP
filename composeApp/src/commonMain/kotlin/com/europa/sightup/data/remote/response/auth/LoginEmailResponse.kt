package com.europa.sightup.data.remote.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginEmailResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
)