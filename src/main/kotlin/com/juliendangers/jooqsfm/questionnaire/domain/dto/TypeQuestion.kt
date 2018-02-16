package com.juliendangers.jooqsfm.questionnaire.domain.dto

import com.juliendangers.jooqsfm.persistence.tables.records.TypequestionRecord

data class TypeQuestion(
        val id: Int,
        val libelle: String,
        val nombreReponseMin: Int = 2,
        val nombreReponseMax: Int = 10,
        val memeQuestionSuivante: Boolean = false,
        val definitionDeVariable: Boolean,
        val typeVariable: String,
        val valeurParDefaut: String
) {
    companion object {
        fun fromRecord(typeQuestion: TypequestionRecord) =
                TypeQuestion(
                        typeQuestion.idtypequestion.intValueExact(),
                        typeQuestion.libelle,
                        typeQuestion.nombrereponsemin.intValueExact(),
                        typeQuestion.nombrereponsemax.intValueExact(),
                        typeQuestion.memequestionsuivante,
                        typeQuestion.definitiondevariable,
                        typeQuestion.variabletype,
                        typeQuestion.valeurpardefaut
                )
    }
}
