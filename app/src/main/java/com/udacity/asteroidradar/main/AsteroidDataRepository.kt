package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.network.AsteroidsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AsteroidRepository(private val cache : AsteroidDatabaseDao) {
    private val service by lazy {
        AsteroidsApi.service
    }

    private val API_KEY = "mKFSpwB2zKtzTUeegZoujLWePhUPURc4wXU6lN50"

    var asteroids: LiveData<List<Asteroid>>

    init {
        asteroids = cache.getAllAsteroids()
    }

    suspend fun refreshAsteroids(startDate : String, endDate : String) {
        withContext(Dispatchers.IO) {
            try {
                // TODO limit the date range to 7 days
                val asteroidsFresh = service.queryAsteroids( startDate, endDate, API_KEY)
                cache.updateDatabase(asteroidsFresh)
            } catch ( e : Exception ) {
            }
        }
    }
}