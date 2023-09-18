package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.PersonAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {

    @GET("sa/getAll")
    suspend fun getAllSA(): Response<List<SampleApplicationDTO>>

    @Multipart
    @POST("person/registration")
    suspend fun personRegistration(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("patronymic") patronymic: String,
        @Query("passportSeries") passportSeries: String,
        @Query("passportNumber") passportNumber: String,
        @Query("dateOfBirth") dateOfBirth: String,
        @Part file: MultipartBody.Part
    ): Response<HttpResponse>

    @POST("application/registration")
    suspend fun applicationRegistration(@Query("id") id: Long): Response<HttpResponse>

    @GET("person/get/people")
    suspend fun getPeople(): Response<List<PersonAnswerDTO>>

    @POST("person/add/manager")
    suspend fun addManager(@Query("id") id: Long): Response<HttpResponse>

    @POST("person/delete/manager")
    suspend fun deleteManager(@Query("id") id: Long): Response<HttpResponse>

}