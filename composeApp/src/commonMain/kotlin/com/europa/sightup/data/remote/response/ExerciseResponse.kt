package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sightupkmpapp.composeapp.generated.resources.Res

@Serializable
data class ExerciseResponse(
    @SerialName("taskId") val taskId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("duration") val duration: String,
    @SerialName("type") var type: String,
    @SerialName("video") var video: String,
    @SerialName("images") var images: String,
    @SerialName("subtitle") var subtitle: String,
    @SerialName("helps") var helps: List<String>
)
