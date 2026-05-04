package com.example.apphotel.ui.habitaciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apphotel.R
import com.example.apphotel.databinding.FragmentHabitacionesBinding
import com.example.apphotel.model.Habitacion
import com.example.apphotel.ui.adapter.HabitacionAdapter
import com.example.apphotel.viewmodel.HabitacionViewModel

class HabitacionesFragment : Fragment() {
    private var _binding: FragmentHabitacionesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitacionViewModel by viewModels()
    private lateinit var adapter: HabitacionAdapter

    private val estadosDisponibles = arrayOf("DISPONIBLE", "OCUPADA", "MANTENIMIENTO")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitacionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadHabitaciones()
        
        binding.fabAddHabitacion.setOnClickListener {
            // TODO: Navigate to create habitacion
            Toast.makeText(context, "Crear habitación no implementado aún", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = HabitacionAdapter(emptyList(), { habitacion ->
            showChangeStatusDialog(habitacion)
        }, { habitacion ->
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.btn_delete))
                .setMessage(getString(R.string.msg_delete_habitacion_confirm, habitacion.numero))
                .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                    habitacion.id?.let { viewModel.deleteHabitacion(it) }
                }
                .setNegativeButton(getString(R.string.btn_cancel), null)
                .show()
        })
        binding.recyclerHabitaciones.adapter = adapter
    }

    private fun showChangeStatusDialog(habitacion: Habitacion) {
        val currentIndex = estadosDisponibles.indexOf(habitacion.estado)

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Cambiar estado de Hab. ${habitacion.numero}")
            .setSingleChoiceItems(estadosDisponibles, currentIndex) { dialog, which ->
                val newEstado = estadosDisponibles[which]
                if (newEstado != habitacion.estado) {
                    val updated = Habitacion(
                        id = habitacion.id,
                        numero = habitacion.numero,
                        tipo = habitacion.tipo,
                        piso = habitacion.piso,
                        precioNoche = habitacion.precioNoche,
                        capacidad = habitacion.capacidad,
                        descripcion = habitacion.descripcion,
                        estado = newEstado
                    )
                    habitacion.id?.let { viewModel.updateHabitacion(it, updated) }
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.btn_cancel), null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.habitaciones.observe(viewLifecycleOwner) { habitaciones ->
            adapter.updateData(habitaciones)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
