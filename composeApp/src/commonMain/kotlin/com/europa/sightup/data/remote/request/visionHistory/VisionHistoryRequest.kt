package com.europa.sightup.data.remote.request.visionHistory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisionHistoryRequest(
    @SerialName("userId") val userId: String,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("appTest") val appTest: Boolean,
    @SerialName("testId") val testId: String,
    @SerialName("testTitle") val testTitle: String,
    @SerialName("result") val result: ResultRequest
)