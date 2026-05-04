package com.example.apphotel.model

import com.google.gson.annotations.SerializedName

data class Huesped(
    @SerializedName("id_huesped") val id: String? = null,
    val cedula: String,
    val nombres: String,
    val apellidos: String,
    val email: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val nacionalidad: String? = null,
    val estado: Int? = null
) {
    override fun toString(): String = "$nombres $apellidos"
}
