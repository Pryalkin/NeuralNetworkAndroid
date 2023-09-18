package com.bsuir.neural_network.app.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.repository.CabinetRepository
import com.bsuir.neural_network.app.utils.MutableLiveEvent
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

    private val _app = MutableLiveData<List<ApplicationAnswerDTO>>()
    val app = _app.share()

    fun getAllInactivity() {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.getAllInactivity()
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getInactivity() {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.getInactivity()
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getActivity() {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.getActivity()
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getReady() {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.getReady()
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getPayment() {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.getPayment()
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }


//
//    private val _histories = MutableLiveData<List<HistoryAnswerDTO>>()
//    val histories = _histories.share()
//
//    private val _image = MutableLiveData<ImageAnswerDTO>()
//    val image = _image.share()
//
//    private val _navigateToTabsEvent = MutableUnitLiveEvent()
//    val navigateToTabsEvent = _navigateToTabsEvent.share()
//
//    fun getRole(): String {
//        return cabinetRepository.getRole()!!
//    }
//
//    fun getAllImagesForUser() {
//        viewModelScope.launch {
//            var res: Response<List<ImageAnswerDTO>> = cabinetRepository.getAllImagesForUser()
//            if (res.isSuccessful){
//                _images.value = res.body()
//                showToast("Изображения получены!")
//            } else {
//                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
//                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
//                showToast(mes)
//            }
//        }
//    }
//
//    fun getAllHistoryForUser() {
//        viewModelScope.launch {
//            var res: Response<List<HistoryAnswerDTO>> = cabinetRepository.getAllHistoryForUser()
//            if (res.isSuccessful){
//                _histories.value = res.body()
//                showToast("История получена!")
//            } else {
//                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
//                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
//                showToast(mes)
//            }
//        }
//    }
//
    private fun showToast(mes: String) = _message.publishEvent(mes)

    fun getRole(): String {
        return cabinetRepository.getRole()!!
    }

    fun accept(id: Long) {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.accept(id)
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun payment(id: Long) {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.payment(id)
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun ready(id: Long) {
        viewModelScope.launch {
            var res: Response<List<ApplicationAnswerDTO>> = cabinetRepository.ready(id)
            if (res.isSuccessful){
                _app.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }
//
//    fun subscribe(activity: FragmentActivity?) {
//        viewModelScope.launch {
//            var res: Response<HttpResponse> = cabinetRepository.subscribe()
//            if (res.isSuccessful){
//                showToast("Вы успешно подписались!")
//                cabinetRepository.logout()
//                activity!!.startActivity(Intent(activity, MainActivity::class.java))
//            } else {
//                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
//                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
//                showToast(mes)
//            }
//        }
//    }




}