package com.bsuir.neural_network.app.dto

data class ImageAnswerDTO (
    val id: Long,
    val url: String,
    val keywords: Set<String>
)