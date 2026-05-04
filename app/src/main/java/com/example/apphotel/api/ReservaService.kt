package com.example.apphotel.api

import com.example.apphotel.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ReservaService {
    @GET("reservas")
    fun getAll(): Call<List<Reserva>>

    @GET("reservas/{id}")
    fun getById(@Path("id") id: String): Call<ReservaFull>

    @POST("reservas")
    fun create(@Body request: ReservaRequest): Call<ReservaFull>

    @PUT("reservas/{id}")
    fun update(@Path("id") id: String, @Body body: Map<String, String>): Call<Reserva>

    @DELETE("reservas/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}
