package com.udacity.asteroidradar.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.network.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
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

class MainViewModel(val repo : AsteroidRepository) : ViewModel() {
    private var _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> = _pictureOfDay

    fun getPictureOfDay() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val json = AsteroidsApi.service.queryPictureOfDay()
                    _pictureOfDay.value = json
                } catch (e: Exception) {
                }
            }
        }
    }
}