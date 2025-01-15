package com.example.simpletextcomposeapplication.adventofcode

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.simpletextcomposeapplication.adventofcode.input.readInput
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


/**
 * https://adventofcode.com/2024/day/2
 * */
class Day02 {
    @Test
    fun part1(): Unit = runBlocking {
//        val expected = 2
        val expected = 526
        val sum = flow<List<Int>> {
//            readInput("Day02Example.txt").map { line ->
            readInput("Day02Input.txt").map { line ->
                emit(line.split(" ").map { it.toInt() })
            }
        }.map { report ->
            report.safeness().also { println(it.id + " " + report) }
        }.filter {
            it == Safeness.SAFE
        }.count().also(::println)

        assertThat(sum).isEqualTo(expected)
    }

    private enum class Safeness(val id: String) {
        SAFE("Safe  "),
        SAFE_WITH_DAMPENER("SafeWD"),
        UNSAFE("Unsafe")
    }

    private fun List<Int>.safeness(): Safeness {
        val report = this
        var isIncreasing: Boolean? = null
        var lastValue: Int? = null

        for (value in report) {
            if (lastValue == null) {
                lastValue = value
                continue
            }
            if (isIncreasing == null) {
                isIncreasing = lastValue < value
            }
            val invalidTendency = when (isIncreasing) {
                true -> lastValue > value
                false -> lastValue < value
            }
            val validAdjacents = when (isIncreasing) {
                true -> (value - lastValue) in  1..3
                false -> (lastValue - value) in 1..3
            }
            if (lastValue == value || invalidTendency || !validAdjacents) {
                return Safeness.UNSAFE
            }
            lastValue = value
        }
        return Safeness.SAFE
    }

    private fun List<Int>.safeness(withDampener: Boolean = false): Safeness {
        if (!withDampener) return this.safeness()
        if (this.safeness() == Safeness.SAFE) return Safeness.SAFE

        this.forEachIndexed { index, value ->
            val dampenerList = this.toMutableList().also { it.removeAt(index) }
            if (dampenerList.safeness() == Safeness.SAFE) return Safeness.SAFE_WITH_DAMPENER
        }
        return Safeness.UNSAFE
    }

    @Test
    fun part2(): Unit = runBlocking {
//        val expected = 4
        val expected = 566
        val sum = flow<List<Int>> {
//            readInput("Day02Example.txt").map { line ->
            readInput("Day02Input.txt").map { line ->
                emit(line.split(" ").map { it.toInt() })
            }
        }.map { report ->
            report.safeness(withDampener = true).also { println(it.id + " " + report) }
        }.filter {
            it == Safeness.SAFE || it == Safeness.SAFE_WITH_DAMPENER
        }.count().also(::println)

        assertThat(sum).isEqualTo(expected)
    }
}