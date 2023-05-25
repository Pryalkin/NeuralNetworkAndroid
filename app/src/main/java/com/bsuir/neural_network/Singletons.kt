package com.bsuir.neural_network

import android.content.Context
import androidx.fragment.app.Fragment
import com.bsuir.neural_network.sources.backend.SourcesProvider
import com.bsuir.neural_network.app.repository.HomeRepository
import com.bsuir.neural_network.app.repository.AuthRepository
import com.bsuir.neural_network.app.screens.Navigator
import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.app.setting.SharedPreferencesAppSettings
import com.bsuir.neural_network.sources.SourceProviderHolder
import com.bsuir.neural_network.sources.model.auth.AuthSource
import com.bsuir.neural_network.sources.model.home.HomeSource


object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // source
    private val authSource: AuthSource by lazy {
        sourcesProvider.getAuthSource()
    }

    private val homeSource: HomeSource by lazy {
        sourcesProvider.getHomeSource()
    }

    // repository
    val authRepository: AuthRepository by lazy {
        AuthRepository(
            authSource = authSource,
            appSettings = appSettings
        )
    }

    val homeRepository: HomeRepository by lazy {
        HomeRepository(
            homeSource = homeSource,
            appSettings = appSettings
        )
    }

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }

    fun Fragment.navigator() = requireActivity() as Navigator
}