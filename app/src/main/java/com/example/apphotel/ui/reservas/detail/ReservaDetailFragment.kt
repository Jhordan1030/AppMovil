package com.example.apphotel.ui.reservas.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.apphotel.R
import com.example.apphotel.databinding.FragmentReservaDetailBinding
import com.example.apphotel.databinding.ItemDetalleReservaBinding
import com.example.apphotel.model.DetalleReserva
import com.example.apphotel.model.ReservaFull
import com.example.apphotel.repository.ReservaRepository
import kotlinx.coroutines.launch

class ReservaDetailFragment : Fragment() {
    private var _binding: FragmentReservaDetailBinding? = null
    private val binding get() = _binding!!
    private val repository = ReservaRepository()
    private lateinit var reservaId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reservaId = arguments?.getString("reservaId") ?: return
        loadReserva()
        
        binding.btnCheckout.setOnClickListener { updateStatus("CHECKOUT") }
        binding.btnCancel.setOnClickListener { updateStatus("CANCELADA") }
    }

    private fun loadReserva() {
        lifecycleScope.launch {
            try {
                val response = repository.getById(reservaId)
                if (response.isSuccessful) {
                    val full = response.body()
                    full?.let { displayReserva(it) }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayReserva(full: ReservaFull) {
        val r = full.reserva
        binding.textCodigo.text = r.codigo
        binding.textEstado.text = r.estado
        binding.textHuespedInfo.text = getString(R.string.huesped_info, r.huespedNombre, r.huespedCedula, r.idHuesped)
        
        binding.textFechasInfo.text = getString(R.string.checkin_info, r.fechaCheckin) + "\n" + 
                getString(R.string.checkout_info, r.fechaCheckout, r.numNoches ?: 0)
        
        binding.textSubtotal.text = getString(R.string.precio_format, r.subtotal ?: 0.0)
        binding.textImpuesto.text = getString(R.string.precio_format, r.impuesto ?: 0.0)
        binding.textTotal.text = getString(R.string.precio_format, r.total ?: 0.0)

        binding.recyclerDetalles.adapter = DetailAdapter(full.detalles)

        // Modern pill-style status badges: colored text on light tinted background
        val (bgRes, textColorRes) = when(r.estado) {
            "CONFIRMADA", "CHECKIN" -> R.drawable.status_available_bg to R.color.status_available
            "CHECKOUT" -> R.drawable.status_checkout_bg to R.color.status_checkout
            "CANCELADA" -> R.drawable.status_cancelled_bg to R.color.status_cancelled
            else -> R.drawable.status_maintenance_bg to R.color.status_maintenance
        }
        binding.textEstado.setBackgroundResource(bgRes)
        binding.textEstado.setTextColor(requireContext().getColor(textColorRes))

        if ((r.estado == "CONFIRMADA") || (r.estado == "CHECKIN")) {
            binding.btnCheckout.visibility = View.VISIBLE
            binding.btnCancel.visibility = View.VISIBLE
        } else {
            binding.btnCheckout.visibility = View.GONE
            binding.btnCancel.visibility = View.GONE
        }
    }

    private fun updateStatus(status: String) {
        lifecycleScope.launch {
            try {
                val response = repository.updateStatus(reservaId, status, null)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Estado actualizado a $status", Toast.LENGTH_SHORT).show()
                    loadReserva()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class DetailAdapter(private val list: List<DetalleReserva>) :
        RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val b = ItemDetalleReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(b)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = list[position]
            holder.binding.textHabitacion.text = "Hab. ${item.habitacionNumero} - ${item.habitacionTipo}"
            holder.binding.textPrecio.text = "$${item.precioNoche}"
            holder.binding.btnRemove.visibility = View.GONE
        }

        override fun getItemCount(): Int = list.size

        inner class ViewHolder(val binding: ItemDetalleReservaBinding) : RecyclerView.ViewHolder(binding.root)
    }
}
