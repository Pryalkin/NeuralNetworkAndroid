package com.bsuir.neural_network.app.repository

import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.sources.exception.BackendException
import com.bsuir.neural_network.sources.exception.InvalidCredentialsException
import com.bsuir.neural_network.sources.model.cabinet.CabinetSource
import okhttp3.MultipartBody
import retrofit2.Response

class CabinetRepository (
    private val cabinetSource: CabinetSource,
    private val appSettings: AppSettings
){

    fun getRole(): String?{
        return appSettings.getCurrentRole()
    }

    suspend fun getAllImagesForUser(): Response<List<ImageAnswerDTO>> {
        val res: Response<List<ImageAnswerDTO>> = try {
            cabinetSource.getAllImagesForUser()
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        return res
    }

    suspend fun getAllHistoryForUser(): Response<List<HistoryAnswerDTO>> {
        val res: Response<List<HistoryAnswerDTO>> = try {
            cabinetSource.getAllHistoryForUser()
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        return res
    }

    suspend fun subscribe(): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            cabinetSource.subscribe()
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        return res
    }

    fun logout(){
        appSettings.setCurrentToken("")
        appSettings.setCurrentUsername("")
        appSettings.setCurrentRole("")
    }


}