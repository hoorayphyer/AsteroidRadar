package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.network.NasaJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun NasaJson.IndividualAsteroidInfo.toAsteroid(): Asteroid {
    return with(this) {
        val cad = close_approach_data[0]!!
        Asteroid(
            id, name, cad.date, absolute_magnitude_h, estimated_diameter["kilometers"]!!.max,
            cad.relative_velocity["kilometers_per_second"]!!, cad.miss_distance["astronomical"]!!,
            is_potentially_hazardous_asteroid
        )
    }
}


fun NasaJson.toListOfAsteroids(): List<Asteroid> {
    return this.near_earth_objects
        .values
        .flatten()
        .map { it.toAsteroid() }
}

class AsteroidRepository(private val cache: AsteroidDatabaseDao) {
    private val service by lazy {
        AsteroidsApi.service
    }

    private var _asteroids = cache.getAllAsteroids()
    val asteroids : LiveData<List<Asteroid>> = _asteroids

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            val startDate = dateFormat.format(cal.time)
            // the Nasa request is limited to 7 days
            cal.add(Calendar.DAY_OF_YEAR, 7)
            val endDate = dateFormat.format(cal.time)
            try {
                cache.deletePriorTo(startDate)
                val asteroidsFresh =
                    service.queryAsteroids(startDate, endDate).toListOfAsteroids()
                cache.updateDatabase(asteroidsFresh)
            } catch (e: Exception) {
            }
        }
    }
}