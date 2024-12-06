package com.europa.sightup.data.remote.request.prescription

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddPrescriptionRequest(
    @SerialName("userId") var userId: String,
    @SerialName("userEmail") var userEmail: String,
    @SerialName("prescriptionType") val prescriptionType: String,
    @SerialName("appTest") val appTest: Boolean,
    @SerialName("prescriptionDate") val prescriptionDate: String,
    @SerialName("manufacturer") val manufacturer: String,
    @SerialName("productName") val productName: String,
    @SerialName("replacement") val replacement: String,
    @SerialName("prescriptionInfo") val prescriptionInfo: List<AddPrescriptionInfoRequest>,
    @SerialName("notes") val notes: String,
)

@Serializable
data class AddPrescriptionInfoRequest(
    @SerialName("testType") val testType: String,
    @SerialName("left") val left: String,
    @SerialName("right") val right: String,
)
