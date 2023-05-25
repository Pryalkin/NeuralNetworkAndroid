package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface HomeSource {
    suspend fun upload(keywords: String, body: MultipartBody.Part): Response<HttpResponse>
    suspend fun getAllImages(): Response<List<ImageAnswerDTO>>
}