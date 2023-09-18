package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.ApplicationAnswerDTO
import com.bsuir.neural_network.sources.backend.BackendRetrofitSource
import com.bsuir.neural_network.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitCabinetSource (
    config: RetrofitConfig
) : BackendRetrofitSource(config), CabinetSource {

    private val cabinetApi = retrofit.create(CabinetApi::class.java)

    override suspend fun getInactivity(): Response<List<ApplicationAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getInactivity()
    }

    override suspend fun getActivity(): Response<List<ApplicationAnswerDTO>>  = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getActivity()
    }

    override suspend fun getReady(): Response<List<ApplicationAnswerDTO>>  = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getReady()
    }

    override suspend fun getPayment(): Response<List<ApplicationAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getPayment()
    }

    override suspend fun getAllInactivity(): Response<List<ApplicationAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.getAllInactivity()
    }

    override suspend fun accept(id: Long): Response<List<ApplicationAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.accept(id)
    }

    override suspend fun payment(id: Long): Response<List<ApplicationAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.payment(id)
    }

    override suspend fun ready(id: Long): Response<List<ApplicationAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        cabinetApi.ready(id)
    }

}