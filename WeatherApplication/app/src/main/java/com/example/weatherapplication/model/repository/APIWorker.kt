package com.example.weatherapplication.model.repository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapplication.model.db.WeatherRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope

interface APIWorkerInterface{

    fun doWork()

}
class APIWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val repo = WeatherRepository(applicationContext, CoroutineScope(IO))
        repo.refreshFromAPI()
        return Result.success()
    }
}