package com.bsuir.neural_network.sources.model.home

import com.bsuir.neural_network.app.dto.PersonAnswerDTO
import com.bsuir.neural_network.app.dto.utils.HttpResponse
import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
import com.bsuir.neural_network.sources.backend.BackendRetrofitSource
import com.bsuir.neural_network.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.Response

class RetrofitHomeSource(
    config: RetrofitConfig
) : BackendRetrofitSource(config), HomeSource {

    private val homeApi = retrofit.create(HomeApi::class.java)

    override suspend fun getAllSA(): Response<List<SampleApplicationDTO>>  = wrapRetrofitExceptions {
        delay(1000)
        homeApi.getAllSA()
    }

    override suspend fun personRegistration(
        personDTO: PersonDTO,
        body: MultipartBody.Part,
    ): Response<HttpResponse>  = wrapRetrofitExceptions {
        delay(1000)
        homeApi.personRegistration(personDTO.name,
            personDTO.surname,
            personDTO.patronymic,
            personDTO.passportSeries,
            personDTO.passportNumber,
            personDTO.dateOfBirth,
            body)
    }

    override suspend fun applicationRegistration(id: Long): Response<HttpResponse> = wrapRetrofitExceptions {
        delay(1000)
        homeApi.applicationRegistration(id)
    }

    override suspend fun getPeople(): Response<List<PersonAnswerDTO>> = wrapRetrofitExceptions {
        delay(1000)
        homeApi.getPeople()
    }

    override suspend fun add(id: Long): Response<HttpResponse> = wrapRetrofitExceptions {
        delay(1000)
        homeApi.addManager(id)
    }

    override suspend fun delete(id: Long): Response<HttpResponse> = wrapRetrofitExceptions {
        delay(1000)
        homeApi.deleteManager(id)
    }

}