package com.udacity.asteroidradar.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// use a viewmodel factory so as to use ViewModelProvider() in creating the viewmodel object
class MainViewModelFactory(private val repo : AsteroidRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(private val repo : AsteroidRepository) : ViewModel() {
    val asteroids : LiveData<List<Asteroid>> = repo.asteroids

    init{
        // TODO put it where it belongs
        viewModelScope.launch {
            repo.refreshAsteroids("2015-09-07", "2015-09-08")
        }
    }
}