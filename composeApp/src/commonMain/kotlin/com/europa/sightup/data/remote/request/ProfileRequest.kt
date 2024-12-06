package com.europa.sightup.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRequest(
    @SerialName("userId") val userId: String? = null,
    @SerialName("email") val email: String,
    @SerialName("userName") val userName: String? = null,
    @SerialName("birthday") val birthday: Int? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("goal") val goal: String? = null,
    @SerialName("frequency") val frequency: String? = null,
    @SerialName("unit") val unit: String? = null,
)