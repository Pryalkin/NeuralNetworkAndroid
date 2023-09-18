package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CabinetApi {

    @GET("application/get/inactivity")
    suspend fun getInactivity(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/activity")
    suspend fun getActivity(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/ready")
    suspend fun getReady(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/payment")
    suspend fun getPayment(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/all/inactivity")
    suspend fun getAllInactivity(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/all/activity")
    suspend fun getAllActivity(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/all/ready")
    suspend fun getAllReady(): Response<List<ApplicationAnswerDTO>>

    @GET("application/get/all/payment")
    suspend fun getAllPayment(): Response<List<ApplicationAnswerDTO>>

    @GET("application/accept")
    suspend fun accept(@Query("id") id: Long): Response<List<ApplicationAnswerDTO>>

    @GET("application/payment")
    suspend fun payment(@Query("id") id: Long): Response<List<ApplicationAnswerDTO>>

    @GET("application/ready")
    suspend fun ready(@Query("id") id: Long): Response<List<ApplicationAnswerDTO>>
}