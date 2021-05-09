package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AsteroidRepository {
    private lateinit var _asteroids : MutableLiveData<List<Asteroid>>
    val asteroids: LiveData<List<Asteroid>> = _asteroids

    init {
        _asteroids.value = listOf(Asteroid(147, "codename", "closeApproachDate", 1.23, 67.8, 7.89, 3590.0, true))
    }
}