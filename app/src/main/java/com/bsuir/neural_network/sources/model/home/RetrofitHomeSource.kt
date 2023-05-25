package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.sources.backend.BackendRetrofitSource
import com.bsuir.neural_network.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.Response

class RetrofitHomeSource(
    config: RetrofitConfig
) : BackendRetrofitSource(config), HomeSource {

    private val homeApi = retrofit.create(HomeApi::class.java)

    override suspend fun upload(keywords: String,
                                body: MultipartBody.Part): Response<HttpResponse>  = wrapRetrofitExceptions {
        delay(1000)
        homeApi.upload(keywords, body)
    }

    override suspend fun getAllImages(): Response<List<ImageAnswerDTO>>  = wrapRetrofitExceptions {
        delay(1000)
        homeApi.getAllImages()
    }


}