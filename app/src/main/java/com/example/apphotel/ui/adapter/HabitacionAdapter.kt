package com.example.apphotel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apphotel.databinding.ItemHabitacionBinding
import com.example.apphotel.R
import com.example.apphotel.model.Habitacion

class HabitacionAdapter(
    private var habitaciones: List<Habitacion>,
    private val onItemClick: (Habitacion) -> Unit,
    private val onDeleteClick: (Habitacion) -> Unit
) : RecyclerView.Adapter<HabitacionAdapter.HabitacionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitacionViewHolder {
        val binding = ItemHabitacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitacionViewHolder, position: Int) {
        holder.bind(habitaciones[position])
    }

    override fun getItemCount(): Int = habitaciones.size

    fun updateData(newHabitaciones: List<Habitacion>) {
        this.habitaciones = newHabitaciones
        notifyDataSetChanged()
    }

    inner class HabitacionViewHolder(private val binding: ItemHabitacionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habitacion: Habitacion) {
            val context = binding.root.context
            binding.textNumero.text = habitacion.numero
            binding.textTipo.text = habitacion.tipo
            binding.textEstado.text = habitacion.estado
            binding.textPrecio.text = context.getString(R.string.precio_format, habitacion.precioNoche)
            binding.textPisoCapacidad.text = context.getString(R.string.piso_capacidad_format, habitacion.piso, habitacion.capacidad)
            
            // Modern pill-style status badges: colored text on light tinted background
            val (bgRes, textColorRes) = when(habitacion.estado) {
                "DISPONIBLE" -> R.drawable.status_available_bg to R.color.status_available
                "OCUPADA" -> R.drawable.status_occupied_bg to R.color.status_occupied
                "MANTENIMIENTO" -> R.drawable.status_maintenance_bg to R.color.status_maintenance
                else -> R.drawable.status_maintenance_bg to R.color.status_maintenance
            }
            binding.textEstado.setBackgroundResource(bgRes)
            binding.textEstado.setTextColor(context.getColor(textColorRes))
            
            binding.root.setOnClickListener { onItemClick(habitacion) }
            binding.btnDelete.setOnClickListener { onDeleteClick(habitacion) }
        }
    }
}
