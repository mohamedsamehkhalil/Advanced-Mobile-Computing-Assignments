package com.example.weatherapplication.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapplication.model.entities.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_table ORDER BY location ASC") // why is this one not suspennd ???
    fun getAllWeather(): LiveData<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWeather(weather: Weather)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAllWeather()

    @Query("SELECT COUNT(*) FROM weather_table")
    suspend fun getSize(): Int

}