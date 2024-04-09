package com.example.compse.retrofit


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient

object RetrofitFactory {
    fun <T> createService(clazz: Class<T>): T =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(Json{
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .client(
                OkHttpClient.Builder()
                    .build()
            )
            .build()
            .create(clazz)

}