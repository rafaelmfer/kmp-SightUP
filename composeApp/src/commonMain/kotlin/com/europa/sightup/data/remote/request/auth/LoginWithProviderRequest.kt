package com.europa.sightup.data.remote.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginWithProviderRequest(
    @SerialName("idToken") val idToken: String,
)
