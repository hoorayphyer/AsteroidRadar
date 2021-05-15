package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit_service = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface NasaService {
    @GET("feed")
    suspend fun queryAsteroids(@Query("start_date") start_date: String,
                               @Query("end_date") end_date: String,
                               @Query("api_key") api_key: String): NasaJson
}


object AsteroidsApi {
    val service: NasaService by lazy {
        retrofit_service.create(NasaService::class.java)
    }
}