package io.github.light0x00

import io.github.light0x00.lightjson.LightJsonException
import io.github.light0x00.lightjson.Stringify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author light
 * @since 2023/3/26
 */
class StringifyTest {

    class User(val name: String, val id: Int? = null, var friend: User? = null, val friends: List<User> = emptyList()) {
        override fun toString(): String {
            return "User(name='$name')"
        }
    }

    @Test
    fun test() {
        Stringify(
            User(
                "Jack", 1,
                friends = listOf(User("Alice", 2), User("Eminem", 3))
            )
        ).apply {
            println(get())
        }
    }

    @Test
    fun testCircularDependencies() {
        val alice = User("Alice")
        val bob = User("Bob")
        val cindy = User("Cindy")

        alice.friend = bob
        bob.friend = cindy
        cindy.friend = alice

        Assertions.assertThrows(LightJsonException::class.java) { Stringify(alice) }
    }
}