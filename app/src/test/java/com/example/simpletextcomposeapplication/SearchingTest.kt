package com.example.simpletextcomposeapplication

import org.junit.jupiter.api.Test
import kotlin.random.Random

class SearchingTest {

    @Test
    fun `Testing a binary search of a list`() {
        (1..10)
            .map { Random.nextInt(from = 1, until = 10) }
            .sorted().also { println(it) }
            .binarySearch(element = 5)
            .also {
                println("Search index: $it")
            }
    }

}