package com.example.simpletextcomposeapplication.di

import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.TestDispatchers
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepositoryFake
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return TestDispatchers()
    }

    @Provides
    @Singleton
    fun provideMeowFactsRepository(): MeowFactsRepository {
        return MeowFactsRepositoryFake()
    }

}