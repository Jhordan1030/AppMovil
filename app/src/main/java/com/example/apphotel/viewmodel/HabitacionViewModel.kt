package com.example.apphotel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphotel.model.Habitacion
import com.example.apphotel.repository.HabitacionRepository
import kotlinx.coroutines.launch

class HabitacionViewModel : ViewModel() {
    private val repository = HabitacionRepository()

    private val _habitaciones = MutableLiveData<List<Habitacion>>()
    val habitaciones: LiveData<List<Habitacion>> = _habitaciones

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadHabitaciones() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getAll()
                if (response.isSuccessful) {
                    _habitaciones.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateHabitacion(id: String, habitacion: Habitacion) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.update(id, habitacion)
                if (response.isSuccessful) {
                    loadHabitaciones()
                } else {
                    _error.value = "Error al actualizar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteHabitacion(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.delete(id)
                if (response.isSuccessful) {
                    loadHabitaciones()
                } else {
                    _error.value = "Error al eliminar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
