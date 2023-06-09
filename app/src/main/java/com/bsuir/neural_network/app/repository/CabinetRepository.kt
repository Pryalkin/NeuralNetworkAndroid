package com.bsuir.neural_network.app.repository

import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.sources.model.cabinet.CabinetSource

class CabinetRepository (
    private val cabinetSource: CabinetSource,
    private val appSettings: AppSettings
){

    fun getRole(): String?{
        return appSettings.getCurrentRole()
    }


    fun logout(){
        appSettings.setCurrentToken("")
        appSettings.setCurrentUsername("")
        appSettings.setCurrentRole("")
    }


}