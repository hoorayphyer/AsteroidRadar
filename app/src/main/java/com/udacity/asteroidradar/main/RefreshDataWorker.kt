package com.udacity.asteroidradar.main

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.HttpException
import java.text.SimpleDateFormat
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

        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
        val cal = Calendar.getInstance()
        val startDate  = dateFormat.format(cal.time)
        cal.add(Calendar.DAY_OF_YEAR, 7)
        val endDate  = dateFormat.format(cal.time)

        return try {
            repo.refreshAsteroids( startDate, endDate)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}