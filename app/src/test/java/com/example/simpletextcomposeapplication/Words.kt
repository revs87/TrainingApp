package com.example.simpletextcomposeapplication

import org.junit.Assert
import org.junit.Test

class Words {

    /*
    *
    * Have the function LongestWord(sen) take the sen parameter being passed and return the longest
    *  word in the string. If there are two or more words that are the same length, return the first
    *  word from the string with that length. Ignore punctuation and assume sen will not be empty.
    * Words may also contain numbers, for example "Hello world123 567"
    *
    * */

    private fun longestWord(sen: String): String {
        var max = ""
        val curr = mutableListOf<Char>()
        sen.forEach {
            if (it.isLetter() || it.isDigit()) {
                curr.add(it)
                if (curr.size > max.length) { max = curr.joinToString("") }
            } else {
                curr.clear()
            }
            //println("Max:" + max + " Curr:" + curr.joinToString(""))
        }

        // code goes here
        return max
    }

    @Test
    fun test_word() {
        Assert.assertTrue("Hello" == longestWord("Hello John!"))
    }

    @Test
    fun test_wordWithNumbers() {
        Assert.assertTrue("world123" == longestWord("Hello world123 567!"))
    }

    @Test
    fun test_wordWithBigNumbers() {
        Assert.assertTrue("567234234" == longestWord("Hello world123 567234234!"))
    }
}