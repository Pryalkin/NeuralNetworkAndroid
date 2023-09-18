package com.bsuir.neural_network.app.repository

import com.bsuir.neural_network.app.dto.LoginUserAnswerDTO
import com.bsuir.neural_network.app.dto.LoginUserDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.sources.exception.BackendException
import com.bsuir.neural_network.sources.exception.InvalidCredentialsException
import com.bsuir.neural_network.sources.model.auth.AuthSource
import retrofit2.Response

class AuthRepository(
    private val authSource: AuthSource,
    private val appSettings: AppSettings
) {

    suspend fun login(userDTO: LoginUserDTO): Response<LoginUserAnswerDTO> {
        val res: Response<LoginUserAnswerDTO> = try {
            authSource.login(userDTO)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        appSettings.setCurrentToken(res.headers().get("Jwt-Token"))
        appSettings.setCurrentUsername(res.body()?.username)
        appSettings.setCurrentRole(res.body()?.role)
        return res
    }

    suspend fun registration(userDTO: LoginUserDTO): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            authSource.registration(userDTO)
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

    fun cleanToken(){
        appSettings.setCurrentToken("")
        appSettings.setCurrentUsername("")
        appSettings.setCurrentRole("")
    }

}