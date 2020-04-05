package com.example.weatherapplication.view

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.model.entities.Weather
import kotlinx.android.synthetic.main.weather_card.view.*
import kotlin.math.roundToInt

class WeatherAdapter(private var weatherList: List<Weather>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(val weatherView: CardView) : RecyclerView.ViewHolder(weatherView) {
        private val location = weatherView.location_name
        private val temp = weatherView.temp_val
        private val press= weatherView.press_val
        private val humidity = weatherView.humidity_val
        private val maxTemp = weatherView.max_temp_val
        private val minTemp = weatherView.min_temp_val

        fun bind(weather: Weather) {
            location.text = weather.location
            temp.text = weatherView.context.getString(R.string.TEMPUNIT, weather.temp.roundToInt())
            press.text = weatherView.context.getString(R.string.PRESSUNIT,weather.press.roundToInt())
            humidity.text = weatherView.context.getString(R.string.HUMIDITYUNIT ,weather.humidity.roundToInt())
            maxTemp.text = weatherView.context.getString(R.string.TEMPUNIT, weather.maxTemp.roundToInt())
            minTemp.text = weatherView.context.getString(R.string.TEMPUNIT, weather.minTemp.roundToInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_card, parent, false) as CardView
    )

    fun setWeather(weather: List<Weather>){
        weatherList = weather
        notifyDataSetChanged()
    }

    override fun getItemCount() = weatherList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) = holder.bind(weatherList[position])
}