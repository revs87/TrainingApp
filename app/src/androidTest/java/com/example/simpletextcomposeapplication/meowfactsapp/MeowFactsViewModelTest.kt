package com.example.simpletextcomposeapplication.meowfactsapp

import app.cash.turbine.test
import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.di.AppModule
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class MeowFactsViewModelTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    lateinit var repo: MeowFactsRepository

    private lateinit var viewModel: MeowFactsViewModel


    @Before
    fun setUp() {
        hiltRule.inject()

        viewModel = MeowFactsViewModel(
            dispatcherProvider,
            repo
        )
    }

    @Test
    fun addMeowFact() = runBlocking {
        val job1 = launch {
            viewModel.state
                .asFlow()
                .test {
                    cancelAndConsumeRemainingEvents()
                }
        }
        job1.join()
//        viewModel.addMeowFact(1)
        job1.cancel()
    }
}