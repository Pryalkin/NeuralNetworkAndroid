package com.bsuir.neural_network.app.views

import androidx.lifecycle.ViewModel
import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.app.repository.AuthRepository

class LogoutViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository
): ViewModel() {

    fun logout(){
        authRepository.logout();
    }

}