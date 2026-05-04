package com.example.apphotel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apphotel.databinding.ItemReservaBinding
import com.example.apphotel.R
import com.example.apphotel.model.Reserva

class ReservaAdapter(
    private var reservas: List<Reserva>,
    private val onItemClick: (Reserva) -> Unit,
    private val onDeleteClick: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val binding = ItemReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        holder.bind(reservas[position])
    }

    override fun getItemCount(): Int = reservas.size

    fun updateData(newReservas: List<Reserva>) {
        this.reservas = newReservas
        notifyDataSetChanged()
    }

    inner class ReservaViewHolder(private val binding: ItemReservaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reserva: Reserva) {
            val context = binding.root.context
            binding.textCodigo.text = reserva.codigo
            binding.textEstado.text = reserva.estado
            
            // Modern pill-style status badges: colored text on light tinted background
            val (bgRes, textColorRes) = when(reserva.estado) {
                "CONFIRMADA", "CHECKIN" -> R.drawable.status_available_bg to R.color.status_available
                "CHECKOUT" -> R.drawable.status_checkout_bg to R.color.status_checkout
                "CANCELADA" -> R.drawable.status_cancelled_bg to R.color.status_cancelled
                else -> R.drawable.status_maintenance_bg to R.color.status_maintenance
            }
            binding.textEstado.setBackgroundResource(bgRes)
            binding.textEstado.setTextColor(context.getColor(textColorRes))

            binding.textHuesped.text = reserva.huespedNombre
            binding.textFechas.text = "${reserva.fechaCheckin} - ${reserva.fechaCheckout} (${reserva.numNoches} noches)"
            binding.textTotal.text = context.getString(R.string.precio_format, reserva.total ?: 0.0)
            
            binding.root.setOnClickListener { onItemClick(reserva) }
            binding.btnDelete.setOnClickListener { onDeleteClick(reserva) }
        }
    }
}
