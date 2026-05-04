package com.example.apphotel.model

import com.google.gson.annotations.SerializedName

data class DetalleReserva(
    @SerializedName("id_detalle") val id: String? = null,
    @SerializedName("id_reserva") val idReserva: String? = null,
    @SerializedName("id_habitacion") val idHabitacion: String,
    @SerializedName("habitacion_numero") val habitacionNumero: String? = null,
    @SerializedName("habitacion_tipo") val habitacionTipo: String? = null,
    @SerializedName("habitacion_piso") val habitacionPiso: Int? = null,
    @SerializedName("habitacion_capacidad") val habitacionCapacidad: Int? = null,
    @SerializedName("precio_noche") val precioNoche: Double,
    @SerializedName("num_noches") val numNoches: Int? = null,
    val subtotal: Double? = null
)
