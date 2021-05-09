package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository

class MainViewModel : ViewModel() {
    private val _data = AsteroidRepository()
    val asteroids : LiveData<List<Asteroid>> = _data.asteroids

    init{

    }
}