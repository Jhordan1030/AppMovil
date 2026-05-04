package com.example.apphotel.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://hotel-api-silk.vercel.app/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    val huespedService: HuespedService = retrofit.create(HuespedService::class.java)
    val habitacionService: HabitacionService = retrofit.create(HabitacionService::class.java)
    val reservaService: ReservaService = retrofit.create(ReservaService::class.java)
}
