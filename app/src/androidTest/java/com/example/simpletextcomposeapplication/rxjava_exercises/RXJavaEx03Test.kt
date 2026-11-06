package com.example.simpletextcomposeapplication.rxjava_exercises

import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

class RXJavaEx03Test {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /**
     * Exercise 3: Transforming Data for the UI
     * Scenario: Your API returns a User object with firstName and lastName, but your UI needs a single
     * fullName string formatted in uppercase.
     *
     * Task:
     *      1. Emit a User object using Observable.just().
     *      2. Intercept the emission and transform the object into a formatted string.
     * Key Operator: map()
     * */
    @Test
    fun rxJavaExercise3() {
        val expectedFullName = "JOHN MARTINDALE"

        Observable.just(User("John", "Martindale"))
            .map { user ->
                UserUppercaseFormatter.format(user)
            }
            .doOnNext { formattedName ->
                println("UI displays: $formattedName")
            }
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(expectedFullName)
    }

    data class User(val firstName: String, val lastName: String)

    data object UserUppercaseFormatter {
        fun format(user: User) = "${user.firstName} ${user.lastName}".uppercase()
    }
}