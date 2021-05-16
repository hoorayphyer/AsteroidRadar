package com.udacity.asteroidradar.main

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(
    appContext,
    params
) {
    companion object{
        const val WORK_NAME = "AutoRefreshAsteroidsData"
    }

    override suspend fun doWork(): Result {
        val dao = AsteroidDatabase.getInstance(applicationContext).dao
        val repo = AsteroidRepository(dao)
        return try {
            repo.refreshAsteroids()
            repo.refreshPictureOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}