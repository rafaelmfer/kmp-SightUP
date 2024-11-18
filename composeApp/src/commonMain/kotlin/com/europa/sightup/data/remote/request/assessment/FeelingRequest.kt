package com.europa.sightup.data.remote.request.assessment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeelingRequest(
    @SerialName("feeling") val feeling: String,
)