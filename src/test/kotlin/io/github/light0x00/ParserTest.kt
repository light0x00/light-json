package io.github.light0x00

import io.github.light0x00.lightjson.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author light
 * @since 2023/3/25
 */
class ParserTest {

    @Test
    fun testParseValue() {
        Assertions.assertIterableEquals(
            parseValue(StringReader("[1,2,3]")) as MutableIterable<*>?,
            listOf(1, 2, 3)
        )
        Assertions.assertEquals(
            parseValue(StringReader("""{"a":123,"b":false,"c":"cccc"}""")),
            mapOf<String,Any>("a" to 123, "b" to false, "c" to "cccc")
        )

        var parseValue =
            parseValue(StringReader("""{"a":123,"b":false,"c":"c\"c\"cc","d":[{},1,"2",false,null,{"e":"eeee"}]}"""))
        println(parseValue)
    }

    @Test
    fun testParseLiteral() {
        Assertions.assertEquals(
            parseLiteral(StringReader("null"), "null"),
            "null"
        )
    }

    @Test
    fun testParseString() {
        val s1 = parseString(StringReader("\"\""))
        Assertions.assertEquals("", s1)

        val s2 = parseString(StringReader("\"\\\"\""))
        Assertions.assertEquals("\"", s2)
    }

    @Test
    fun testParseNumber() {
        Assertions.assertEquals(1234, tryParseNumber(StringReader("1234")))
        Assertions.assertEquals(0.1234, tryParseNumber(StringReader(".1234,56")))
        Assertions.assertEquals(123.4, tryParseNumber(StringReader("+123.4,56")))
        Assertions.assertEquals(-123.4, tryParseNumber(StringReader("-123.4,56")))
        Assertions.assertThrows(
            LightJsonException::class.java,
            { tryParseNumber(StringReader("123.4.56")) },
            "Illegal number"
        )
    }
}