package com.europa.sightup.data.remote.response

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
)

@Serializable
data class UserPreferencesResponse(
    @SerialName("goal") val goal: String,
    @SerialName("frequency") val frequency: String,
    @SerialName("unit") val unit: String,
)