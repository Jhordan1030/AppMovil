package com.example.apphotel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphotel.model.Huesped
import com.example.apphotel.repository.HuespedRepository
import kotlinx.coroutines.launch

class HuespedViewModel : ViewModel() {
    private val repository = HuespedRepository()

    private val _huespedes = MutableLiveData<List<Huesped>>()
    val huespedes: LiveData<List<Huesped>> = _huespedes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadHuespedes() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getAll()
                if (response.isSuccessful) {
                    _huespedes.value = response.body() ?: emptyList()
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

    fun deleteHuesped(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.delete(id)
                if (response.isSuccessful) {
                    loadHuespedes()
                } else {
                    _error.value = "Error al eliminar: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
