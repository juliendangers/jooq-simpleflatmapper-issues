package com.juliendangers.jooqsfm.questionnaire.domain.dto

import com.juliendangers.jooqsfm.persistence.tables.records.ReponseRecord
import java.time.Instant
import java.util.*

data class ReponseDto(
        val id: UUID,
        val libelle: String,
        val key: String,
        val conditions: List<ReponseEvaluation>? = emptyList(),
        val validations: List<ReponseEvaluation>? = emptyList(),
        val questionSuivante: UUID?,
        val dateCreation: Instant,
        val dateMiseAJour: Instant?
) {
    companion object {
        fun fromRecord(reponse: ReponseRecord, reponseEvaluations: List<ReponseEvaluation>): ReponseDto {
            val pairOfEvaluations = reponseEvaluations.partition { evaluation -> evaluation.type == ReponseEvaluationType.Condition }
            return ReponseDto(
                    reponse.idreponse,
                    reponse.libelle,
                    reponse.key,
                    pairOfEvaluations.first,
                    pairOfEvaluations.second,
                    reponse.idquestionSuivante,
                    reponse.datecreation.toInstant(),
                    reponse.datemiseajour.toInstant()
            )
        }
    }
}
