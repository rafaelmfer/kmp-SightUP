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
    @SerialName("userId") val userId: String,
    @SerialName("userEmail") val userEmail: String,
    @SerialName("appTest") val appTest: Boolean,
    @SerialName("tests") val tests: List<TestResponse>,
    @SerialName("_id") val id: String,
)

@Serializable
data class TestResponse(
    @SerialName("testId") val testId: String,
    @SerialName("testTitle") val testTitle: String,
    @SerialName("date") val date: String,
    @SerialName("result") val result: ResultResponse,
)

@Serializable
data class ResultResponse(
    @SerialName("left") val left: String,
    @SerialName("right") val right: String,
)
