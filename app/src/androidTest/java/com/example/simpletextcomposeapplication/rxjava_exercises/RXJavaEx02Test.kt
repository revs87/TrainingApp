package com.example.simpletextcomposeapplication.rxjava_exercises

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class RXJavaEx02Test {

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

    private fun fetchStringsFromNetwork(): Observable<List<String>> {
        return Observable.create { emitter ->
            println("1. Fetching data")
            try {
                Thread.sleep(1000)
                val simulatedData = listOf("John", "Jane", "Bob")
                emitter.onNext(simulatedData)
                emitter.onComplete()
            } catch (e: Exception) {
                if (!emitter.isDisposed) {
                    emitter.onError(e)
                }
            }
        }
    }

    /**
     * Exercise 2: Threading – The Android Core
     * Scenario: Android throws a NetworkOnMainThreadException if you do heavy work on the UI thread.
     *
     * You need to fetch data in the background and display it on the UI.
     * Task:
     *       1. Create an Observable that emits a list of strings (simulating a heavy network call).
     *       2. Use Schedulers to run the emission on the IO thread.
     *       3. Observe the result on the Android Main Thread.
     *       4. Update a dummy "TextView" (print to console simulating UI update).
     * Key Operators: subscribeOn(Schedulers.io()), observeOn(AndroidSchedulers.mainThread())
     * */
    @Test
    fun rxJavaExercise2() {
        val expectedList = listOf("John", "Jane", "Bob")

        val testObserver = fetchStringsFromNetwork()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { data ->
                println("2. Updating UI")
                println("   -> TextView text set to: $data")
            }
            .test()

        testObserver.awaitDone(3, TimeUnit.SECONDS)

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(expectedList)
    }

//    @Test
//    fun rxJavaExercise2() {
//        val latch = CountDownLatch(1)
//
//        fetchStringsFromNetwork()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { data ->
//                    println("2. Updating UI on thread: ${Thread.currentThread().name}")
//                    println("   -> TextView text set to: $data")
//                    latch.countDown()
//                },
//                { error ->
//                    println("Error occurred: ${error.message}")
//                    latch.countDown()
//                },
//                {
//                    println("3. Stream complete.")
//                }
//            )
//
//        latch.await(3, TimeUnit.SECONDS)
//    }
}