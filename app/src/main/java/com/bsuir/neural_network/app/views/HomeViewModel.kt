package com.bsuir.neural_network.app.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.repository.HomeRepository
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

    private val _images = MutableLiveData<List<ImageAnswerDTO>>()
    val images = _images.share()

    private val _image = MutableLiveData<ImageAnswerDTO>()
    val image = _image.share()

    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun getRole(): String {
        return homeRepository.getRole()!!
    }

    fun upload(keywords: String, body: MultipartBody.Part) {
        viewModelScope.launch {
            var res: Response<HttpResponse> = homeRepository.upload(keywords, body)
            if (res.isSuccessful){
                showToast("Вы успешно зарегистрировали фильм!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    fun getAllImages() {
        viewModelScope.launch {
            var res: Response<List<ImageAnswerDTO>> = homeRepository.getAllImages()
            if (res.isSuccessful){
                _images.value = res.body()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
                showToast(mes)
            }
        }
    }

    private fun showToast(mes: String) = _message.publishEvent(mes)

//    fun getImage(id: Long) {
//        viewModelScope.launch {
//            var res: Response<ImageAnswerDTO> = homeRepository.getImage(id)
//            if (res.isSuccessful){
//                _image.value = res.body()
//            } else {
//                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
//                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
//                showToast(mes)
//            }
//        }
//    }

//    fun getMoviesFind(find: String) {
//        viewModelScope.launch {
//            var res: Response<List<MovieAnswerDTO>> = homeRepository.getMoviesFind(find)
//            if (res.isSuccessful){
//                _movies.value = res.body()
//            } else {
//                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
//                val mes = gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message
//                showToast(mes)
//            }
//        }
//    }


}