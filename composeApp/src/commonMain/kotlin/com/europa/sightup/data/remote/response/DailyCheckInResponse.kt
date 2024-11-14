package com.europa.sightup.data.remote.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyCheckInResponse(
    @SerialName("exerciseFelling") val exerciseFelling: String? = null,
    @SerialName("dailyCheckDate") val dailyCheckDate: String,
    @SerialName("email") val email: String,
    @SerialName("dailyCheckInfo") val dailyCheckInfo: DailyCheckInfo,
    @SerialName("dailyExerciseInfo") val dailyExerciseInfo: List<DailyExerciseInfo>,
    @SerialName("assessmentId") val assessmentId: String,
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
    @SerialName("eyesNow") val eyesNow: String,
    @SerialName("category") val category: String,
    @SerialName("exerciseName") val exerciseName: String,
    @SerialName("exerciseTime") val exerciseTime: String,
    @SerialName("done") val done: Boolean? = null,
)

