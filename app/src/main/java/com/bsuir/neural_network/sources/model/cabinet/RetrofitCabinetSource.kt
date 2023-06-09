package com.bsuir.neural_network.sources.model.cabinet

import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.sources.backend.BackendRetrofitSource
import com.bsuir.neural_network.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitCabinetSource (
    config: RetrofitConfig
) : BackendRetrofitSource(config), CabinetSource {

    private val cabinetApi = retrofit.create(CabinetApi::class.java)

}