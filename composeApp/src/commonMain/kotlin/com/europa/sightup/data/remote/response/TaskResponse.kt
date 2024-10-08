package com.europa.sightup.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    @SerialName("_id") val id: String,
    @SerialName("taskId") val taskId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("type") val type: String,
    @SerialName("video") val video: String,
    @SerialName("images") val images: String
)