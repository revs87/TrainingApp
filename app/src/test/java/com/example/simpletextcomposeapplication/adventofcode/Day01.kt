package com.example.simpletextcomposeapplication.adventofcode

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.simpletextcomposeapplication.adventofcode.input.readInput
import org.junit.jupiter.api.Test
import kotlin.math.abs

/**
 * https://adventofcode.com/2024/day/1
 * */
class Day01 {

    @Test
    fun part1() {
        //readInput("Day01Input.txt").also(::println)
        val (firstColumn, secondColumn) = readInput("Day01Input.txt").map { pair ->
            val first = pair.substringBefore(" ").toLong()
            val second = pair.substringAfterLast(" ").toLong()
            first to second
        }.unzip()

        val result = firstColumn.sorted().zip(secondColumn.sorted()) { first, second ->
            abs(first - second)
        }.sum()

        assertThat(result).isEqualTo(3569916L)
    }

    @Test
    fun part2() {
        val map = readInput("Day01Example.txt").map { pair ->
            val first = pair.substringBefore(" ").toLong()
            val second = pair.substringAfterLast(" ").toLong()
            first to second
        }

        val result = map
            .associateWith { pair ->
                pair.first * map.count { it.second == pair.first }
            }
            .values
            .sum()

        val grouped: Map<Long, Int> = map
            .unzip().second
            .groupingBy { it }
            .eachCount()
        val result2 = map.unzip().first.map { // or sumOf()
            grouped.getOrDefault(it, 0) * it
        }.sum()

        assertThat(result).isEqualTo(31)
        assertThat(result2).isEqualTo(31)
    }
}