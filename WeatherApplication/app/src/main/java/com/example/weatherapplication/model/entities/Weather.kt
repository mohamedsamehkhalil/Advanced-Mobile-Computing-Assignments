package com.example.weatherapplication.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_table")
data class Weather (
    @PrimaryKey
    var location: String,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("pressure")
    val press: Float,
    @SerializedName("humidity")
    val humidity: Float,
    @SerializedName("temp_max")
    val maxTemp: Float,
    @SerializedName("temp_min")
    val minTemp: Float
){
    companion object {
        fun createDummyData(): ArrayList<Weather>{
            var list = ArrayList<Weather>()
            list.add(
                Weather(
                    "London,uk",
                    25f,
                    120f,
                    13f,
                    27f,
                    20f
                )
            )
            list.add(
                Weather(
                    "Cairo,Egypt",
                    26f,
                    110f,
                    14f,
                    29f,
                    22f
                )
            )
            list.add(
                Weather(
                    "Paris,France",
                    29f,
                    150f,
                    16f,
                    32f,
                    19f
                )
            )
            list.addAll(list)
            return list
        }
    }
}