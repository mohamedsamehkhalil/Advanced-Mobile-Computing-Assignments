package com.example.weatherapplication.model.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapplication.model.WeatherAPI
import com.example.weatherapplication.model.dao.WeatherDao
import com.example.weatherapplication.model.db.EmptyCallBackInterface
import com.example.weatherapplication.model.db.WeatherRoomDatabase
import com.example.weatherapplication.model.entities.Weather
import com.example.weatherapplication.model.entities.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(private val application: Context, private val scope: CoroutineScope) : APIWorkerInterface,
    EmptyCallBackInterface {

    private val locations: Array<String> =
        arrayOf("London", "Cairo", "Paris", "Berlin", "Moscow", "Helsinki", "Tokyo")
    private val  retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api: WeatherAPI
    init {
        api = retrofit.create(WeatherAPI::class.java)
    }

    companion object{
        const val APIWORKERID = "com.example.weatherapplication.model.repository.WeatherRepository.APIWORKERID"
    }

    private val weatherDao = WeatherRoomDatabase.getDatabase(application, scope, this).weatherDao()

    val weather: LiveData<List<Weather>> = weatherDao.getAllWeather()

    suspend fun insert(weather: Weather) = weatherDao.insertWeather(weather)
    fun refreshFromAPI() {
//        scope.launch { weatherDao.deleteAllWeather() }
        for (location in locations) {
            val call = api.getWeather(location, "91d17d7e7d973e03d5e44ce7c08835d6", "metric")
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.d("debug", "Failed to request" + t.stackTrace)
                }

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.code() == 200) {
                        val weather = response.body()!!.weather!!
                        Log.d("beep", location + " " + weather.humidity)
                        weather.location = location
                        scope.launch { insert(weather) }
                    }
                }

            })
        }
    }

    override fun doWork() {
        refreshFromAPI()
    }

    override suspend fun onEmptyDB() {
        refreshFromAPI()
    }
}