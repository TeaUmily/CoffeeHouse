package com.example.coffeehouse.data.network

import com.example.coffeehouse.BuildConfig
import com.example.coffeehouse.data.network.api.CoffeeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitCoffeeInstance {
    private const val BASE_URL = "https://fake-coffee-api.vercel.app"

    val api: CoffeeApiService by lazy {
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
            .build()

        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        retrofit.create(CoffeeApiService::class.java)
    }
}
