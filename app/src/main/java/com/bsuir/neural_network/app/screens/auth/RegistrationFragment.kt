package com.bsuir.neural_network.app.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bsuir.neural_network.app.dto.LoginUserDTO
import com.bsuir.neural_network.app.utils.observeEvent
import com.bsuir.neural_network.app.views.RegistrationViewModel
import com.bsuir.neural_network.databinding.FragmentRegistrationBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.apply {
            btnSend.setOnClickListener {
                val userDTO = LoginUserDTO(
                    username = edLogin.text.toString(),
                    password = edPassword.text.toString(),
                    password2 = edPassword2.text.toString()
                )
                viewModel.registration(userDTO)
                edLogin.setText("")
                edPassword.setText("")
                edPassword2.setText("")
            }
        }
        observeShowAuthMessageEvent()
        return binding.root
    }

    private fun observeShowAuthMessageEvent() = viewModel.message.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

}