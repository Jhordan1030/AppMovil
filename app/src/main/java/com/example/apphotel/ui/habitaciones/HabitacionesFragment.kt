package com.example.apphotel.ui.habitaciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apphotel.R
import com.example.apphotel.databinding.FragmentHabitacionesBinding
import com.example.apphotel.ui.adapter.HabitacionAdapter
import com.example.apphotel.viewmodel.HabitacionViewModel

class HabitacionesFragment : Fragment() {
    private var _binding: FragmentHabitacionesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitacionViewModel by viewModels()
    private lateinit var adapter: HabitacionAdapter

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

        // FAB → Crear nueva habitación
        binding.fabAddHabitacion.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_habitaciones_to_habitacionFormFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = HabitacionAdapter(emptyList(), { habitacion ->
            // Click en tarjeta → Editar habitación
            val bundle = Bundle().apply {
                putString("habitacionId", habitacion.id)
            }
            findNavController().navigate(R.id.action_navigation_habitaciones_to_habitacionFormFragment, bundle)
        }, { habitacion ->
            // Click en eliminar → Confirmación
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
