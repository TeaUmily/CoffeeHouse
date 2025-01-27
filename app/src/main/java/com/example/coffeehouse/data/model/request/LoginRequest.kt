package com.example.coffeehouse.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("userName") val userName: String,
    @SerialName("password") val password: String
)
