package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface HomeApi {

    @Multipart
    @POST("image/upload")
    suspend fun upload(
        @Query("keywords") keywords: String,
        @Part file: MultipartBody.Part
    ): Response<HttpResponse>

    @GET("image/getAll")
    suspend fun getAllImages(): Response<List<ImageAnswerDTO>>

}