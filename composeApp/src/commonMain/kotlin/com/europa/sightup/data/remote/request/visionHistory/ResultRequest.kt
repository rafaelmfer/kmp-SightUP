package com.europa.sightup.data.remote.request.visionHistory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultRequest(
    @SerialName("left") val left: String,
    @SerialName("right") val right: String
)