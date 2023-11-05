package com.example.everything.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://newsapi.org/v2/"
    const val USER_KEY = "f8e98ef4bb614703907f787c84331a8c" // "db205c08b8f245dbb8174a61de20970d"

    private var retrofit: EverythingApi? = null

    fun getEverythingApi(): EverythingApi {
        if (retrofit == null) {
            val mHttpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val mOkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build()
                .create(EverythingApi::class.java)
        }
        return retrofit!!
    }
}