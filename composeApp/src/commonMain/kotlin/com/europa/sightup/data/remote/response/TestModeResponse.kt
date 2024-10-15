package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestModeResponse(
    @SerialName("touch") val touch: String,
    @SerialName("voice") val voice: String,
    @SerialName("smartwatch") val smartwatch: String
)