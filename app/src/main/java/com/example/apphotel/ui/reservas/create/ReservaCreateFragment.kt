package com.example.apphotel.ui.reservas.create

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apphotel.databinding.FragmentReservaCreateBinding
import com.example.apphotel.databinding.ItemDetalleReservaBinding
import com.example.apphotel.R
import com.example.apphotel.model.*
import com.example.apphotel.repository.HabitacionRepository
import com.example.apphotel.repository.HuespedRepository
import com.example.apphotel.repository.ReservaRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReservaCreateFragment : Fragment() {
    private var _binding: FragmentReservaCreateBinding? = null
    private val binding get() = _binding!!
    
    private val huespedRepository = HuespedRepository()
    private val habitacionRepository = HabitacionRepository()
    private val reservaRepository = ReservaRepository()

    private var selectedHuesped: Huesped? = null
    private val selectedHabitaciones = mutableListOf<Habitacion>()
    private lateinit var detailAdapter: DetailAdapter

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservaCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHuespedSpinner()
        setupDatePickers()
        setupDetailList()
        
        binding.btnAddHabitacion.setOnClickListener {
            showHabitacionPickerDialog()
        }

        binding.btnCreate.setOnClickListener {
            createReserva()
        }
    }

    private fun setupHuespedSpinner() {
        lifecycleScope.launch {
            try {
                val response = huespedRepository.getAll()
                if (response.isSuccessful) {
                    val huespedes = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, huespedes)
                    binding.spinnerHuesped.setAdapter(adapter)
                    binding.spinnerHuesped.setOnItemClickListener { _, _, position, _ ->
                        selectedHuesped = huespedes[position]
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDatePickers() {
        binding.editCheckin.setOnClickListener { showDatePicker { binding.editCheckin.setText(it) } }
        binding.editCheckout.setOnClickListener { showDatePicker { binding.editCheckout.setText(it) } }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, day ->
            calendar.set(year, month, day)
            onDateSelected(dateFormatter.format(calendar.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun setupDetailList() {
        detailAdapter = DetailAdapter(selectedHabitaciones) { habitacion ->
            selectedHabitaciones.remove(habitacion)
            detailAdapter.notifyDataSetChanged()
        }
        binding.recyclerDetalles.adapter = detailAdapter
    }

    private fun showHabitacionPickerDialog() {
        lifecycleScope.launch {
            try {
                val response = habitacionRepository.getDisponibles()
                if (response.isSuccessful) {
                    val disponibles = response.body() ?: emptyList()
                    val availableToSelect = disponibles.filter { d -> !selectedHabitaciones.any { s -> s.id == d.id } }
                    
                    val items = availableToSelect.map { it.toString() }.toTypedArray()
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Seleccionar Habitación")
                        .setItems(items) { _, which ->
                            selectedHabitaciones.add(availableToSelect[which])
                            detailAdapter.notifyDataSetChanged()
                        }
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createReserva() {
        val checkin = binding.editCheckin.text.toString()
        val checkout = binding.editCheckout.text.toString()

        if (selectedHuesped == null || checkin.isBlank() || checkout.isBlank() || selectedHabitaciones.isEmpty()) {
            Toast.makeText(context, "Por favor complete todos los campos y agregue al menos una habitación", Toast.LENGTH_LONG).show()
            return
        }

        val request = ReservaRequest(
            idHuesped = selectedHuesped!!.id!!,
            fechaReserva = dateFormatter.format(Date()),
            fechaCheckin = checkin,
            fechaCheckout = checkout,
            detalles = selectedHabitaciones.map { DetalleRequest(it.id!!, it.precioNoche) }
        )

        lifecycleScope.launch {
            try {
                val response = reservaRepository.create(request)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Reserva creada exitosamente", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
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

    inner class DetailAdapter(
        private val list: List<Habitacion>,
        private val onRemove: (Habitacion) -> Unit
    ) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val b = ItemDetalleReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(b)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = list[position]
            holder.binding.textHabitacion.text = holder.itemView.context.getString(R.string.habitacion_format, item.numero, item.tipo)
            holder.binding.textPrecio.text = holder.itemView.context.getString(R.string.precio_format, item.precioNoche)
            holder.binding.btnRemove.setOnClickListener { onRemove(item) }
        }

        override fun getItemCount(): Int = list.size

        inner class ViewHolder(val binding: ItemDetalleReservaBinding) : RecyclerView.ViewHolder(binding.root)
    }
}
