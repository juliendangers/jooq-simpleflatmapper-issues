package com.juliendangers.jooqsfm.questionnaire.services

import com.juliendangers.jooqsfm.questionnaire.repositories.QuestionRepository
import com.juliendangers.jooqsfm.questionnaire.repositories.QuestionnaireRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionnaireService(val repository: QuestionnaireRepository, val questionRepository: QuestionRepository) {

    fun findById1(id: UUID) = repository.findByIdWithQuestionsResponses(id)
    fun findById2(id: UUID) = repository.findByIdWithQuestions(id)
    fun findById3(id: UUID) = repository.findByIdWithRawQuestions(id)

}