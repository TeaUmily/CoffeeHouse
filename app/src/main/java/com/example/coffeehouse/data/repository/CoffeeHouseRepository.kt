package com.example.coffeehouse.data.repository

import com.example.coffeehouse.data.model.request.AddFavoriteCoffeeRequest
import com.example.coffeehouse.data.model.request.RemoveFavoriteCoffeeRequest
import com.example.coffeehouse.data.network.RetrofitCoffeeHouseInstance

interface CoffeeHouseRepository {
    suspend fun getFavoriteCoffeeIds(): List<String>
    suspend fun addFavoriteCoffee(coffeeId: String)
    suspend fun removeFavoriteCoffee(coffeeId: String)
}

class CoffeeHouseRepositoryImpl: CoffeeHouseRepository {

    override suspend fun getFavoriteCoffeeIds(): List<String> = RetrofitCoffeeHouseInstance.api.getFavoriteCoffees()

    override suspend fun addFavoriteCoffee(coffeeId: String) {
        RetrofitCoffeeHouseInstance.api.addCoffeeToFavorites(AddFavoriteCoffeeRequest(coffeeId))
    }

    override suspend fun removeFavoriteCoffee(coffeeId: String) {
        RetrofitCoffeeHouseInstance.api.removeCoffeeFromFavorites(RemoveFavoriteCoffeeRequest(coffeeId))
    }
}
