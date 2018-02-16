package com.juliendangers.jooqsfm.questionnaire.domain.dto

import com.juliendangers.jooqsfm.persistence.tables.records.ReponsevalidationRecord
import java.time.Instant
import java.util.*

data class ReponseEvaluation(
        val id: UUID,
        val idReponse: UUID,
        val operator: String,
        val variables: List<String>,
        val type: ReponseEvaluationType,
        val dateCreation: Instant,
        val dateMiseAJour: Instant?
) {
    companion object {
        fun fromRecord(evaluation: ReponsevalidationRecord) =
            ReponseEvaluation(
                    evaluation.idreponsevalidation,
                    evaluation.idreponse,
                    evaluation.idoperator,
                    evaluation.variables.toList(),
                    ReponseEvaluationType.valueOf(evaluation.type.name),
                    evaluation.datecreation.toInstant(),
                    evaluation.datemiseajour.toInstant()
            )
    }
}

enum class ReponseEvaluationType {
    Condition, Validation
}