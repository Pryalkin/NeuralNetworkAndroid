package com.bsuir.neural_network.sources.backend

import com.bsuir.neural_network.sources.model.auth.AuthSource
import com.bsuir.neural_network.sources.model.cabinet.CabinetSource
import com.bsuir.neural_network.sources.model.home.HomeSource

interface SourcesProvider {

    fun getAuthSource(): AuthSource
    fun getHomeSource(): HomeSource
    fun getCabinetSource(): CabinetSource

}