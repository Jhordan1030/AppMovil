package com.example.apphotel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apphotel.databinding.ItemHuespedBinding
import com.example.apphotel.model.Huesped

class HuespedAdapter(
    private var huespedes: List<Huesped>,
    private val onItemClick: (Huesped) -> Unit,
    private val onDeleteClick: (Huesped) -> Unit
) : RecyclerView.Adapter<HuespedAdapter.HuespedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HuespedViewHolder {
        val binding = ItemHuespedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HuespedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HuespedViewHolder, position: Int) {
        holder.bind(huespedes[position])
    }

    override fun getItemCount(): Int = huespedes.size

    fun updateData(newHuespedes: List<Huesped>) {
        this.huespedes = newHuespedes
        notifyDataSetChanged()
    }

    inner class HuespedViewHolder(private val binding: ItemHuespedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(huesped: Huesped) {
            val fullName = "${huesped.nombres} ${huesped.apellidos}"
            binding.textNombre.text = fullName
            binding.textCedula.text = "C.I.: ${huesped.cedula}"
            binding.textEmail.text = huesped.email

            // Generate initials for the avatar circle
            val initials = buildString {
                huesped.nombres?.firstOrNull()?.let { append(it.uppercaseChar()) }
                huesped.apellidos?.firstOrNull()?.let { append(it.uppercaseChar()) }
            }
            binding.textAvatar.text = initials.ifEmpty { "?" }
            
            binding.root.setOnClickListener { onItemClick(huesped) }
            binding.btnDelete.setOnClickListener { onDeleteClick(huesped) }
        }
    }
}
