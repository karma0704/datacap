package io.edurt.datacap.parser.trino

import io.edurt.datacap.parser.ParserResponse
import io.edurt.datacap.parser.type.StatementType
import org.junit.Assert.assertTrue
import org.junit.Test
import org.slf4j.LoggerFactory.getLogger

class TrinoHelperTest {
    private val log = getLogger(TrinoHelperTest::class.java)

    private fun printLog(response: ParserResponse) {
        log.info("engine: [ {} ]", response.engine)
        log.info("type: [ {} ]", response.type)
        log.info("isParser: [ {} ]", response.isParser)
        log.info("database name: [ {} ]", response.table.database)
        log.info("table name: [ {} ]", response.table.name)
        response.table.columns.forEach {
            log.info("column name: [ {} ], alias: [ {} ], type: [ {} ], expression: [ {} ], functions: [ {} ]", it.name, it.alias, it.type, it.expression, it.functions)
        }
    }

    @Test
    fun test() {
        val response = TrinoHelper.parse("SELECT 1")
        printLog(response)
        assertTrue(response.type == StatementType.SELECT)
    }

    @Test
    fun testFromTable() {
        val response = TrinoHelper.parse("SELECT \"name\", \"age\" FROM \"a_table_name\"")
        printLog(response)
        assertTrue(response.isParser)
    }

    @Test
    fun testFromTableWithAlias() {
        val response = TrinoHelper.parse("SELECT \"name\" AS \"name\", \"age\" AS \"age\", \"summer\" FROM \"a_table_name\"")
        printLog(response)
    }
}
