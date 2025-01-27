package com.example.coffeehouse.data.network.api

import com.example.coffeehouse.data.model.request.RemoveFavoriteCoffeeRequest
import com.example.coffeehouse.data.model.request.AddFavoriteCoffeeRequest
import com.example.coffeehouse.data.model.request.LoginRequest
import com.example.coffeehouse.data.model.request.RegisterRequest
import com.example.coffeehouse.data.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CoffeeHouseApiService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest)

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("favoriteCoffee")
    suspend fun getFavoriteCoffees(): List<String>

    @POST("favoriteCoffee")
    suspend fun addCoffeeToFavorites(@Body addFavoriteCoffeeRequest: AddFavoriteCoffeeRequest)

    @HTTP(method = "DELETE", path = "favoriteCoffee", hasBody = true)
    suspend fun removeCoffeeFromFavorites(
        @Body removeFavoriteCoffeeRequest: RemoveFavoriteCoffeeRequest
    )
}
