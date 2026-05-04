package com.example.apphotel.ui.reservas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apphotel.R
import com.example.apphotel.databinding.FragmentReservasBinding
import com.example.apphotel.ui.adapter.ReservaAdapter
import com.example.apphotel.viewmodel.ReservaViewModel

class ReservasFragment : Fragment() {
    private var _binding: FragmentReservasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReservaViewModel by viewModels()
    private lateinit var adapter: ReservaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadReservas()
        
        binding.fabAddReserva.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reservas_to_reservaCreateFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = ReservaAdapter(emptyList(), { reserva ->
            val bundle = Bundle().apply {
                putString("reservaId", reserva.id)
            }
            findNavController().navigate(R.id.action_navigation_reservas_to_reservaDetailFragment, bundle)
        }, { reserva ->
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.btn_delete))
                .setMessage(getString(R.string.msg_delete_reserva_confirm, reserva.codigo))
                .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                    reserva.id?.let { viewModel.deleteReserva(it) }
                }
                .setNegativeButton(getString(R.string.btn_cancel), null)
                .show()
        })
        binding.recyclerReservas.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.reservas.observe(viewLifecycleOwner) { reservas ->
            adapter.updateData(reservas)
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
