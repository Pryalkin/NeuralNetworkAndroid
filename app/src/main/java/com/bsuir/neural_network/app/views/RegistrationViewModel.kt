package com.bsuir.neural_network.app.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.app.dto.UserDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.repository.AuthRepository
import com.bsuir.neural_network.app.utils.MutableLiveEvent
import com.bsuir.neural_network.app.utils.publishEvent
import com.bsuir.neural_network.app.utils.share
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class RegistrationViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository
): ViewModel() {

    private val _message = MutableLiveEvent<String>()
    val message = _message.share()

    fun registration(userDTO: UserDTO){
        viewModelScope.launch {
            var res: Response<HttpResponse> = authRepository.registration(userDTO)
            if (res.isSuccessful){
                showAuthToast("Пользователь успешно зарегистрирован!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showAuthToast(mes)
            }
        }
    }

    private fun showAuthToast(mes: String) = _message.publishEvent(mes)

}