package com.bsuir.neural_network.app.repository

import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.sources.exception.BackendException
import com.bsuir.neural_network.sources.exception.InvalidCredentialsException
import com.bsuir.neural_network.sources.model.cabinet.CabinetSource
import retrofit2.Response

class CabinetRepository (
    private val cabinetSource: CabinetSource,
    private val appSettings: AppSettings
){

    fun getRole(): String?{
        return appSettings.getCurrentRole()
    }


    fun logout(){
        appSettings.setCurrentToken("")
        appSettings.setCurrentUsername("")
        appSettings.setCurrentRole("")
    }

    suspend fun getAllInactivity(): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.getAllInactivity()
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

    suspend fun getInactivity(): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.getInactivity()
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

    suspend fun getActivity(): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.getActivity()
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

    suspend fun getReady(): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.getReady()
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

    suspend fun getPayment(): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.getPayment()
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

    suspend fun accept(id: Long): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.accept(id)
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

    suspend fun payment(id: Long): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.payment(id)
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

    suspend fun ready(id: Long): Response<List<ApplicationAnswerDTO>> {
        val res: Response<List<ApplicationAnswerDTO>> = try {
            cabinetSource.ready(id)
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