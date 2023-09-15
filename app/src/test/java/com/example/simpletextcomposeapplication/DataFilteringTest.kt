package com.example.simpletextcomposeapplication

import assertk.assertThat
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

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
        assertThat(expected.toTypedArray().contentEquals(getUniqueCapsFree(list).toTypedArray())).isTrue()
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

    /*
    *
    * Have the function QuestionsMarks(str) take the str string parameter, which will contain single
    *  digit numbers, letters, and question marks, and check if there are exactly 3 question marks
    *  between every pair of two numbers that add up to 10. If so, then your program should return
    *  the string true, otherwise it should return the string false. If there aren't any two numbers
    *  that add up to 10 in the string, then your program should return false as well.
    * For example: if str is "arrb6???4xxbl5???eee5" then your program should return true because
    *  there are exactly 3 question marks between 6 and 4, and 3 question marks between 5 and 5 at
    *  the end of the string.
    *
    * */

    private fun isTen(str: String): Boolean {
        val regex = "[1-9][?]\\D*[?]\\D*[?][1-9]".toRegex()

        if (!regex.containsMatchIn(str)) { return false }

        val subStr: String = regex.find(str)?.value ?: return false

        println("subStr:$subStr")

        val firstDigit = subStr.first().digitToInt()
        val lastDigit = subStr.last().digitToInt()
        if (firstDigit + lastDigit != 10) { return false }

        return true
    }

    @Test
    fun test_isTen() {
        assert(isTen("aa6?9"))
        assert(isTen("acc?7??sss?3rr1??????5"))
        assert(isTen("arrb6??sss?4xxbl5???eee5"))
    }

}