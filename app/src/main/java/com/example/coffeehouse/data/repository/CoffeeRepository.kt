package com.example.coffeehouse.data.repository

import com.example.coffeehouse.data.network.RetrofitCoffeeInstance
import com.example.coffeehouse.data.model.response.CoffeeItem

interface CoffeeRepository {
    suspend fun getCoffees(): List<CoffeeItem>
}

class CoffeeRepositoryImpl : CoffeeRepository {
    override suspend fun getCoffees(): List<CoffeeItem> {
        return RetrofitCoffeeInstance.api.getCoffees()
    }
}
