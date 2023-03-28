package io.github.light0x00

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class Fuck(x:Int,y:Int){
}

fun f() {
    var a = 1
    // simple name in template:
    val s1 = "a is $a"

    a = 2
    // arbitrary expression in template:
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)

    val items = listOf("apple", "banana", "kiwifruit")
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    for(x in 1..9) {
        println(x)
    }

    val f = Fuck(1,2);

}

class TestObj(val x : Int, private val  y : Int){

}

fun main() = runBlocking { // this: CoroutineScope
    reflect(
        TestObj(1,3)
    )
}


fun reflect(obj : Any){
    val c = obj::class
    val c2 = obj::javaClass

    for(p in  obj::class.memberProperties){
        if (p.visibility == KVisibility.PUBLIC) {
            println(p.name+","+p.getter.call(obj))
        }
    }
}

fun maxOf(a: Int, b: Int): Int = if (a > b) a else b

/**
 * @author light
 * @since 2023/3/24
 */
class KotlinSyntax {


}