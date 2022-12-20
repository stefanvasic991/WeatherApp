package com.easyswitch.weatherapp.model

import com.google.gson.annotations.SerializedName


data class Hourly (

  @SerializedName("time"                ) var time               : ArrayList<String> = arrayListOf(),
  @SerializedName("temperature_2m"      ) var temperature2m      : ArrayList<Double> = arrayListOf(),
  @SerializedName("relativehumidity_2m" ) var relativehumidity2m : ArrayList<Int>    = arrayListOf(),
  @SerializedName("windspeed_10m"       ) var windspeed10m       : ArrayList<Double> = arrayListOf()

)