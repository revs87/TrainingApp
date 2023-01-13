package com.example.simpletextcomposeapplication

import org.junit.Assert.assertTrue
import org.junit.Test

class Math {
    /*
        First Factorial
            Have the function FirstFactorial(num) take the num parameter being passed and return
            the factorial of it. For example: if num = 4, then your program should return
            (4 * 3 * 2 * 1) = 24. For the test cases, the range will be between 1 and 18 and the
            input will always be an integer.
        Examples
            Input: 4
            Output: 24

            Input: 8
            Output: 40320
    */

    private fun getFirstFactorial(num: Int): Int {
        if (num == 0) return 0
        if (num == 1) return 1
        var result = 1
        for (i in num downTo 1) {
            result *= i
        }

        println(result)
        return result
    }

    @Test
    fun test_FirstFactorialOf_4() {
        assertTrue("24" == getFirstFactorial(4).toString())
    }

    @Test
    fun test_FirstFactorialOf_8() {
        assertTrue("40320" == getFirstFactorial(8).toString())
    }

    /*
    * You are given an m x n integer grid accounts where accounts[i][j] is the amount of money the
    * i^th customer has in the j^th bank. Return the wealth that the richest customer has.
    *
    * A customer's wealth is the amount of money they have in all their bank accounts. The richest
    * customer is the customer that has the maximum wealth.
    *
    * */

    private fun maximumWealth(accounts: Array<IntArray>): Int {
        var max = 0
        accounts.forEach {
            val sum = it.sum()
            if (sum > max) { max = sum }
        }
        return max
    }

    @Test
    fun test_maxWealth() {
        val account1 = intArrayOf(43369, 23443, 2034)
        val account2 = intArrayOf(369, 243, 34, 400)
        val account3 = intArrayOf(104505, 434, 110)
        val account4 = intArrayOf(2342345)
        val account5 = intArrayOf(45345, 334, 232)
        val accounts = arrayOf(account1, account2, account3, account4, account5)
        assertTrue(2342345 == maximumWealth(accounts))
    }

}