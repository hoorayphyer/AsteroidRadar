package com.udacity.asteroidradar.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

private val retrofit_service = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface NasaService {
    @GET("feed")
    suspend fun queryAsteroids(@Query("start_date") start_date: String,
                               @Query("end_date") end_date: String,
                               @Query("api_key") api_key: String): String
}


object AsteroidsApi {
    val service: NasaService by lazy {
        retrofit_service.create(NasaService::class.java)
    }
}


