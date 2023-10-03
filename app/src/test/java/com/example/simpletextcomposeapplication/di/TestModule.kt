package com.example.simpletextcomposeapplication.di

import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.component.TestDispatchers
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

}