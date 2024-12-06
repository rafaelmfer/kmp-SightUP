package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestModeResponse(
    @SerialName("touch") val touch: String,
    @SerialName("voice") val voice: String,
    @SerialName("smartwatch") val smartwatch: String
)

@Serializable
data class VideoModeResponse(
    @SerialName("touch") val touch: String,
    @SerialName("voice") val voice: String,
    @SerialName("smartwatch") val smartwatch: String
)

@Serializable
data class VideoEyes(
    @SerialName("left") val left: String,
    @SerialName("right") val right: String
)