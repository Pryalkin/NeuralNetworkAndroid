package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.PersonAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
import okhttp3.MultipartBody
import retrofit2.Response

interface HomeSource {
    suspend fun getAllSA(): Response<List<SampleApplicationDTO>>
    suspend fun personRegistration(personDTO: PersonDTO, body: MultipartBody.Part): Response<HttpResponse>
    suspend fun applicationRegistration(id: Long): Response<HttpResponse>
    suspend fun getPeople(): Response<List<PersonAnswerDTO>>
    suspend fun add(id: Long): Response<HttpResponse>
    suspend fun delete(id: Long): Response<HttpResponse>
}