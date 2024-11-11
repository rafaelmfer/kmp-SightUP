package com.europa.sightup.data.remote.response.assessment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyCheckResponse(
    @SerialName("message") val message: String? = null,
    @SerialName("updatedDaily") val updatedDaily: UpdatedDailyResponse,
)

@Serializable
data class UpdatedDailyResponse(
    @SerialName("assessmentId") val assessmentId: String,
    @SerialName("email") val email: String,
    @SerialName("dailyCheckDate") val dailyCheckDate: String,
    @SerialName("dailyCheckInfo") val dailyCheckInfo: DailyCheckInfoResponse,
    @SerialName("dailyExerciseInfo") val dailyExerciseInfo: List<DailyExerciseInfoResponse> = listOf(),
)

@Serializable
data class DailyCheckInfoResponse(
    @SerialName("visionStatus") val visionStatus: String,
    @SerialName("condition") val condition: List<String>,
    @SerialName("causes") val causes: List<String>,
    @SerialName("infoTime") val infoTime: String,
    @SerialName("done") val done: Boolean,
)

@Serializable
data class DailyExerciseInfoResponse(
    @SerialName("eyesNow") val eyesNow: String,
    @SerialName("category") val category: String,
    @SerialName("exerciseName") val exerciseName: String,
    @SerialName("exerciseTime") val exerciseTime: String,
    @SerialName("done") val done: Boolean,
)