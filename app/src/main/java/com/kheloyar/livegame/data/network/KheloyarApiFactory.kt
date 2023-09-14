package com.kheloyar.livegame.data.network

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object KheloyarApiFactory {
    private const val BASE_URL = "https://site.com"

    private fun okhttpKheloyar(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("okhttp", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun getApiKheloyar(): KheloyarRemoteApi {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(KheloyarNullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttpKheloyar())
            .build()
            .create()
    }
}