package com.example.coffeehouse.data.network.interceptors

import com.example.coffeehouse.data.storage.TokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AccessTokenInterceptor(
    private val tokenStorage: TokenStorage,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenStorage.getAccessToken() }
        val request = chain.request()
            .newBuilder()
            .apply {
                if (accessToken != null) {
                    addHeader("Authorization", "Bearer $accessToken")
                }
            }
            .build()

        return chain.proceed(request)
    }
}
