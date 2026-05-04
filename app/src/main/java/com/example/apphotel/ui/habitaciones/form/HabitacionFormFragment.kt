package com.example.apphotel.ui.habitaciones.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apphotel.databinding.FragmentHabitacionFormBinding
import com.example.apphotel.model.Habitacion
import com.example.apphotel.repository.HabitacionRepository
import kotlinx.coroutines.launch

class HabitacionFormFragment : Fragment() {
    private var _binding: FragmentHabitacionFormBinding? = null
    private val binding get() = _binding!!
    private val repository = HabitacionRepository()
    private var habitacionId: String? = null

    private val tipos = arrayOf("SIMPLE", "DOBLE", "FAMILIAR", "SUITE")
    private val estados = arrayOf("DISPONIBLE", "OCUPADA", "MANTENIMIENTO")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitacionFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdowns()

        habitacionId = arguments?.getString("habitacionId")
        if (habitacionId != null) {
            binding.textFormTitle.text = "Editar Habitación"
            binding.layoutEstado.visibility = View.VISIBLE
            loadHabitacion()
        }

        binding.btnSave.setOnClickListener {
            saveHabitacion()
        }
    }

    private fun setupDropdowns() {
        val tipoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tipos)
        binding.spinnerTipo.setAdapter(tipoAdapter)

        val estadoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, estados)
        binding.spinnerEstado.setAdapter(estadoAdapter)
    }

    private fun loadHabitacion() {
        lifecycleScope.launch {
            try {
                val response = repository.getById(habitacionId!!)
                if (response.isSuccessful) {
                    val h = response.body()
                    h?.let {
                        binding.editNumero.setText(it.numero)
                        binding.spinnerTipo.setText(it.tipo, false)
                        binding.editPiso.setText(it.piso.toString())
                        binding.editCapacidad.setText(it.capacidad.toString())
                        binding.editPrecio.setText(it.precioNoche.toString())
                        binding.editDescripcion.setText(it.descripcion ?: "")
                        binding.spinnerEstado.setText(it.estado ?: "DISPONIBLE", false)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveHabitacion() {
        val numero = binding.editNumero.text.toString().trim()
        val tipo = binding.spinnerTipo.text.toString().trim()
        val pisoStr = binding.editPiso.text.toString().trim()
        val capacidadStr = binding.editCapacidad.text.toString().trim()
        val precioStr = binding.editPrecio.text.toString().trim()
        val descripcion = binding.editDescripcion.text.toString().trim()
        val estado = binding.spinnerEstado.text.toString().trim()

        if (numero.isBlank() || tipo.isBlank() || pisoStr.isBlank() || capacidadStr.isBlank() || precioStr.isBlank()) {
            Toast.makeText(context, "Por favor complete todos los campos obligatorios", Toast.LENGTH_LONG).show()
            return
        }

        val piso = pisoStr.toIntOrNull()
        val capacidad = capacidadStr.toIntOrNull()
        val precio = precioStr.toDoubleOrNull()

        if (piso == null || capacidad == null || precio == null) {
            Toast.makeText(context, "Verifique los valores numéricos", Toast.LENGTH_LONG).show()
            return
        }

        val habitacion = Habitacion(
            numero = numero,
            tipo = tipo,
            piso = piso,
            precioNoche = precio,
            capacidad = capacidad,
            descripcion = descripcion.ifBlank { null },
            estado = if (habitacionId != null) estado.ifBlank { "DISPONIBLE" } else "DISPONIBLE"
        )

        lifecycleScope.launch {
            try {
                val response = if (habitacionId == null) {
                    repository.create(habitacion)
                } else {
                    repository.update(habitacionId!!, habitacion)
                }

                if (response.isSuccessful) {
                    val msg = if (habitacionId == null) "Habitación creada" else "Habitación actualizada"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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
}
