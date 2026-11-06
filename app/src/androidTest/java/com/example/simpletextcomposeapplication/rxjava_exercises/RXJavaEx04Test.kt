package com.example.simpletextcomposeapplication.rxjava_exercises

import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

class RXJavaEx04Test {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /**
     * Exercise 4: Filtering Unwanted Data
     * Scenario: You are polling a server for notifications, but you only want to alert the user if
     * the notification has a priority level of "HIGH".
     *
     * Task:
     *      1. Create an Observable emitting a list of Notification objects with varying priorities.
     *      2. Filter the stream so that only "HIGH" priority notifications reach the subscribe block.
     * Key Operator: filter()
     * */
    @Test
    fun rxJavaExercise4() {
        val serverNotifications = listOf(
            Notification(1, "New like on your photo", Priority.LOW),
            Notification(2, "Server maintenance at midnight", Priority.MEDIUM),
            Notification(3, "Security alert: New login", Priority.HIGH),
            Notification(4, "Weekly newsletter", Priority.LOW),
            Notification(5, "Payment failed", Priority.HIGH)
        )

        val expectedHighPriorityNotifications = listOf(
            Notification(3, "Security alert: New login", Priority.HIGH),
            Notification(5, "Payment failed", Priority.HIGH)
        )

        Observable.fromIterable(serverNotifications)
            .filter { notification ->
                notification.priority == Priority.HIGH
            }
            .doOnNext { notification ->
                println("ALERT (HIGH PRIORITY): ${notification.message}")
            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValueSequence(expectedHighPriorityNotifications)
    }

    enum class Priority {
        LOW, MEDIUM, HIGH
    }

    data class Notification(val id: Int, val message: String, val priority: Priority)
}