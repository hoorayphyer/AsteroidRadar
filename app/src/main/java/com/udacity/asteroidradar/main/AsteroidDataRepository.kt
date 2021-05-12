package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.network.AsteroidsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AsteroidRepository {
    private val service by lazy {
        AsteroidsApi.service
    }

    private val API_KEY = "mKFSpwB2zKtzTUeegZoujLWePhUPURc4wXU6lN50"

    private var _asteroids : MutableLiveData<List<Asteroid>> = MutableLiveData(listOf(Asteroid(147, "codename", "closeApproachDate", 1.23, 67.8, 7.89, 3590.0, true)))

    var asteroids: LiveData<List<Asteroid>> = _asteroids // return results from database, and use Transformations.map if necessary

    init {
        asteroids = _asteroids
    }

    suspend fun refreshAsteroids(startDate : String, endDate : String) {
        withContext(Dispatchers.IO) {
            try {
                // TODO limit the date range to 7 days
                val asteroidsFresh = service.queryAsteroids( startDate, endDate, API_KEY)
            } catch ( e : Exception ) {
            }
            // then insert the above asteroidlist into database
        }
    }
}