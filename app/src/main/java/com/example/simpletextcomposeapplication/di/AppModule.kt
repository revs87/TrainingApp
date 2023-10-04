package com.example.simpletextcomposeapplication.di

import android.content.Context
import androidx.room.Room
import com.example.simpletextcomposeapplication.DefaultDispatchers
import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsApi
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsService
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsServiceImpl
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepositoryImpl
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFactsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatchers()
    }

    @Provides
    @Singleton
    fun provideMeowFactsDatabase(@ApplicationContext context: Context): MeowFactsDatabase {
        return Room.databaseBuilder(
            context,
            MeowFactsDatabase::class.java,
            MeowFactsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMeowFactsApi(): MeowFactsApi {
        return createApi()
    }

    private fun createApi(): MeowFactsApi {
        val okHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://meowfacts.herokuapp.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MeowFactsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMeowFactsService(api: MeowFactsApi): MeowFactsService {
        return MeowFactsServiceImpl(api)
    }

    @Provides
    @Singleton
    fun provideMeowFactsRepository(service: MeowFactsService, db: MeowFactsDatabase
    ): MeowFactsRepository {
        return MeowFactsRepositoryImpl(
            service,
            db.meowFactsDao
        )
    }

}