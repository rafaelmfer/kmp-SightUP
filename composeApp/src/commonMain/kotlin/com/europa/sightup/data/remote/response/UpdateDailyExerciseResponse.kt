package com.europa.sightup.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDailyExerciseResponse(
    @SerialName("message") val message: String,
    @SerialName("updatedExercise") val updatedExercise: UpdatedExerciseResponse
) {
    @Serializable
    data class UpdatedExerciseResponse(
        @SerialName("taskId") val taskId: String,
        @SerialName("title") val title: String,
        @SerialName("timeSchedule") val timeSchedule: String,
        @SerialName("duration") val duration: Int,
        @SerialName("eyeCondition") val eyeCondition: List<String>,
        @SerialName("done") val done: Boolean,
        @SerialName("_id") val id: String
    )
}