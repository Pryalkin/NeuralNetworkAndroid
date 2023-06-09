package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.SampleApplication
import okhttp3.MultipartBody
import retrofit2.Response

interface HomeSource {
    suspend fun getAllSA(): Response<List<SampleApplication>>
    suspend fun personRegistration(personDTO: PersonDTO, body: MultipartBody.Part): Response<HttpResponse>
    suspend fun applicationRegistration(id: Long): Response<HttpResponse>
}