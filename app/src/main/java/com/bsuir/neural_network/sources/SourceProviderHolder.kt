package com.bsuir.neural_network.sources

import com.bsuir.neural_network.Singletons
import com.bsuir.neural_network.app.setting.AppSettings
import com.bsuir.neural_network.sources.backend.RetrofitConfig
import com.bsuir.neural_network.sources.backend.RetrofitSourcesProvider
import com.bsuir.neural_network.sources.backend.SourcesProvider
import com.bsuir.neural_network.sources.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object SourceProviderHolder {

    val sourcesProvider: SourcesProvider by lazy {
        val moshi = Moshi.Builder().
        add(Date::class.java, Rfc3339DateJsonAdapter()).
        addLast(KotlinJsonAdapterFactory()).
        build()
        val config = RetrofitConfig(
            retrofit = createRetrofit(moshi),
            moshi = moshi
        )
        RetrofitSourcesProvider(config)
    }

    /**
     * Create an instance of Retrofit client.
     */
    private fun createRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Create an instance of OkHttpClient with interceptors for authorization
     * and logging (see [createAuthorizationInterceptor] and [createLoggingInterceptor]).
     */
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor(Singletons.appSettings))
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    /**
     * Add Authorization header to each request if JWT-token exists.
     */
    private fun createAuthorizationInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = settings.getCurrentToken()
            if (token != null) {
                newBuilder.addHeader("Authorization", "Bearer $token")
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    /**
     * Log requests and responses to LogCat.
     */
    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}