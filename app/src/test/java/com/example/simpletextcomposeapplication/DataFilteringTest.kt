package com.example.simpletextcomposeapplication

import org.junit.Assert
import org.junit.Test

class DataFilteringTest {

    data class User(val name: String, val username: String, val email: String)

    @Test
    fun test_filteringByUsername() {
        var log_dump: String =
            "name=John Trust, username=john3, email=john3@gmail.com, id=434453;" +
                    " name=Hannah Smith, username=hsmith, email=hsm@test.com, id=23312;" +
                    " name=Hannah Smith, username=hsmith, id=3223423, email=hsm@test.com;" +
                    " name=Robert M, username=rm44, id=222342, email=rm@me.com;" +
                    " name=Robert M, username=rm4411, id=5535, email=rm@me.com;" +
                    " name=Susan Vee, username=sv55, id=443432, email=susanv123@me.com;" +
                    " name=Robert Nick, username=rnick33, id=23432, email=rnick@gmail.com;" +
                    " name=Robert Nick II, username=rnickTemp34, id=23432, email=rnick@gmail.com;" +
                    " name=Susan Vee, username=sv55, id=443432, email=susanv123@me.com;"
        //var log_dump2: String = "name=Dan B, username=db, email=db@gmail.com, id=123; name=Hannah, username=hsmith, id=333, email=hsm@test.com; name=Dan Brick, username=db, email=db@gmail.com, id=663;"

        val logz: MutableList<User> = mutableListOf()

        val logs = log_dump.split(';')

        for (log in logs) {
            var user: User? = null
            val userData = log.split(',')
            for (data in userData) {
                val field = data.substringBefore('=').trim()
                val value = data.substringAfter('=').trim()
                if (value.isNotBlank()) {
                    val currName = user?.name ?: ""
                    val currUsername = user?.username ?: ""
                    val currEmail = user?.email ?: ""
                    when (field) {
                        "name" ->       user = User(name = value, username = currUsername, email = currEmail)
                        "username" ->   user = User(name = currName, username = value, email = currEmail)
                        "email" ->      user = User(name = currName, username = currUsername, email = value)
                        else -> {}
                    }
                }
            }
            if (user != null) { logz.add(user) }
        }
        val logz2 = logz.distinctBy { it.username }

        log_dump = ""
        for (log in logz2) {
            log_dump += "name=${log.name}, username=${log.username}, email=${log.email}; "
        }
        println(log_dump.trim())
    }

    @Test
    fun test_uniqueStringCapsFree() {
        val expected = listOf("ONE", "Two", "Three")
        val list = listOf("ONE", "One", "ONE", "Two", "Three")
        Assert.assertTrue(expected.toTypedArray().contentEquals(getUniqueCapsFree(list).toTypedArray()))
    }

    private fun getUniqueCapsFree(list: List<String>): List<String> {
        println(list.toSet())                                         // [ONE, One, Two, Three]
        println(list.distinctBy { it.lowercase() })                   // [ONE, Two, Three] - first match
        println(list.associateBy { it.lowercase() }.map { it.value }) // [ONE, Two, Three] - last match
        println(list.associate { Pair(it, it.lowercase()) }
            .map { Pair(it.key, it.value) }
            .reversed()
            .distinctBy { it.second }
            .reversed()
            .map { it.first })                                        // [One, Two, Three]
        return list.distinctBy { it.lowercase() }
    }
}