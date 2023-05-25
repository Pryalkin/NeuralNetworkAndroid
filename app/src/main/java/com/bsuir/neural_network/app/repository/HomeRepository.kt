package com.bsuir.neural_network.app.repository

import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.sources.exception.BackendException
import com.bsuir.neural_network.sources.exception.InvalidCredentialsException
import com.bsuir.neural_network.sources.model.home.HomeSource
import okhttp3.MultipartBody
import retrofit2.Response

class HomeRepository(
    private val homeSource: HomeSource,
    private val appSettings: AppSettings
) {

    fun getRole(): String?{
        return appSettings.getCurrentRole()
    }

    suspend fun upload(keywords: String, body: MultipartBody.Part): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            homeSource.upload(keywords, body)
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

    suspend fun getAllImages(): Response<List<ImageAnswerDTO>> {
        val res: Response<List<ImageAnswerDTO>> = try {
            homeSource.getAllImages()
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

}