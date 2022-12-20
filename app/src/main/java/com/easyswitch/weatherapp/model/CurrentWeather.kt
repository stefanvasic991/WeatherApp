package com.easyswitch.weatherapp.model

import com.google.gson.annotations.SerializedName


data class CurrentWeather (

  @SerializedName("temperature"   )
  var temperature: Double? = null,
  @SerializedName("windspeed"     )
  var windspeed: Double? = null,
  @SerializedName("winddirection" )
  var winddirection: Int?    = null,
  @SerializedName("weathercode"   )
  var weathercode: Int?    = null,
  @SerializedName("time"          )
  var time: String? = null

)