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

    @Multipart
    @POST("image/similar_image_search")
        suspend fun similarImageSearch( @Query("searchScore") searchScore: String,
                                        @Query("keywords") keywords: String,
                                        @Part file: MultipartBody.Part): Response<List<ImageAnswerDTO>>

    @POST("image/user/save")
    suspend fun onImageSave(@Query("id") id: Long): Response<HttpResponse>

    @GET("image/user/get/images")
    suspend fun findImages(@Query("str") str: String): Response<List<ImageAnswerDTO>>
}