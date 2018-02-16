package com.juliendangers.jooqsfm.questionnaire.controllers


import com.juliendangers.jooqsfm.questionnaire.services.QuestionnaireService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class QuestionnaireController(private val service: QuestionnaireService) {

    @GetMapping("/questionnaires/{id}")
    fun findById1(@PathVariable id: UUID, @RequestParam(required = false) type: Int) = when (type) {
        2 -> service.findById2(id)
        3 -> service.findById3(id)
        else -> service.findById1(id)
    }
}
