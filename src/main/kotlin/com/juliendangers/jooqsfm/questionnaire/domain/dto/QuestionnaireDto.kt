package com.juliendangers.jooqsfm.questionnaire.domain.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.juliendangers.jooqsfm.persistence.tables.records.QuestionnaireRecord
import java.time.Instant
import java.util.*

data class QuestionnaireDto(
        val id: UUID,
        val nom: String,
        val version: Float,
        val action: String,
        val debut: UUID?,
        val active: Boolean,
        val availableVariables: List<Variable>,
        val dateCreation: Instant,
        val questions: List<QuestionDto> = emptyList()
) {
    companion object {

        val mapper = ObjectMapper()
        init {
            mapper.registerModule(KotlinModule())
        }

        fun fromRecord(questionnaire: QuestionnaireRecord, questions: List<QuestionDto> = emptyList()) =
                QuestionnaireDto(
                        questionnaire.idquestionnaire,
                        questionnaire.nom,
                        questionnaire.version.toFloat(),
                        questionnaire.action,
                        questionnaire.idquestionFirst,
                        questionnaire.active,
                        //mapper.treeToValue(questionnaire.availablevariables),
                        emptyList(),
                        questionnaire.datecreation.toInstant(),
                        questions
                )
    }
}
