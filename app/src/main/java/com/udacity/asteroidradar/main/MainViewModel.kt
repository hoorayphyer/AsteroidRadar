package com.udacity.asteroidradar.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.network.AsteroidsApi
import com.udacity.asteroidradar.network.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.IllegalArgumentException

// use a viewmodel factory so as to use ViewModelProvider() in creating the viewmodel object
class MainViewModelFactory(private val repo: AsteroidRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(val repo: AsteroidRepository) : ViewModel() {
    // use liveData builder (Note it starts with a lower case)
    val pictureOfDay: LiveData<PictureOfDay> = liveData {
        val pod = getPictureOfDay()
        pod?.let {
            emit(pod)
        }
    }

    private suspend fun getPictureOfDay(): PictureOfDay? {
        return withContext(Dispatchers.IO) {
            try {
                val pod = AsteroidsApi.service.queryPictureOfDay()
                pod
            } catch (e: Exception) {
                null
            }
        }
    }
}