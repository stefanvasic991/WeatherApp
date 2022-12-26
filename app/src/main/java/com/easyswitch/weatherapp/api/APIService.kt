package com.easyswitch.weatherapp.api

import com.easyswitch.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/v1/"
    }

    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("current_weather") current_weather: Boolean,
        @Query("hourly") hourly: String?
    ): Weather


}