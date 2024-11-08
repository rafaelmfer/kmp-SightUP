package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddPrescriptionResponse(
    @SerialName("message") val message: String,
    @SerialName("user") val user: UserResponse,
)