package com.example.simpletextcomposeapplication.component

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import assertk.assertions.isTrue
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest {

    @BeforeEach
    fun setUp() {}

    @AfterEach
    fun tearDown() {}


    @Test
    fun `Execution block returns the sum of each delay's execution time`() {
        runBlocking {
            val time = measureTimeMillis {
                delay(1000)
                delay(500)
            }
            assertThat(time).isGreaterThan(1500)
        }
    }

    @Test
    fun `Concurrent coroutines return the sum of each execution time - launch without join()`() {
        runBlocking {
            val time = measureTimeMillis {
                launch { delay(1000) } // A new instance of Job is always assigned to a new coroutine
                launch { delay(500) }
            }
            assertThat(time).isLessThan(40)
        }
    }

    @Test
    fun `Concurrent coroutines return the sum of each execution time - launch with join() - when result doesn't matter`() {
        runBlocking {
            val time = measureTimeMillis {
                val c1 = launch { delay(1000) }
                val c2 = launch { delay(500) }
                c1.join() // Wait for the coroutine to complete.
                c2.join() // When both are completed, continue.
            }
            assertThat(time).isLessThan(1500)
        }
    }

    @Test
    fun `Asynchronous coroutines return an execution time lesser than the sum of each execution time - async - result matters!`() {
        runBlocking {
            val time = measureTimeMillis {
                val c1 = async { delay(1000); true }
                val c2 = async { delay(500); true }
                val res1 = c1.await()
                val res2 = c2.await()
                assertThat(res1 && res2).isTrue()
            }
            assertThat(time).isLessThan(1500)

            //listOf(c1, c2).awaitAll().map { assertThat(it).isTrue() }
        }
    }
}