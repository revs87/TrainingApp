package com.example.simpletextcomposeapplication.adventofcode

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.simpletextcomposeapplication.adventofcode.input.readInput
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/**
 * https://adventofcode.com/2024/day/3
 * */
class Day03 {

    @Test
    fun part1(): Unit = runBlocking {
//        val expected = 161
        val expected = 188192787

        flow<List<Pair<Int, Int>>> {
//            readInput("Day03Example.txt").map { input ->
            readInput("Day03Input.txt").map { input ->
                emit(
                    parseMulOperations(input)//.also { println(it) }
                )
            }
        }.map { listOfPairs ->
            listOfPairs.sumOf { pair ->
                pair.first * pair.second
            }
        }.reduce { acc, value ->
            acc + value
        }.also { sum ->
            assertThat(sum).isEqualTo(expected)
        }
    }

    private fun parseMulOperations(input: String): List<Pair<Int, Int>> {
        val regex = Regex("""mul\((\d+),(\d+)\)""")
        val matches = regex.findAll(input)

        return matches.map { matchResult ->
            val (firstNumber, secondNumber) = matchResult.destructured
            Pair(firstNumber.toInt(), secondNumber.toInt())
        }.toList()
    }
}