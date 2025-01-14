package com.example.simpletextcomposeapplication.adventofcode

import com.example.simpletextcomposeapplication.adventofcode.input.readInput
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.math.abs


/**
 * https://adventofcode.com/2024/day/2
 * */
class Day02 {
    @Test
    fun part1() = runBlocking {
        flow<List<Int>> {
            readInput("Day02Example.txt").map { line ->
                emit(line.split(" ").map { it.toInt() })
            }
        }.map { report ->
            report.safeness().also { println(it.id) }
        }.filter {
            it == Safeness.SAFE
        }.runningFold(0) { acc, value ->
            acc + 1
        }.collect { safeCount ->
            println(safeCount)
        }
    }

    private enum class Safeness(val id: String) {
        SAFE("Safe"),
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
                lastValue = value
                continue
            }
            if (isIncreasing != (lastValue < value) || abs(lastValue - value) > 3 || lastValue == value) {
                return Safeness.UNSAFE
            }
            lastValue = value
        }
        return Safeness.SAFE
    }
}