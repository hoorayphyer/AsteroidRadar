package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _data = AsteroidRepository()
    val asteroids : LiveData<List<Asteroid>> = _data.asteroids

    init{
        // TODO put it where it belongs
        viewModelScope.launch {
            _data.refreshAsteroids("2015-09-07", "2015-09-08")
        }
    }
}