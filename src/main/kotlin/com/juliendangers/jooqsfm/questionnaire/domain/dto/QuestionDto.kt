package com.juliendangers.jooqsfm.questionnaire.domain.dto

import com.juliendangers.jooqsfm.persistence.tables.records.QuestionRecord
import java.time.Instant
import java.util.*

data class QuestionDto(
        val id: UUID,
        val key: String,
        val description: String,
        val genereNouvelleBranche: Boolean,
        val estValide: Boolean,
        val dateCreation: Instant,
        val dateMiseAJour: Instant?,
        val typeQuestion: TypeQuestion? = null,
        val reponses: List<ReponseDto>? = emptyList()
) {
    companion object {
        fun fromRecord(question: QuestionRecord, typeQuestion: TypeQuestion? = null, reponses: List<ReponseDto>? = emptyList()) =
                QuestionDto(
                        question.idquestion,
                        question.key,
                        question.description,
                        question.generenouvellebranche,
                        question.estvalide,
                        question.datecreation.toInstant(),
                        question.datemiseajour.toInstant(),
                        typeQuestion,
                        reponses
                )
    }
}
