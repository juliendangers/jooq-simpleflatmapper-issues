package com.juliendangers.jooqsfm.questionnaire.domain.dto

data class Variable (val key: String, val type: String)

enum class VariableType {
    Numeric, Boolean
}