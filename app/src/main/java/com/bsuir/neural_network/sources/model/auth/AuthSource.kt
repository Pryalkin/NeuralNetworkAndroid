package com.bsuir.neural_network.sources.model.auth

import com.bsuir.neural_network.app.dto.LoginUserAnswerDTO
import com.bsuir.neural_network.app.dto.LoginUserDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import retrofit2.Response

interface AuthSource {
    suspend fun registration(userDTO: LoginUserDTO): Response<HttpResponse>
    suspend fun login(userDTO: LoginUserDTO): Response<LoginUserAnswerDTO>
}