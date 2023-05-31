package com.bsuir.neural_network.app.dto.utils

enum class SearchScore(private val displayValue: String,
                        private val value: Int) {

    NINETY_FIVE("NINETY_FIVE", 10),
    NINETY("NINETY", 9),
    EIGHTY("EIGHTY", 8),
    SEVENTY("SEVENTY", 7),
    SIXTY("SIXTY", 6),
    FIFTY("FIFTY", 5),
    FORTY("FORTY", 4),
    THIRTY("THIRTY", 3),
    TWENTY("TWENTY", 2),
    TEN("TEN", 1);

    fun getValue(): Int{
        return value
    }

    override fun toString(): String {
        return displayValue
    }
}