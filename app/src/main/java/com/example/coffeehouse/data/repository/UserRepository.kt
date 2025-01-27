package com.example.coffeehouse.data.repository

import com.example.coffeehouse.data.model.request.LoginRequest
import com.example.coffeehouse.data.model.request.RegisterRequest
import com.example.coffeehouse.data.network.RetrofitCoffeeHouseInstance
import com.example.coffeehouse.data.storage.TokenStorage
import com.example.coffeehouse.data.storage.UserInfoStorage

interface UserRepository {
    suspend fun login(username: String, password: String)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun register(username: String, password: String)
    suspend fun logout()
    suspend fun getUserName(): String?
}

class UserRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val userInfoStorage: UserInfoStorage
) : UserRepository {

    override suspend fun login(username: String, password: String) {
        val token = RetrofitCoffeeHouseInstance.api.login(LoginRequest(username, password))
        tokenStorage.storeAccessToken(token.token)
        userInfoStorage.storeUserName(username)
    }

    override suspend fun isUserLoggedIn(): Boolean = tokenStorage.getAccessToken() != null

    override suspend fun register(username: String, password: String) =
        RetrofitCoffeeHouseInstance.api.register(RegisterRequest(username, password))

    override suspend fun logout() {
        tokenStorage.removeAccessToken()
        userInfoStorage.storeUserName("")
    }

    override suspend fun getUserName(): String? = userInfoStorage.getUserName()
}
