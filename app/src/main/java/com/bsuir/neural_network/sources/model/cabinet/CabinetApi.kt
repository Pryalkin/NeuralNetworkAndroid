package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface CabinetApi {

    @GET("image/user/getAll")
    suspend fun getAllImagesForUser(): Response<List<ImageAnswerDTO>>

    @GET("image/user/history")
    suspend fun getAllHistoryForUser(): Response<List<HistoryAnswerDTO>>

    @POST("user/subscribe")
    suspend fun subscribe(): Response<HttpResponse>
}