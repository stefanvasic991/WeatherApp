package com.easyswitch.weatherapp.model

import com.google.gson.annotations.SerializedName


data class HourlyUnits (

  @SerializedName("time"                ) var time               : String? = null,
  @SerializedName("temperature_2m"      ) var temperature2m      : String? = null,
  @SerializedName("relativehumidity_2m" ) var relativehumidity2m : String? = null,
  @SerializedName("windspeed_10m"       ) var windspeed10m       : String? = null

)