package com.example.coffeehouse.data.network

import com.example.coffeehouse.App
import com.example.coffeehouse.BuildConfig
import com.example.coffeehouse.data.network.api.CoffeeHouseApiService
import com.example.coffeehouse.data.network.interceptors.AccessTokenInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitCoffeeHouseInstance {
    private const val BASE_URL = "https://coffee-house-c8fac7659e47.herokuapp.com/"

    val api: CoffeeHouseApiService by lazy {
        val json = Json {
            ignoreUnknownKeys = true
        }

        val okHttpClient = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .addInterceptor(AccessTokenInterceptor(App.getInstance().tokenStorage))
            .build()

        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        retrofit.create(CoffeeHouseApiService::class.java)
    }
}
