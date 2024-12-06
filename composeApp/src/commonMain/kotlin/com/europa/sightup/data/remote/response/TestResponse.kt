package com.europa.sightup.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.astigmatism_drive
import sightupkmpapp.composeapp.generated.resources.astigmatism_phone
import sightupkmpapp.composeapp.generated.resources.astigmatism_read
import sightupkmpapp.composeapp.generated.resources.astigmatism_results
import sightupkmpapp.composeapp.generated.resources.visual_acuity_drive
import sightupkmpapp.composeapp.generated.resources.visual_acuity_phone
import sightupkmpapp.composeapp.generated.resources.visual_acuity_read
import sightupkmpapp.composeapp.generated.resources.visual_acuity_results

@Serializable
data class TestResponse(
    @SerialName("_id") val id: String,
    @SerialName("taskId") val taskId: String,
    @SerialName("title") val title: String,
    @SerialName("shortDescription") val shortDescription: String,
    @SerialName("description") val description: String,
    @SerialName("type") val type: String,
    @SerialName("testMode") val testMode: TestModeResponse,
    @SerialName("videoMode") val videoMode: VideoModeResponse,
    @SerialName("videoEyes") val videoEyes: VideoEyes,
    @SerialName("images") val images: String,
    @SerialName("imageInstruction") val imageInstruction: String,
    @SerialName("checkList") val checkList: List<String>,
    @SerialName("howItWorks") val howItWorks: List<String>,
) {
    override fun toString(): String {
        return TestResponse::class.simpleName.toString()
    }
}

enum class VisionTestTypes(
    val title: String,
    val description: String,
    val resultImage: DrawableResource,
    val resultReadImage: DrawableResource,
    val resultDriveImage: DrawableResource,
    val resultPhoneImage: DrawableResource,
    val resultReadDescription: String = "",
    val resultDriveDescription: String = "",
    val resultPhoneDescription: String = "",
) {
    VisionAcuity(
        "Visual Acuity",
        "Visual Acuity test measures the sharpness of your vision.  Results are shown as 20/20 or 20/200, comparing your vision to normal eyesight in a North America measurement.",
        Res.drawable.visual_acuity_results,
        Res.drawable.visual_acuity_read,
        Res.drawable.visual_acuity_drive,
        Res.drawable.visual_acuity_phone,
        "Regular-sized text is readable without much issue, although very small print may feel a bit difficult.",
        "Most text and icons are clear, with only the smallest elements possibly needing a zoom.",
        "There's no significant issue while driving, and distant objects are relatively clear."
    ),
    Astigmatism(
        "Astigmatism",
        "Astigmatism test measures uneven curvature in the cornea or lens that can distort your vision. It indicates the location of astigmatism. The number ranges from 1° to 180°. Axis does not indicate the strength of an eyeglasses prescription.",
        Res.drawable.astigmatism_results,
        Res.drawable.astigmatism_read,
        Res.drawable.astigmatism_drive,
        Res.drawable.astigmatism_phone,
        "The text may appear more slanted, which can cause your eyes to tire quickly after reading for a while. You might have to bring the book closer.",
        "The screen might not appear clear, making it harder to focus, especially with small text. It can lead to eye strain after prolonged use.",
        "At night, lights may seem to glare or spread, and headlights can feel too bright, making driving more challenging."
    );
}

