package com.example.apphotel.repository

import com.example.apphotel.api.RetrofitClient
import com.example.apphotel.model.ReservaRequest
import retrofit2.awaitResponse

class ReservaRepository {
    private val service = RetrofitClient.reservaService

    suspend fun getAll() = service.getAll().awaitResponse()
    suspend fun getById(id: String) = service.getById(id).awaitResponse()
    suspend fun create(request: ReservaRequest) = service.create(request).awaitResponse()
    suspend fun updateStatus(id: String, estado: String, observaciones: String?) = 
        service.update(id, mapOf("estado" to estado, "observaciones" to (observaciones ?: ""))).awaitResponse()
    suspend fun delete(id: String) = service.delete(id).awaitResponse()
}
