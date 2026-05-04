package com.example.apphotel.model

import com.google.gson.annotations.SerializedName

data class Reserva(
    @SerializedName("id_reserva") val id: String? = null,
    val codigo: String,
    @SerializedName("id_huesped") val idHuesped: String,
    @SerializedName("fecha_reserva") val fechaReserva: String? = null,
    @SerializedName("fecha_checkin") val fechaCheckin: String,
    @SerializedName("fecha_checkout") val fechaCheckout: String,
    @SerializedName("num_noches") val numNoches: Int? = null,
    val subtotal: Double? = null,
    val impuesto: Double? = null,
    val total: Double? = null,
    val estado: String? = null,
    val observaciones: String? = null,
    @SerializedName("huesped_cedula") val huespedCedula: String? = null,
    @SerializedName("huesped_nombre") val huespedNombre: String? = null
)
