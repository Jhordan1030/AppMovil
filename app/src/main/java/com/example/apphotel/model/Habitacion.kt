package com.example.apphotel.model

import com.google.gson.annotations.SerializedName

data class Habitacion(
    @SerializedName("id_habitacion") val id: String? = null,
    val numero: String,
    val tipo: String,
    val piso: Int,
    @SerializedName("precio_noche") val precioNoche: Double,
    val capacidad: Int,
    val descripcion: String? = null,
    val estado: String? = null
) {
    override fun toString(): String = "Hab. $numero - $tipo ($$precioNoche)"
}
