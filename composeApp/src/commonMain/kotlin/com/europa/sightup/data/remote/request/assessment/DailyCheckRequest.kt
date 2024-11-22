package com.europa.sightup.data.remote.request.assessment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyCheckRequest(
    @SerialName("dailyCheckDate") var dailyCheckDate: String,
    @SerialName("email") var email: String,
    @SerialName("dailyCheckInfo") val dailyCheckInfo: DailyCheckInfoRequest? = null,
    @SerialName("dailyExerciseInfo") val dailyExerciseInfo: DailyExerciseInfoRequest? = null,
)

@Serializable
data class DailyCheckInfoRequest(
    @SerialName("visionStatus") val visionStatus: String,
    @SerialName("condition") val condition: List<String>,
    @SerialName("causes") val causes: List<String>,
    @SerialName("done") val done: Boolean = true,
)

@Serializable
data class DailyExerciseInfoRequest(
    @SerialName("eyesNow") val eyesNow: String,
    @SerialName("category") val category: String,
    @SerialName("exerciseName") val exerciseName: String,
    @SerialName("done") val done: Boolean,
)