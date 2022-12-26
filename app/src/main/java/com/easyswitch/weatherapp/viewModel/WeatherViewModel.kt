package com.easyswitch.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easyswitch.weatherapp.api.APIManager
import com.easyswitch.weatherapp.model.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weather: MutableLiveData<Weather> = MutableLiveData()

    fun getWeather(): MutableLiveData<Weather> {
        return weather
    }

    fun callWeather(latitude: Double, longitude: Double, current_weather: Boolean,hourly: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = APIManager.apiService.getWeather(latitude, longitude, current_weather, hourly)
            weather.postValue(response)
        }
    }
}