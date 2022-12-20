package com.easyswitch.weatherapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyswitch.weatherapp.R
import com.easyswitch.weatherapp.adapter.DayAdapter
import com.easyswitch.weatherapp.api.APIInterface
import com.easyswitch.weatherapp.api.APIUtils
import com.easyswitch.weatherapp.databinding.ActivityMainBinding
import com.easyswitch.weatherapp.model.Weather

class WeatherActivity : AppCompatActivity(), LocationListener {

    private var binding: ActivityMainBinding? = null
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    var hourList: ArrayList<String> = ArrayList()
    var temperatureList: ArrayList<Double> = ArrayList()

    lateinit var adapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getLocation()
        weatherData()
    }

    private fun weatherData() {
        APIUtils.getWeather("44.68", "20.93", true, "temperature_2m", object : APIInterface.onDelegate {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(result: Any?) {
                val data =result as Weather

                data.currentWeather?.let {
                    binding?.tvTemperature?.text = String.format("%.0f", it.temperature) + "°C"
                    binding?.tvWind?.text = "Wind speed: " + it.windspeed.toString() + " km/h"
                }

                data.hourly?.let {
                    hourList.clear()
                    hourList.addAll(it.time)

                    temperatureList.clear()
                    temperatureList.addAll(it.temperature2m)

                    adapter = DayAdapter(this@WeatherActivity, hourList,  temperatureList)
                    binding?.rvDaily?.layoutManager = LinearLayoutManager(this@WeatherActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding?.rvDaily?.adapter = adapter
                }

            }

            override fun onError(error: Any?) {
                Log.d("Error", error.toString())
            }
        })
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this@WeatherActivity)
    }
    override fun onLocationChanged(location: Location) {
        Log.d("LAT", location.latitude.toString())
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}