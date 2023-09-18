package com.bsuir.neural_network.app.repository

import com.bsuir.neural_network.app.dto.PersonAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
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

    suspend fun getAllSA(): Response<List<SampleApplicationDTO>> {
        val res: Response<List<SampleApplicationDTO>> = try {
            homeSource.getAllSA()
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

    suspend fun personRegistration(personDTO: PersonDTO, body: MultipartBody.Part): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            homeSource.personRegistration(personDTO, body)
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

    suspend fun applicationRegistration(id: Long): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            homeSource.applicationRegistration(id)
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

    fun logout() {
        appSettings.setCurrentToken("")
        appSettings.setCurrentUsername("")
        appSettings.setCurrentRole("")
    }

    suspend fun getPeople(): Response<List<PersonAnswerDTO>> {
        val res: Response<List<PersonAnswerDTO>> = try {
            homeSource.getPeople()
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

    suspend fun add(id: Long): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            homeSource.add(id)
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

    suspend fun delete(id: Long): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            homeSource.delete(id)
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