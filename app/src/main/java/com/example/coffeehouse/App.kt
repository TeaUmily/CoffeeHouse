package com.example.coffeehouse

import android.app.Application
import com.example.coffeehouse.data.repository.CoffeeHouseRepositoryImpl
import com.example.coffeehouse.data.repository.CoffeeRepositoryImpl
import com.example.coffeehouse.data.repository.UserRepositoryImpl
import com.example.coffeehouse.data.storage.DataStoreTokenStorage
import com.example.coffeehouse.data.storage.DataStoreUserInfoStorage

class App : Application() {

    val tokenStorage by lazy { DataStoreTokenStorage(this) }
    private val userInfoStorage by lazy { DataStoreUserInfoStorage(this) }

    val userRepository by lazy {
        UserRepositoryImpl(
            tokenStorage = tokenStorage, userInfoStorage = userInfoStorage
        )
    }
    val coffeeRepository by lazy { CoffeeRepositoryImpl() }
    val coffeeHouseRepository by lazy { CoffeeHouseRepositoryImpl() }

    companion object {
        private var instance: App? = null

        fun getInstance(): App {
            return instance ?: throw IllegalStateException("Application not initialized")
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
