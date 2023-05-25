package com.bsuir.neural_network.app.dto

data class LoginUserAnswerDTO(
    val username: String,
    val role: String,
    val authorities: Array<String>
)
