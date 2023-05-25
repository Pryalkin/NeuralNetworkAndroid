package com.bsuir.neural_network.sources.backend

import com.bsuir.neural_network.sources.model.auth.AuthSource
import com.bsuir.neural_network.sources.model.auth.RetrofitAuthSource
import com.bsuir.neural_network.sources.model.home.HomeSource
import com.bsuir.neural_network.sources.model.home.RetrofitHomeSource

class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider{

    override fun getAuthSource(): AuthSource {
        return RetrofitAuthSource(config)
    }

    override fun getHomeSource(): HomeSource {
        return RetrofitHomeSource(config)
    }


}