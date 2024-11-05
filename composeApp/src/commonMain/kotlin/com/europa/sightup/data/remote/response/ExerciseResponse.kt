package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponse(
    @SerialName("_id") val id: String,
    @SerialName("taskId") val taskId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("type") val type: String,
    @SerialName("video") val video: String,
    @SerialName("images") val images: String,
    @SerialName("imageInstruction") val imageInstruction: String,
    @SerialName("duration") val duration: Int,
    @SerialName("helps") val helps: List<String>,
    @SerialName("category") val category: String,
    @SerialName("motivation") val motivation: String,
    @SerialName("finishTitle") val finishTitle: String,
    @SerialName("advice") val advice: String,
)