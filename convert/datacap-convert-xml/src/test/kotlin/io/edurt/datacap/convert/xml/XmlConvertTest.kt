package io.edurt.datacap.convert.xml

import com.google.inject.Guice.createInjector
import com.google.inject.Injector
import io.edurt.datacap.convert.ConvertFilter
import io.edurt.datacap.convert.ConvertManager
import io.edurt.datacap.convert.model.ConvertRequest
import io.edurt.datacap.convert.model.ConvertResponse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory.getLogger

class XmlConvertTest
{
    private val log = getLogger(this::class.java)
    private val name = "Xml"
    private var injector: Injector? = null
    private val request: ConvertRequest = ConvertRequest()

    @Before
    fun before()
    {
        injector = createInjector(ConvertManager())

        request.name = "test"
        request.path = System.getProperty("user.dir")
        request.headers = listOf("name", "age")

        val l1 = listOf("Test", 12)
        val l2 = listOf("Test1", 121)
        request.columns = listOf(l1, l2)
    }

    @Test
    fun testWriter()
    {
        injector?.let { injector ->
            ConvertFilter.filter(injector, name)
                .ifPresent { file ->
                    val response = file.writer(request)
                    log.info("Response: {}", response.toString())
                    assertTrue(response.successful == true)
                }
        }
    }

    @Test
    fun testReader()
    {
        injector?.let { injector ->
            ConvertFilter.filter(injector, name)
                .ifPresent { file ->
                    val response = file.reader(request)
                    print(response)
                    assertTrue(response.successful == true)
                }
        }
    }

    @Test
    fun testFormat()
    {
        injector?.let { injector ->
            ConvertFilter.filter(injector, name)
                .ifPresent { file ->
                    val response = file.format(request)
                    print(response)
                    assertTrue(response.successful == true)
                }
        }
    }

    private fun print(response: ConvertResponse)
    {
        log.info("headers: { ${response.headers} }")
        response.columns
            .let { columns ->
                columns.forEachIndexed { index, line ->
                    log.info("index: [ $index ], line: [ $line ]")
                }
            }
    }
}
