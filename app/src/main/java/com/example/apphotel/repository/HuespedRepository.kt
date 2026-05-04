package com.example.apphotel.repository

import com.example.apphotel.api.RetrofitClient
import com.example.apphotel.model.Huesped
import retrofit2.awaitResponse

class HuespedRepository {
    private val service = RetrofitClient.huespedService

    suspend fun getAll() = service.getAll().awaitResponse()
    suspend fun getById(id: String) = service.getById(id).awaitResponse()
    suspend fun create(huesped: Huesped) = service.create(huesped).awaitResponse()
    suspend fun update(id: String, huesped: Huesped) = service.update(id, huesped).awaitResponse()
    suspend fun delete(id: String) = service.delete(id).awaitResponse()
}
