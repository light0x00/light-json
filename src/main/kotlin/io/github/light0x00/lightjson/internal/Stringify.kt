package io.github.light0x00.lightjson.internal

import io.github.light0x00.lightjson.Graph
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties


fun stringify() {

}

/**
 * @author light
 * @since 2023/3/26
 */
class Stringify(obj: Any?) {
    private val builder = StringBuilder()
    private val dependencies = Graph<Any>()

    init {
        traverse(obj)
    }

    fun get(): String {
        return builder.toString();
    }

    private fun traverse(obj: Any?) {
        when (obj) {
            is List<*> -> {
                builder.append("[")
                for (ele in obj) {
                    if (ele != null) markDependencies(obj, ele)
                    traverse(ele)
                    builder.append(",")
                }
                builder.apply {
                    if (last() == ',') deleteCharAt(builder.lastIndex)
                }
                builder.append("]")
            }
            is String -> builder.append("\"$obj\"")
            is Number, is Boolean -> builder.append("$obj")
            null -> builder.append("null")
            else -> {
                builder.append("{")
                for (prop in obj::class.memberProperties) {
                    if (prop.visibility == KVisibility.PUBLIC) {
                        builder.append("\"${prop.name}\":")
                        traverse(prop.getter.call(obj)
                            .also {
                                if (it != null) markDependencies(obj, it);
                            }
                        )
                        builder.append(",")
                    }
                }
                builder.apply {
                    if (last() == ',') deleteCharAt(builder.lastIndex)
                }
                builder.append("}")
            }
        }
    }

    private fun markDependencies(from: Any, to: Any) {
        var path = dependencies.path(to, from)
        if (path != null) {
            throw LightJsonException(
                """Circular dependencies detected:
                |${path.joinToString(separator = "->")}
            """.trimMargin()
            )
        }
        dependencies.addEdge(from, to)
    }
}