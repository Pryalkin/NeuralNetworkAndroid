package com.bsuir.neural_network.app.screens.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels

import com.bsuir.neural_network.app.dto.UserDTO
import com.bsuir.neural_network.app.screens.app.ApplicationActivity
import com.bsuir.neural_network.app.utils.observeEvent
import com.bsuir.neural_network.app.views.LoginViewModel
import com.bsuir.neural_network.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.apply{
            binding.btnSend.setOnClickListener {
                val userDTO = UserDTO(
                    username = edLogin.text.toString(),
                    password = edPassword.text.toString(),
                    password2 = ""
                )
                viewModel.login(userDTO)
                edLogin.setText("")
                edPassword.setText("")
            }
        }
        observeShowAuthMessageEvent()
        observeNavigateToTabsEvent()
        return binding.root
    }

    private fun observeShowAuthMessageEvent() = viewModel.message.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun observeNavigateToTabsEvent() = viewModel.navigateToTabsEvent.observeEvent(viewLifecycleOwner) {
        requireActivity().startActivity(Intent(activity, ApplicationActivity::class.java))
    }

}