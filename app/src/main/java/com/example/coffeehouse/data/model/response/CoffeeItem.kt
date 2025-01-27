package com.example.coffeehouse.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoffeeItem(
    @SerialName("_id") val id: String,
    @SerialName("id") val order: Int,
    @SerialName("description") val description: String,
    @SerialName("flavor_profile") val flavorProfile: List<String>,
    @SerialName("grind_option") val grindOption: List<String>,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Double,
    @SerialName("region") val region: String,
    @SerialName("roast_level") val roastLevel: Int,
    @SerialName("weight") val weight: Int
)