package com.example.simpletextcomposeapplication

import org.junit.Assert.assertTrue
import org.junit.Test

class Factorial {
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
}