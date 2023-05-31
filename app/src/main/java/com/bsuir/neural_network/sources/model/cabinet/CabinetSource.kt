package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import retrofit2.Response

interface CabinetSource {
    suspend fun getAllImagesForUser(): Response<List<ImageAnswerDTO>>
    suspend fun getAllHistoryForUser(): Response<List<HistoryAnswerDTO>>
    suspend fun subscribe(): Response<HttpResponse>
}