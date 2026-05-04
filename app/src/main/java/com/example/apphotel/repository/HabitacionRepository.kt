package com.example.apphotel.repository

import com.example.apphotel.api.RetrofitClient
import com.example.apphotel.model.Habitacion
import retrofit2.awaitResponse

class HabitacionRepository {
    private val service = RetrofitClient.habitacionService

    suspend fun getAll() = service.getAll().awaitResponse()
    suspend fun getDisponibles() = service.getDisponibles().awaitResponse()
    suspend fun getById(id: String) = service.getById(id).awaitResponse()
    suspend fun create(habitacion: Habitacion) = service.create(habitacion).awaitResponse()
    suspend fun update(id: String, habitacion: Habitacion) = service.update(id, habitacion).awaitResponse()
    suspend fun delete(id: String) = service.delete(id).awaitResponse()
}
