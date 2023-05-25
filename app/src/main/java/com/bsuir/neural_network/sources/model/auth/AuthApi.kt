package com.bsuir.neural_network.sources.model.auth

import com.bsuir.neural_network.app.dto.LoginUserAnswerDTO
import com.bsuir.neural_network.app.dto.UserDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/registration")
    suspend fun registration(@Body userDTO: UserDTO): Response<HttpResponse>

    @POST("auth/login")
    suspend fun login(@Body userDTO: UserDTO): Response<LoginUserAnswerDTO>

}