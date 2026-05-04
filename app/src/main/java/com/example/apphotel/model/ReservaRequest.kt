package com.example.apphotel.model

import com.google.gson.annotations.SerializedName

data class ReservaRequest(
    @SerializedName("id_huesped") val idHuesped: String,
    @SerializedName("fecha_reserva") val fechaReserva: String,
    @SerializedName("fecha_checkin") val fechaCheckin: String,
    @SerializedName("fecha_checkout") val fechaCheckout: String,
    val observaciones: String? = null,
    val detalles: List<DetalleRequest>
)

data class DetalleRequest(
    @SerializedName("id_habitacion") val idHabitacion: String,
    @SerializedName("precio_noche") val precioNoche: Double
)
