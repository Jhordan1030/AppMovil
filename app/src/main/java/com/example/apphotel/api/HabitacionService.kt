package com.example.apphotel.api

import com.example.apphotel.model.Habitacion
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface HabitacionService {
    @GET("habitaciones")
    fun getAll(): Call<List<Habitacion>>

    @GET("habitaciones/disponibles")
    fun getDisponibles(): Call<List<Habitacion>>

    @GET("habitaciones/{id}")
    fun getById(@Path("id") id: String): Call<Habitacion>

    @POST("habitaciones")
    fun create(@Body habitacion: Habitacion): Call<Habitacion>

    @PUT("habitaciones/{id}")
    fun update(@Path("id") id: String, @Body habitacion: Habitacion): Call<Habitacion>

    @DELETE("habitaciones/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}
