package io.github.light0x00

import io.github.light0x00.lightjson.Graph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author light
 * @since 2023/3/28
 */
class GraphTest {

    @Test
    fun testPath() {
        val g = Graph<Any>()
        g.addEdge(1, 2)
        g.addEdge(2, 3)
        g.addEdge(2, 4)
        g.addEdge(3, 1)
        g.addEdge(4, 5)

        val path = g.path(2, 2)
        Assertions.assertIterableEquals(listOf(2, 3, 1, 2), path)
    }
}