package com.juliendangers.jooqsfm.config

import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.jooq.impl.DataSourceConnectionProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import javax.sql.DataSource
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultExecuteListenerProvider


import org.jooq.ExecuteContext
import org.jooq.impl.DefaultExecuteListener
import org.springframework.context.annotation.Configuration

import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator

class ExceptionTranslator : DefaultExecuteListener() {

    override fun exception(context: ExecuteContext?) {
        val dialect = context!!.configuration().dialect()
        val translator = SQLErrorCodeSQLExceptionTranslator(dialect.name)

        context.exception(translator.translate("Access database using jOOQ", context.sql(), context.sqlException()))
    }
}

@Configuration
class JooqConfiguration @Autowired constructor(private val dataSource: DataSource) {

    @Bean
    fun connectionProvider(): DataSourceConnectionProvider {
        return DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource!!))
    }

    @Bean
    fun dsl(): DefaultDSLContext {
        return DefaultDSLContext(configuration())
    }

    fun configuration(): DefaultConfiguration {
        val jooqConfiguration = DefaultConfiguration()
        jooqConfiguration.set(connectionProvider())
        jooqConfiguration
                .set(DefaultExecuteListenerProvider(ExceptionTranslator()))

        return jooqConfiguration
    }
}