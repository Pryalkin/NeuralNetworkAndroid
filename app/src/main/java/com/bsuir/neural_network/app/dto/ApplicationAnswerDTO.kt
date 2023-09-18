package com.bsuir.neural_network.app.dto

import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
import java.util.*

data class ApplicationAnswerDTO(
    val id: Long,
    val number: String,
    val personDTO: PersonDTO,
    val file: String,
    val status: String,
    val date: Date,
    val sampleApplicationDTO: SampleApplicationDTO
)
