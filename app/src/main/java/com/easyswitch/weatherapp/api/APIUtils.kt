package com.easyswitch.weatherapp.api

import android.util.Log
import com.easyswitch.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object APIUtils {

    fun getData(): APIService {
        return APIManager.client.create(APIService::class.java)
    }

    fun getWeather(latitude: String?, longitude: String?, currentWeather: Boolean, hourly: String?, delegate: APIInterface.onDelegate) {
        val call: Call<Weather> = getData().getWeather(latitude, longitude, currentWeather, hourly)

        call.enqueue(object : Callback<Weather> {
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.e("onFailure", "onFailure")
            }

            override fun onResponse(
                call: Call<Weather>,
                response: Response<Weather>
            ) {
                if (response.isSuccessful) {
                    delegate.onSuccess(response.body())
                } else {
                    delegate.onError()
                    Log.e("onFail", delegate.onError().toString())
                }
            }
        })
    }
}