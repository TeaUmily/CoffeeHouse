package com.example.coffeehouse.data.network.api

import com.example.coffeehouse.data.model.response.CoffeeItem
import retrofit2.http.GET

interface CoffeeApiService {
    @GET("api")
    suspend fun getCoffees(): List<CoffeeItem>
}
