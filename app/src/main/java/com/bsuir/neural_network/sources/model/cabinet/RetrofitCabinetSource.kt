package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.HistoryAnswerDTO
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.sources.backend.BackendRetrofitSource
import com.bsuir.neural_network.sources.backend.RetrofitConfig
import com.bsuir.neural_network.sources.model.home.HomeApi
import com.bsuir.neural_network.sources.model.home.HomeSource
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitCabinetSource (
    config: RetrofitConfig
) : BackendRetrofitSource(config), CabinetSource {

    private val cabinetApi = retrofit.create(CabinetApi::class.java)

    override suspend fun getAllImagesForUser(): Response<List<ImageAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getAllImagesForUser()
    }

    override suspend fun getAllHistoryForUser(): Response<List<HistoryAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getAllHistoryForUser()
    }

    override suspend fun subscribe(): Response<HttpResponse> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.subscribe()
    }

}