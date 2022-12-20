package com.easyswitch.weatherapp.api

class APIInterface {
    interface onDelegate {
        fun onSuccess(result: Any?)
        fun onError(error: Any? = null)
    }
}