package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyCheckInResponse(
    @SerialName("dailyCheckDate") val dailyCheckDate: String,
    @SerialName("email") val email: String,
    @SerialName("dailyCheckInfo") val dailyCheckInfo: DailyCheckInfo? = null,
    @SerialName("dailyExerciseInfo") val dailyExerciseInfo: List<DailyExerciseInfo>,
)

@Serializable
data class DailyCheckInfo(
    @SerialName("visionStatus") val visionStatus: String,
    @SerialName("condition") val condition: List<String>,
    @SerialName("causes") val causes: List<String>,
    @SerialName("infoTime") val infoTime: String,
    @SerialName("done") val done: Boolean? = null,
)

@Serializable
data class DailyExerciseInfo(
    @SerialName("eyesNow") val eyesNow: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("exerciseName") val exerciseName: String? = null,
    @SerialName("exerciseTime") val exerciseTime: String? = null,
    @SerialName("done") val done: Boolean? = null,
)

