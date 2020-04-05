package com.example.weatherapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.weatherapplication.model.entities.Weather
import com.example.weatherapplication.model.repository.APIWorker
import com.example.weatherapplication.model.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository = WeatherRepository(application, viewModelScope)
    val weather: LiveData<List<Weather>>

    init {
        weather = repository.weather
        Log.d("beep", "got in the view model")

        // setting up worker
        viewModelScope.launch {
            val repeatingRequest =
                // work request with the minimum possible interval of 15 minutes
                PeriodicWorkRequestBuilder<APIWorker>(15, TimeUnit.MINUTES)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.UNMETERED)//must have wifi to update periodically
                            .build()
                    )
                    .build()
            WorkManager.getInstance().enqueueUniquePeriodicWork(
                WeatherRepository.APIWORKERID,
                ExistingPeriodicWorkPolicy.KEEP, //keep the old manager if it's still there
                repeatingRequest
            )
        }
    }

    fun refreshWeatherMenuItem(){
        repository.refreshFromAPI()
    }
}