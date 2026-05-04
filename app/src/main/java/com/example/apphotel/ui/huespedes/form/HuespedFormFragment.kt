package com.example.apphotel.ui.huespedes.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apphotel.databinding.FragmentHuespedFormBinding
import com.example.apphotel.model.Huesped
import com.example.apphotel.repository.HuespedRepository
import kotlinx.coroutines.launch

class HuespedFormFragment : Fragment() {
    private var _binding: FragmentHuespedFormBinding? = null
    private val binding get() = _binding!!
    private val repository = HuespedRepository()
    private var huespedId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHuespedFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        huespedId = arguments?.getString("huespedId")
        if (huespedId != null) {
            loadHuesped()
        }

        binding.btnSave.setOnClickListener {
            saveHuesped()
        }
    }

    private fun loadHuesped() {
        lifecycleScope.launch {
            try {
                val response = repository.getById(huespedId!!)
                if (response.isSuccessful) {
                    val h = response.body()
                    h?.let {
                        binding.editCedula.setText(it.cedula)
                        binding.editNombres.setText(it.nombres)
                        binding.editApellidos.setText(it.apellidos)
                        binding.editEmail.setText(it.email)
                        binding.editTelefono.setText(it.telefono)
                        binding.editDireccion.setText(it.direccion)
                        binding.editNacionalidad.setText(it.nacionalidad)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveHuesped() {
        val huesped = Huesped(
            cedula = binding.editCedula.text.toString(),
            nombres = binding.editNombres.text.toString(),
            apellidos = binding.editApellidos.text.toString(),
            email = binding.editEmail.text.toString(),
            telefono = binding.editTelefono.text.toString(),
            direccion = binding.editDireccion.text.toString(),
            nacionalidad = binding.editNacionalidad.text.toString()
        )

        lifecycleScope.launch {
            try {
                val response = if (huespedId == null) {
                    repository.create(huesped)
                } else {
                    repository.update(huespedId!!, huesped)
                }

                if (response.isSuccessful) {
                    Toast.makeText(context, "Huésped guardado", Toast.LENGTH_SHORT).show()
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
