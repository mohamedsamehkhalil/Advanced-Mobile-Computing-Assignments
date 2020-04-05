package com.example.weatherapplication.model

import com.example.weatherapplication.model.entities.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("q") location: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ):Call<WeatherResponse>
}