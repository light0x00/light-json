package io.github.light0x00.lightjson.internal

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//import java.util.*

fun readUnexpectedErrorMsg(reader: IReader, actual: String, expected: String): String {
    return """
                |${expected} expected ,but got ${actual} at line ${reader.line()} column ${reader.column()}
            """.trimMargin("|")
}

fun readUnexpectedErrorMsg(reader: IReader, expected: String): String {
    return readUnexpectedErrorMsg(reader, ":\n" + reader.nearbyChars(), expected)
}

fun readErrorMsg(reader: IReader, msg: String): String {
    return """
                |$msg
                |${reader.nearbyChars()}
                | at line ${reader.line()} column ${reader.column()}
                """.trimMargin()
}

class Graph<T> {
    private val G = HashMap<T, MutableList<T>>()

    fun addEdge(from: T, to: T) {
        if (G[from] == null) {
            G[from] = ArrayList()
        }
        G[from]!!.add(to);
    }

    fun path(from: T, to: T): List<T>? {
        val stack = Stack<T>().apply { add(from) }
        val visited = HashSet<T>().apply { add(from) }
        val track = HashMap<T, T>()

        while (stack.isNotEmpty()) {
            val node = stack.pop()
            val adjacencyList = G[node] ?: continue
            for (adj in adjacencyList) {
                track[adj] = node
                if (adj == to)
                    return backtrack(from, to, track)

                if (visited.contains(adj))
                    continue
                stack.add(adj)
                visited.add(adj)
            }
        }
        return null
    }

    private fun backtrack(from: T, to: T, track: HashMap<T, T>): List<T> {
        val queue = LinkedList<T>().apply {
        }
        var node = track.get(to)
        while (node != from) {
            queue.addFirst(node)
            node = track.get(node)
        }
        queue.addFirst(from)
        queue.addLast(to)
        return queue
    }
}