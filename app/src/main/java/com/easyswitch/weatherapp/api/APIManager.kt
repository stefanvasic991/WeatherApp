package com.easyswitch.weatherapp.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object APIManager {
    private var retrofit: Retrofit? = null
    var mAccessToken : String? = ""
    val client: Retrofit
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Accept", "/")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            val gson = GsonBuilder().setLenient().create()
//
//            val sslSocketFactory: SSLSocketFactory = context.getSocketFactory()
            val client = OkHttpClient.Builder()
            client.readTimeout(60, TimeUnit.SECONDS)
            client.connectTimeout(60, TimeUnit.SECONDS)
//            client.sslSocketFactory(sslSocketFactory, x509TrustManager)
//            client.hostnameVerifier(object : HostnameVerifier {
//                override fun verify(hostname: String?, session: SSLSession?): Boolean {
//                    return true
//                }
//            })
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(APIService.BASE_URL)
                    .build()
            }
            return retrofit!!
        }
}