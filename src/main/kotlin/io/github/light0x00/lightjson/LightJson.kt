package io.github.light0x00.lightjson

/**
 * @author light
 * @since 2023/3/26
 */
class LightJson {
    companion object {
        @JvmStatic
        fun parseList(reader: IReader): List<Any?> {
            return parseArray(reader)
        }

        @JvmStatic
        fun parseList(s: String): List<Any?> {
            return parseList(StringReader(s))
        }

        @JvmStatic
        fun parseMap(reader: IReader): MutableMap<String, Any?> {
            return parseKeyVal(reader)
        }

        @JvmStatic
        fun parseMap(s: String): MutableMap<String, Any?> {
            return parseMap(StringReader(s))
        }

        @JvmStatic
        fun stringify(obj: Any): String {
            return Stringify(obj).get();
        }
    }
}