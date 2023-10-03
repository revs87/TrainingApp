package com.example.simpletextcomposeapplication.component

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FlowsTest {

    private lateinit var sharedFlow: MutableSharedFlow<Pair<Boolean, Int>>

    @BeforeEach
    fun setUp() {
        sharedFlow = MutableSharedFlow()
    }


    @Test
    fun `Testing a cold flow`() {
        runBlocking {
            flow {
                (0..10).forEach {
                    emit(it)
                    delay(1000L)
                }
            }.collect { println(it) }
        }
    }

    @Test
    fun `Testing a hot flow`() {
        runBlocking {
            launch {
                (0..10).forEach {
                    sharedFlow.emit(Pair(true, it))
                    delay(1000L)
                }
                sharedFlow.emit(Pair(false, -1))
            }

            delay(2100L)

            sharedFlow
                .takeWhile { it.first }
                .collect { println(it) }
        }
    }


    @Test
    fun `Testing a flow data delayed from 0 to 10`() = runBlocking {
        (0..10)
            .asFlow()
            .map {
                delay(100)
                it
            }
            .test {
                (0..10).forEach {
                    val emission = awaitItem()
                    assertThat(emission).isEqualTo(it)
                }
//                awaitComplete()
                cancelAndConsumeRemainingEvents()
            }
    }

}
