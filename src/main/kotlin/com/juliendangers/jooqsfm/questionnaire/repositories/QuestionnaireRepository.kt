package com.juliendangers.jooqsfm.questionnaire.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.juliendangers.jooqsfm.persistence.tables.Question
import com.juliendangers.jooqsfm.persistence.tables.Questionnaire
import com.juliendangers.jooqsfm.persistence.tables.Reponse
import com.juliendangers.jooqsfm.persistence.tables.Typequestion
import com.juliendangers.jooqsfm.persistence.tables.records.QuestionRecord
import com.juliendangers.jooqsfm.persistence.tables.records.QuestionnaireRecord
import com.juliendangers.jooqsfm.persistence.tables.records.ReponseRecord
import com.juliendangers.jooqsfm.persistence.tables.records.TypequestionRecord
import com.juliendangers.jooqsfm.questionnaire.domain.dto.QuestionDto
import com.juliendangers.jooqsfm.questionnaire.domain.dto.QuestionnaireDto
import com.juliendangers.jooqsfm.questionnaire.domain.dto.ReponseDto
import com.juliendangers.jooqsfm.questionnaire.domain.dto.TypeQuestion
import com.juliendangers.jooqsfm.utils.toOption
import org.jooq.DSLContext
import org.jooq.lambda.tuple.Tuple2
import org.jooq.lambda.tuple.Tuple3
import org.simpleflatmapper.jdbc.JdbcMapperFactory
import org.simpleflatmapper.util.TypeReference
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class QuestionnaireRepository (val dsl: DSLContext) {

    fun findByIdWithQuestionsResponses(id: UUID) = jdbcMapper.stream(
            dsl.select()
                    .from(QUESTIONNAIRE)
                    .join(QUESTION).using(QUESTIONNAIRE.IDQUESTIONNAIRE)
                    .join(TYPE_QUESTION).using(QUESTION.IDTYPEQUESTION)
                    .join(REPONSE).using(QUESTION.IDQUESTION)
                    .where(QUESTIONNAIRE.ACTIVE.isTrue)
                    .and(QUESTIONNAIRE.IDQUESTIONNAIRE.cast(String::class.java).eq(id.toString())) // ISSUE WITH JOOQ
                    .orderBy(QUESTIONNAIRE.IDQUESTIONNAIRE)
                    .fetchResultSet()
        )
            .map(resultSetMapper)
            .findFirst()
            .toOption()

    fun findByIdWithQuestions(id: UUID) = jdbcMapper2.stream(
            dsl.select()
                    .from(QUESTIONNAIRE)
                    .join(QUESTION).using(QUESTIONNAIRE.IDQUESTIONNAIRE)
                    .join(TYPE_QUESTION).using(QUESTION.IDTYPEQUESTION)
                    .where(QUESTIONNAIRE.ACTIVE.isTrue)
                    .and(QUESTIONNAIRE.IDQUESTIONNAIRE.cast(String::class.java).eq(id.toString())) // ISSUE WITH JOOQ
                    .orderBy(QUESTIONNAIRE.IDQUESTIONNAIRE)
                    .fetchResultSet()
    )
            .map(resultSetMapper2)
            .findFirst()
            .toOption()

    fun findByIdWithRawQuestions(id: UUID) = jdbcMapper3.stream(
            dsl.select()
                    .from(QUESTIONNAIRE)
                    .join(QUESTION).using(QUESTIONNAIRE.IDQUESTIONNAIRE)
                    .where(QUESTIONNAIRE.ACTIVE.isTrue)
                    .and(QUESTIONNAIRE.IDQUESTIONNAIRE.cast(String::class.java).eq(id.toString())) // ISSUE WITH JOOQ
                    .orderBy(QUESTIONNAIRE.IDQUESTIONNAIRE)
                    .fetchResultSet()
    )
            .map(resultSetMapper3)
            .findFirst()
            .toOption()

    companion object {
        val QUESTIONNAIRE = Questionnaire.QUESTIONNAIRE
        val QUESTION = Question.QUESTION
        val TYPE_QUESTION = Typequestion.TYPEQUESTION
        val REPONSE = Reponse.REPONSE

        val mapper = ObjectMapper()
        init {
            mapper.registerModule(KotlinModule())
        }

        val jdbcMapper = JdbcMapperFactory
                .newInstance()
                .addKeys(QUESTIONNAIRE.IDQUESTIONNAIRE.name, QUESTION.IDQUESTION.name, REPONSE.IDREPONSE.name)
                .newMapper(object : TypeReference<Tuple2<QuestionnaireRecord, List<Tuple3<QuestionRecord, TypequestionRecord, List<ReponseRecord>>>>>() {})

        val resultSetMapper = { result: Tuple2<QuestionnaireRecord, List<Tuple3<QuestionRecord, TypequestionRecord, List<ReponseRecord>>>> ->
            QuestionnaireDto.fromRecord(result.v1, result.v2.map { questions ->
                QuestionDto.fromRecord(questions.v1,
                        TypeQuestion.fromRecord(questions.v2),
                        questions.v3.map {
                            ReponseDto.fromRecord(it, emptyList())
                        })
            })
        }

        val jdbcMapper2 = JdbcMapperFactory
                .newInstance()
                .addKeys(QUESTIONNAIRE.IDQUESTIONNAIRE.name, QUESTION.IDQUESTION.name)
                .newMapper(object : TypeReference<Tuple2<QuestionnaireRecord, List<Tuple2<QuestionRecord, TypequestionRecord>>>>() {})

        val resultSetMapper2 = { result: Tuple2<QuestionnaireRecord, List<Tuple2<QuestionRecord, TypequestionRecord>>> ->
            QuestionnaireDto.fromRecord(result.v1, result.v2.map { questions ->
                QuestionDto.fromRecord(questions.v1,
                        TypeQuestion.fromRecord(questions.v2),
                        emptyList())
            })
        }

        val jdbcMapper3 = JdbcMapperFactory
                .newInstance()
                .addKeys(QUESTIONNAIRE.IDQUESTIONNAIRE.name)
                .newMapper(object : TypeReference<Tuple2<QuestionnaireRecord, List<QuestionRecord>>>() {})

        val resultSetMapper3 = { result: Tuple2<QuestionnaireRecord, List<QuestionRecord>> ->
            QuestionnaireDto.fromRecord(result.v1, result.v2.map { it ->
                QuestionDto.fromRecord(it,
                        null,
                        emptyList())
            })
        }
    }
}
