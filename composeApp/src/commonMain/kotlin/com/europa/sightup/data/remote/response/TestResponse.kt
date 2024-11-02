package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestResponse(
    @SerialName("_id") val id: String,
    @SerialName("taskId") val taskId: String,
    @SerialName("title") val title: String,
    @SerialName("shortDescription") val shortDescription: String,
    @SerialName("description") val description: String,
    @SerialName("type") val type: String,
    @SerialName("testMode") val testMode: TestModeResponse,
    @SerialName("video") val video: String,
    @SerialName("images") val images: String,
    @SerialName("imageInstruction") val imageInstruction: String,
    @SerialName("checkList") val checkList: List<String>,
    @SerialName("howItWorks") val howItWorks: List<String>,
) {
    override fun toString(): String {
        return TestResponse::class.simpleName.toString()
    }
}

enum class VisionTestTypes(val title: String) {
    VisionAcuity("Visual Acuity"),
    Astigmatism("Astigmatism");
}

