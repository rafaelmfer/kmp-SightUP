package com.europa.sightup.data.remote.response.visionHistory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisionHistoryResponse(
    @SerialName("message") val message: String,
    @SerialName("userHistory") val userHistory: UserHistoryResponse,
)

@Serializable
data class UserHistoryResponse(
    @SerialName("_id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("tests") val tests: List<HistoryTestResponse>,
)

@Serializable
data class HistoryTestResponse(
    @SerialName("date") val date: String,
    @SerialName("appTest") val appTest: Boolean,
    @SerialName("result") val result: List<ResultResponse>,
)

@Serializable
data class ResultResponse(
    @SerialName("testId") val testId: String? = null,
    @SerialName("testType") val testTitle: String,
    @SerialName("left") val left: String,
    @SerialName("right") val right: String,
)