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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyswitch.weatherapp.R
import com.easyswitch.weatherapp.adapter.DayAdapter
import com.easyswitch.weatherapp.databinding.ActivityMainBinding
import com.easyswitch.weatherapp.viewModel.WeatherViewModel
import java.text.DecimalFormat

class WeatherActivity : AppCompatActivity(), LocationListener {

    private var binding: ActivityMainBinding? = null
    private  var locationManager: LocationManager? = null
    private val locationPermissionCode = 2
    private var latitude = 0.0
    private var longitude = 0.0
    val format: DecimalFormat = DecimalFormat("#,##0.00")

    lateinit var viewModel: WeatherViewModel

    var hourList: ArrayList<String> = ArrayList()
    var temperatureList: ArrayList<Double> = ArrayList()

    lateinit var adapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getLocation()
    }

    //get location permission
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, this@WeatherActivity)

    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        initViewModel()


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                Log.d("Permission", "Permission Granted")
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                Log.d("Permission", "Permission Denied")
            }
        }
    }


    // Initialize viewModel and adapter
    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        viewModel.getWeather(latitude, longitude, true, "temperature_2m").observe(this, Observer { data ->
            Log.d("DEBUG", data.currentWeather.toString())

            data.currentWeather?.let {
                binding?.tvTemperature?.text = String.format("%.0f", it.temperature).plus("°C")
                binding?.tvWind?.text = "Wind speed: ".plus(String.format("%.0f", it.windspeed).plus("km/h"))
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
        })

//        viewModel.callWeather(latitude, longitude, true, "temperature_2m")
    }
}