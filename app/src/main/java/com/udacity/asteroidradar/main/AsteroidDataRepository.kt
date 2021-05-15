package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.network.NasaJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

fun NasaJson.toListOfAsteroids() : List<Asteroid> {


}

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
                val asteroidsFresh = service.queryAsteroids( startDate, endDate, API_KEY).toListOfAsteroids()
                cache.updateDatabase(asteroidsFresh)
            } catch ( e : Exception ) {
            }
        }
    }
}