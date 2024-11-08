package com.europa.sightup.data.remote.response

import com.europa.sightup.data.remote.response.visionHistory.ResultResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("_id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("email") val email: String? = null,
    @SerialName("birthday") val birthday: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("userName") val userName: String? = null,
    @SerialName("preferences") val preferences: UserPreferencesResponse,
    @SerialName("eyeWear") val eyeWear: UserEyeWearResponse? = null,
)

@Serializable
data class UserPreferencesResponse(
    @SerialName("goal") val goal: String? = null,
    @SerialName("frequency") val frequency: String? = null,
    @SerialName("unit") val unit: String? = null,
)

@Serializable
data class UserEyeWearResponse(
    @SerialName("glasses") val glasses: UserPrescriptionResponse? = null,
    @SerialName("contactLens") val contactLens: UserPrescriptionResponse? = null,
)

@Serializable
data class UserPrescriptionResponse(
    @SerialName("appTest") val appTest: Boolean,
    @SerialName("prescriptionDate") val prescriptionDate: String,
    @SerialName("manufacturer") val manufacturer: String? = null,
    @SerialName("productName") val productName: String? = null,
    @SerialName("replacement") val replacement: String? = null,
    @SerialName("prescriptionInfo") val prescriptionInfo: List<ResultResponse>,
    @SerialName("notes") val notes: String? = null,
)

@Serializable
data class PrescriptionInfoResponse(
    @SerialName("testType") val testType: String,
    @SerialName("left") val left: String,
    @SerialName("right") val right: String,
)