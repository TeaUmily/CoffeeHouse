package com.example.coffeehouse.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveFavoriteCoffeeRequest(
    @SerialName("coffeeId") val coffeeId: String
)