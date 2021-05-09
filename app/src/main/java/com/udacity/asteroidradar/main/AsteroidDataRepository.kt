package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class AsteroidRepository {
    private lateinit var _asteroids : MutableLiveData<List<Asteroid>>
    val asteroids: LiveData<List<Asteroid>> = _asteroids // return results from database, and use Transformations.map if necessary

    init {
        _asteroids.value = listOf(Asteroid(147, "codename", "closeApproachDate", 1.23, 67.8, 7.89, 3590.0, true))
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidlist = 0 // some network calls ending with .await()
            // then insert the above asteroidlist into database
        }
    }
}