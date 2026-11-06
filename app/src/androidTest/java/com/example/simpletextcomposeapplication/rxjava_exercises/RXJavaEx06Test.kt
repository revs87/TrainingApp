package com.example.simpletextcomposeapplication.rxjava_exercises

import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Test
import java.util.concurrent.TimeUnit

class RXJavaEx06Test {

    /**
     * Exercise 6: Preventing Double Clicks
     * Scenario: A user aggressively taps the "Submit Payment" button, which could trigger multiple identical API calls.
     *
     * Task:
     *      1. Simulate a button click stream using a PublishSubject<Unit>.
     *      2. Apply an operator that takes the first click, and ignores any subsequent clicks for the next 1 second.
     * Key Operator: throttleFirst()
     * */
    @Test
    fun rxJavaExercise6() {
        val testScheduler = TestScheduler()

        val buttonClicks = PublishSubject.create<Unit>()

        val testObserver = buttonClicks
            // Take the first click, and ignore subsequent clicks for 1 second
            .throttleFirst(1, TimeUnit.SECONDS, testScheduler)
            .doOnNext {
                println("-> Action Triggered: Submit Payment API Call!")
            }
            .test()

        println("User clicks Submit (0ms)")
        buttonClicks.onNext(Unit)

        testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS)
        println("User clicks Submit again (200ms)")
        buttonClicks.onNext(Unit)

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)
        println("User clicks Submit again (500ms)")
        buttonClicks.onNext(Unit)

        // First click allowed + other clicks discarded
        testObserver.assertValueCount(1)

        println("Waiting to pass the 1 second mark...")
        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS) // Total time elapsed: 1100ms

        println("User clicks Submit (1100ms) - Window is now open again")
        buttonClicks.onNext(Unit)

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        println("User clicks Submit (1200ms) - Blocked again")
        buttonClicks.onNext(Unit)

        // 2 clicks made it through the throttle window
        testObserver.assertValueCount(2)


        buttonClicks.onComplete()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }

}