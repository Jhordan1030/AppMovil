package com.example.apphotel.ui.huespedes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apphotel.R
import com.example.apphotel.databinding.FragmentHuespedesBinding
import com.example.apphotel.ui.adapter.HuespedAdapter
import com.example.apphotel.viewmodel.HuespedViewModel

class HuespedesFragment : Fragment() {
    private var _binding: FragmentHuespedesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HuespedViewModel by viewModels()
    private lateinit var adapter: HuespedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHuespedesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadHuespedes()
        
        binding.fabAddHuesped.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_huespedes_to_huespedFormFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = HuespedAdapter(emptyList(), { huesped ->
            val bundle = Bundle().apply {
                putString("huespedId", huesped.id)
            }
            findNavController().navigate(R.id.action_navigation_huespedes_to_huespedFormFragment, bundle)
        }, { huesped ->
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.btn_delete))
                .setMessage(getString(R.string.msg_delete_huesped_confirm, huesped.nombres))
                .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                    huesped.id?.let { viewModel.deleteHuesped(it) }
                }
                .setNegativeButton(getString(R.string.btn_cancel), null)
                .show()
        })
        binding.recyclerHuespedes.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.huespedes.observe(viewLifecycleOwner) { huespedes ->
            adapter.updateData(huespedes)
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
