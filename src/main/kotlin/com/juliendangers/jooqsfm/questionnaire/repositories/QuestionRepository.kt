package com.juliendangers.jooqsfm.questionnaire.repositories

import arrow.core.Option
import com.juliendangers.jooqsfm.persistence.tables.Question
import com.juliendangers.jooqsfm.persistence.tables.Reponse
import com.juliendangers.jooqsfm.persistence.tables.Typequestion
import com.juliendangers.jooqsfm.persistence.tables.records.QuestionRecord
import com.juliendangers.jooqsfm.persistence.tables.records.ReponseRecord
import com.juliendangers.jooqsfm.persistence.tables.records.TypequestionRecord
import com.juliendangers.jooqsfm.questionnaire.domain.dto.QuestionDto
import com.juliendangers.jooqsfm.questionnaire.domain.dto.ReponseDto
import com.juliendangers.jooqsfm.questionnaire.domain.dto.TypeQuestion
import com.juliendangers.jooqsfm.utils.toOption
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.lambda.tuple.Tuple3
import org.simpleflatmapper.jdbc.JdbcMapperFactory
import org.simpleflatmapper.util.TypeReference
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors


@Repository
class QuestionRepository(val dsl: DSLContext) {

    val defaultQueryDSL = { conditions: Condition ->
        dsl.select()
                .from(QUESTION)
                .join(TYPE_QUESTION).using(QUESTION.IDTYPEQUESTION)
                .join(REPONSE).using(QUESTION.IDQUESTION)
                .where(conditions)
                .orderBy(QUESTION.IDQUESTION)
    }

    fun findByQuestionnaireId(questionnaireId: UUID): List<QuestionDto> {
        val rs = defaultQueryDSL(QUESTION.IDQUESTIONNAIRE.cast(String::class.java).eq(questionnaireId.toString()))
                .fetchResultSet()
        return jdbcMapper.stream(rs)
                .map(resultSetMapper)
                .collect(Collectors.toList())
                .toList()
    }

    fun findById(id: UUID): Option<QuestionDto> {
        val rs = defaultQueryDSL(QUESTION.IDQUESTION.eq(id))
                .fetchResultSet()
        return jdbcMapper.stream(rs)
                .map(resultSetMapper)
                .findFirst()
                .toOption()
    }

    companion object {
        val QUESTION = Question.QUESTION
        val TYPE_QUESTION = Typequestion.TYPEQUESTION
        val REPONSE = Reponse.REPONSE

        val jdbcMapper = JdbcMapperFactory
                .newInstance()
                .addKeys(QUESTION.IDQUESTION.name, REPONSE.IDREPONSE.name)
                .newMapper(object : TypeReference<Tuple3<QuestionRecord, TypequestionRecord, List<ReponseRecord>>>() {})

        val resultSetMapper = { result: Tuple3<QuestionRecord, TypequestionRecord, List<ReponseRecord>> ->
            System.out.println(result.toString())
            QuestionDto.fromRecord(result.v1,
                    TypeQuestion.fromRecord(result.v2),
                    result.v3.map {
                        ReponseDto.fromRecord(it, emptyList())
                    })
        }
    }
}