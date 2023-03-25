package io.github.light0x00

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
/*
https://kotlinlang.org/docs/coroutines-and-channels.html
* */

/*
he main difference is that the runBlocking method blocks the current thread for waiting,
while coroutineScope just suspends,releasing the underlying thread for other usages.
*/
fun main() = runBlocking { // this: CoroutineScope
    val r = doHello()
    println("Done")
}

suspend fun doHello() =
    coroutineScope {
        //启动一个子任务
        /*
        A launch coroutine builder returns a Job object
        that is a handle to the launched coroutine and can be used to explicitly wait for its completion.
         */
        val job = launch {
            doWorld()
        }
        println("Hello")
        job.join()
        println("!");
    }


suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
    launch {
        delay(2000L)
        println("World1")
    }
    launch {
        delay(1000L)
        println("World2")
    }
}

