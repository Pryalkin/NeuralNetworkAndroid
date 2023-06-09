package com.bsuir.neural_network.sources.model.auth

import com.bsuir.neural_network.app.dto.LoginUserAnswerDTO
import com.bsuir.neural_network.app.dto.LoginUserDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/registration")
    suspend fun registration(@Body userDTO: LoginUserDTO): Response<HttpResponse>

    @POST("auth/login")
    suspend fun login(@Body userDTO: LoginUserDTO): Response<LoginUserAnswerDTO>

}