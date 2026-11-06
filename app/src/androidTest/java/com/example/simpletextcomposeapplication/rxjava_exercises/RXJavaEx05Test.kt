package com.example.simpletextcomposeapplication.rxjava_exercises

import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Test
import java.util.concurrent.TimeUnit

class RXJavaEx05Test {

    /**
     * Exercise 5: The Search Bar – Handling Rapid Inputs
     * Scenario: The user is typing in a SearchView. If you make an API call for every keystroke, you will overwhelm your server.
     *
     * Task:
     *      1. Create a PublishSubject<String> to simulate typing events from a SearchView.
     *      2. Apply an operator that waits for the user to stop typing for exactly 500 milliseconds before emitting the final string.
     *      3. Push dummy letters to the subject rapidly, pause, then push more.
     * Key Operator: debounce()
     * */
    @Test
    fun rxJavaExercise5() {
        val testScheduler = TestScheduler()

        val searchSubject = PublishSubject.create<String>()

        val testObserver = searchSubject
            .debounce(500, TimeUnit.MILLISECONDS, testScheduler)
            .doOnNext { query ->
                println("-> API Call Triggered for: $query")
            }
            .test()

        println("User typing: R")
        searchSubject.onNext("R")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        println("User typing: Rx")
        searchSubject.onNext("Rx")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        println("User typing: RxJ")
        searchSubject.onNext("RxJ")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        println("User typing: RxJava")
        searchSubject.onNext("RxJava")

        // The debounce window of 500ms has not been met, so nothing should be emitted yet.
        testObserver.assertEmpty()

        println("User pauses for 500ms...")
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        // Emitted

        testObserver.assertValue("RxJava")

        // Push more letters rapidly
        println("User typing: RxJava R")
        searchSubject.onNext("RxJava R")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        println("User typing: RxJava Rocks")
        searchSubject.onNext("RxJava Rocks")

        println("User pauses for 500ms...")
        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        // Emitted

        testObserver.assertValues("RxJava", "RxJava Rocks")


        searchSubject.onComplete()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }

}