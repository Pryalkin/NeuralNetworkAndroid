package com.bsuir.neural_network.app.views

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.Singletons.cabinetRepository
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.SampleApplication
import com.bsuir.neural_network.app.repository.HomeRepository
import com.bsuir.neural_network.app.screens.auth.MainActivity
import com.bsuir.neural_network.app.utils.MutableLiveEvent
import com.bsuir.neural_network.app.utils.MutableUnitLiveEvent
import com.bsuir.neural_network.app.utils.publishEvent
import com.bsuir.neural_network.app.utils.share
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

class HomeViewModel (
    private val homeRepository: HomeRepository = Singletons.homeRepository
): ViewModel() {

    private val _message = MutableLiveEvent<String>()
    val message = _message.share()

    private val _sas = MutableLiveData<List<SampleApplication>>()
    val sas = _sas.share()

    private val _image = MutableLiveData<SampleApplication>()
    val sa = _image.share()

    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun getRole(): String {
        return homeRepository.getRole()!!
    }

    fun getAllSA() {
        viewModelScope.launch {
            var res: Response<List<SampleApplication>> = homeRepository.getAllSA()
            if (res.isSuccessful){
                _sas.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    private fun showToast(mes: String) = _message.publishEvent(mes)

    fun applicationRegistration(id: Long) {
        viewModelScope.launch {
            var res: Response<HttpResponse> = homeRepository.applicationRegistration(id)
            if (res.isSuccessful){
                showToast("Вы успешно зарегистрировались!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun personRegistration(personDTO: PersonDTO, body: MultipartBody.Part, activity: Activity) {
        viewModelScope.launch {
            var res: Response<HttpResponse> = homeRepository.personRegistration(personDTO, body)
            if (res.isSuccessful){
                showToast("Вы успешно зарегистрировались!")
                homeRepository.logout()
                activity!!.startActivity(Intent(activity, MainActivity::class.java))
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

}