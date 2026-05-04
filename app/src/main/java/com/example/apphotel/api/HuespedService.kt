package com.example.apphotel.api

import com.example.apphotel.model.Huesped
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface HuespedService {
    @GET("huespedes")
    fun getAll(): Call<List<Huesped>>

    @GET("huespedes/{id}")
    fun getById(@Path("id") id: String): Call<Huesped>

    @POST("huespedes")
    fun create(@Body huesped: Huesped): Call<Huesped>

    @PUT("huespedes/{id}")
    fun update(@Path("id") id: String, @Body huesped: Huesped): Call<Huesped>

    @DELETE("huespedes/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}
