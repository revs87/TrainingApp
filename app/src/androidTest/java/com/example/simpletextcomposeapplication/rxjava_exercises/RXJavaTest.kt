package com.example.simpletextcomposeapplication.rxjava_exercises


import androidx.collection.arraySetOf
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.example.simpletextcomposeapplication.rxjava_exercises.data.local.RXUserDatabase
import com.example.simpletextcomposeapplication.rxjava_exercises.data.local.RXUserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random


class RXJavaTest {

    private lateinit var db: RXUserDatabase

    @Before
    fun setUp() {
        db = RXUserDatabase.createDbForTesting(InstrumentationRegistry.getInstrumentation().targetContext)
        populateDb()
    }

    @After
    fun finish() {

    }

    private fun populateDb() {
        val names = arraySetOf("John", "Jane", "Bob", "Alice", "Charlie", "Dave", "Eve", "Frank", "Grace", "Hank")
        val usersList = names.map {
            RXUserEntity(
                username = it,
                lastLogin = Random.nextLong(1778640305660, 1779640305660)
            )
        }
//                usersList.forEach {
//                    println("${it.username.padStart(7)} ${Date(it.lastLogin)}")
//                }
        db.userDao().insertUsers(usersList)
    }

    /**
     * Exercise 1: The Basics – Single and Completable
     * Scenario: You need to fetch a single user profile from a local Room database and then save a "last login" timestamp.
     *
     * Task:
     *     1. Create a Single<User> that emits a dummy user.
     *     2. Create a Completable that simulates saving data to a database.
     *     3. Subscribe to the Single, print the user, and upon success, trigger the Completable.
     *        Key Types: Single.just(), Completable.fromAction(), subscribe()
     * */
    private fun single(): Single<RXUserEntity> {
        return Single.create { emitter ->
            runCatching {
                val user = db.userDao().getUser("Eve")
                emitter.onSuccess(user)
            }.onFailure {
                emitter.onError(Exception("Failed to find user: $it"))
            }
        }
    }
    private fun completable(user: RXUserEntity): Completable {
        return Completable.create { emitter ->
            runCatching {
                val updatedUser = user.copy(lastLogin = System.currentTimeMillis())
                db.userDao().insertUsers(listOf(updatedUser))
                println("User saved: $updatedUser")
                emitter.onComplete()
            }.onFailure {
                emitter.onError(Exception("Failed to save user: $user"))
            }
        }
    }
    @Test
    fun rxJavaExercise1() {
        single()
            .subscribeOn(Schedulers.trampoline())
            .flatMapCompletable { user ->
                println("User found: $user")
                completable(user)
            }
            .doOnComplete {
                val expected = "Eve"

                val actualUserExists = db.userDao().userExists(expected)
                assertThat(actualUserExists).isTrue()
                println("User exists: $expected")

                db.userDao().getUsers().maxByOrNull { it.lastLogin }?.let {
                    assertThat(it.username).isEqualTo(expected)
                    println("User with most recent login: ${it.username}")
                }
            }
            .test()
            .assertComplete()
    }

}