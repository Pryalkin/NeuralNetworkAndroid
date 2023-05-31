package com.bsuir.neural_network.app.views

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.repository.CabinetRepository
import com.bsuir.neural_network.app.screens.auth.MainActivity
import com.bsuir.neural_network.app.utils.MutableLiveEvent
import com.bsuir.neural_network.app.utils.MutableUnitLiveEvent
import com.bsuir.neural_network.app.utils.publishEvent
import com.bsuir.neural_network.app.utils.share
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class CabinetViewModel (
    private val cabinetRepository: CabinetRepository = Singletons.cabinetRepository
): ViewModel() {

    private val _message = MutableLiveEvent<String>()
    val message = _message.share()

    private val _images = MutableLiveData<List<ImageAnswerDTO>>()
    val images = _images.share()

    private val _histories = MutableLiveData<List<HistoryAnswerDTO>>()
    val histories = _histories.share()

    private val _image = MutableLiveData<ImageAnswerDTO>()
    val image = _image.share()

    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun getRole(): String {
        return cabinetRepository.getRole()!!
    }

    fun getAllImagesForUser() {
        viewModelScope.launch {
            var res: Response<List<ImageAnswerDTO>> = cabinetRepository.getAllImagesForUser()
            if (res.isSuccessful){
                _images.value = res.body()
                showToast("Изображения получены!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getAllHistoryForUser() {
        viewModelScope.launch {
            var res: Response<List<HistoryAnswerDTO>> = cabinetRepository.getAllHistoryForUser()
            if (res.isSuccessful){
                _histories.value = res.body()
                showToast("История получена!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    private fun showToast(mes: String) = _message.publishEvent(mes)

    fun subscribe(activity: FragmentActivity?) {
        viewModelScope.launch {
            var res: Response<HttpResponse> = cabinetRepository.subscribe()
            if (res.isSuccessful){
                showToast("Вы успешно подписались!")
                cabinetRepository.logout()
                activity!!.startActivity(Intent(activity, MainActivity::class.java))
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }




}