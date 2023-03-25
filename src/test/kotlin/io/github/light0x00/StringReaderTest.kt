package io.github.light0x00

import io.github.light0x00.lightjson.StringReader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author light
 * @since 2023/3/24
 */
class StringReaderTest {

    @Test
    fun testNearbyChars() {
        val stringReader = StringReader("0123456789012345678901234567890123456789")
        for (i in 0..9) {
            stringReader.read()
        }
        println(stringReader.nearbyChars())
        Assertions.assertEquals(
            stringReader.nearbyChars(),
            """
            012345678901234567890
                     ^
            """.trimIndent()
        )

    }

    @Test
    fun testMatch(){
        val reader = StringReader("fruits:apple cherry peach")
        for(i in 0..6){
            reader.read()
        }
        reader.match("cherry","apple")
        Assertions.assertEquals(reader.peek(),' ')

    }
}