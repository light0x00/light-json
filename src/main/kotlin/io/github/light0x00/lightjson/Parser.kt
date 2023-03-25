package io.github.light0x00.lightjson

import io.github.light0x00.lightjson.Toolkit.Companion.readErrorMsg
import io.github.light0x00.lightjson.Toolkit.Companion.readUnexpectedErrorMsg
import java.util.*


/**
 * @author light
 * @since 2023/3/25
 */

fun parseKeyVal(reader: IReader): MutableMap<String, Any?> {
    reader.match("{")
    val kv: MutableMap<String, Any?> = HashMap()

    while (reader.peek() != '}') {
        val key: String = parseString(reader)
        reader.match(":")
        val value: Any? = parseValue(reader)
        kv[key] = value
        if (reader.peek() == ',') {
            reader.read()
        }
    }
    reader.read()
    return kv
}

fun parseArray(reader: IReader): List<Any?> {
    reader.match("[")
    val lst: MutableList<Any?> = ArrayList()
    while (reader.peek() != ']') {
        lst.add(parseValue(reader))
        if (reader.peek() == ',') {
            reader.read()
        }
    }
    reader.read()
    return lst
}

fun parseValue(reader: IReader): Any? {
    return when (reader.peek()) {
        '"' -> parseString(reader)
        'n' -> {
            parseLiteral(reader, "null")
            null
        }
        't' -> {
            parseLiteral(reader, "true")
            true
        }
        'f' -> {
            parseLiteral(reader, "false")
            false
        }
        '{' -> parseKeyVal(reader)
        '[' -> parseArray(reader)
        else -> parseNumber(reader)
    }
}

fun parseLiteral(reader: IReader, literal: String): String {
    for (element in literal) {
        if (element != reader.read()) {
            throw LightJsonException(readUnexpectedErrorMsg(reader, literal))
        }
    }
    return literal
}


fun parseString(reader: IReader): String {
    var lookahead = reader.read()
    if (lookahead != '"') {
        throw LightJsonException(readUnexpectedErrorMsg(reader, "\""))
    }
    val str = StringBuilder()
    //When you see also in code, you can read it as "and also do the following with the object."
    while ((reader.read().also { lookahead = it }) != '"') {
        if (lookahead == null) {
            throw LightJsonException(readUnexpectedErrorMsg(reader, "\u0000", "string literal"))
        } else if (lookahead == '\\') {
            if (reader.peek() == '\"') {
                lookahead = reader.read() //跳过转义双引号的字符
            }
            //控制字符、unicode 转义和反转义问题 可参考 StringEscapeUtils.escapeJava 和 unescapeJava 的实现
        }
        str.append(lookahead)
    }
    return str.toString()
}

val NUMERIC: Set<*> = HashSet(listOf('-', '+', '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'))

fun parseNumber(reader: IReader): Number {
    var existDecimalPoint = false
    var isFirst = true
    val number = StringBuilder()
    var lookahead: Char?
    while (NUMERIC.contains(reader.peek())) {
        lookahead = reader.read()
        //数字不能包含1一个以上的小数点
        if (lookahead == '.') {
            if (existDecimalPoint) {
                throw LightJsonException(readErrorMsg(reader, "Illegal number"))
            }
            existDecimalPoint = true
        }
        //非第一个字符数字不能是正负号
        if (lookahead == '+' || lookahead == '-') {
            if (!isFirst)
                throw LightJsonException(readErrorMsg(reader, "Illegal number"))
        }
        number.append(lookahead)
        isFirst = false
    }
    if (number.isEmpty() ||
        number.length == 1 && (number.last() in listOf('.', '+', '-'))
    ) {
        throw LightJsonException(readErrorMsg(reader, "Illegal number"))
    }
    return if (existDecimalPoint) number.toString().toDouble() else number.toString().toInt() //考虑 是否溢出、数字类型问题.
}