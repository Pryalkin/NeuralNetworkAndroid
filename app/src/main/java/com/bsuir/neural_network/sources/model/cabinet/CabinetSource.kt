package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import retrofit2.Response

interface CabinetSource {
    suspend fun getInactivity(): Response<List<ApplicationAnswerDTO>>
    suspend fun getActivity(): Response<List<ApplicationAnswerDTO>>
    suspend fun getReady(): Response<List<ApplicationAnswerDTO>>
    suspend fun getPayment(): Response<List<ApplicationAnswerDTO>>
    suspend fun getAllInactivity(): Response<List<ApplicationAnswerDTO>>
    suspend fun accept(id: Long): Response<List<ApplicationAnswerDTO>>
    suspend fun payment(id: Long): Response<List<ApplicationAnswerDTO>>
    suspend fun ready(id: Long): Response<List<ApplicationAnswerDTO>>

}