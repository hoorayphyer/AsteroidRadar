package com.udacity.asteroidradar.main

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.HttpException
import java.util.*

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

        val cal = Calendar.getInstance()
        val startDate  = cal.time.toString()
        cal.add(Calendar.DAY_OF_YEAR, 7)
        val endDate  = cal.time.toString()

        return try {
            repo.refreshAsteroids( startDate, endDate)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}