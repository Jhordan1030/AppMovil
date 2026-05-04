package com.example.apphotel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphotel.model.Reserva
import com.example.apphotel.repository.ReservaRepository
import kotlinx.coroutines.launch

class ReservaViewModel : ViewModel() {
    private val repository = ReservaRepository()

    private val _reservas = MutableLiveData<List<Reserva>>()
    val reservas: LiveData<List<Reserva>> = _reservas

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadReservas() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getAll()
                if (response.isSuccessful) {
                    _reservas.value = response.body() ?: emptyList()
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

    fun deleteReserva(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.delete(id)
                if (response.isSuccessful) {
                    loadReservas()
                } else {
                    _error.value = "Error al eliminar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
