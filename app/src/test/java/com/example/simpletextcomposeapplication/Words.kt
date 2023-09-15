package com.example.simpletextcomposeapplication

import org.junit.Assert
import org.junit.jupiter.api.Test

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


    /**
     *
     * Given the input of words: "A a b b C"
     * It shall return a map of <word, numberOfOccurrences>
     *     output:
     *              A, 1
     *              a, 1
     *              b, 2
     *              C, 1
     *
     * */

    private fun wordCounter(input: String): Map<String, Int> {
        val data = mutableMapOf<String, Int>()
        val words = input.split(' ')
            .map { it.trim() }
            .filter { it.isNotBlank() }
        words.map {
            if (data.containsKey(it)) {
                data.put(it, data[it]?.plus(1) ?: 0)
            } else {
                data.put(it, 1)
            }
        }
        //data.map { println("${it.key} ${it.value}") }
        return data
    }

    private fun assertMapEquals(expected: Map<String, Int>, actual: Map<String, Int>) {
        Assert.assertEquals(expected.keys.toList(), actual.keys.toList())
        Assert.assertEquals(expected.values.toList(), actual.values.toList())
    }

    @Test
    fun test_wordCount() {
        val input = "I am Rui Vieira Vieira"
        val expected = mapOf(
            "I" to 1,
            "am" to 1,
            "Rui" to 1,
            "Vieira" to 2,
        )
        assertMapEquals(expected, wordCounter(input))
    }

    @Test
    fun test_wordCount_withLeadingAndTrailingSpaces() {
        val input = "  I  am  Rui Vieira  Vieira "
        val expected = mapOf(
            "I" to 1,
            "am" to 1,
            "Rui" to 1,
            "Vieira" to 2,
        )
        assertMapEquals(expected, wordCounter(input))
    }

}