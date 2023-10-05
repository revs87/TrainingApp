package com.example.simpletextcomposeapplication.di

import android.content.Context
import androidx.room.Room
import com.example.simpletextcomposeapplication.DefaultDispatchers
import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.accentureapp.data.remote.ArtApi
import com.example.simpletextcomposeapplication.accentureapp.data.remote.ArtService
import com.example.simpletextcomposeapplication.accentureapp.data.remote.ArtServiceImpl
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsApi
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsService
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsServiceImpl
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepositoryImpl
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFactsDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
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



    @Provides
    @Singleton
    fun provideArtApi(): ArtApi {
        return createArtApi()
    }

    private fun createArtApi() : ArtApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(CustomInterceptor())
            .build()

        val gson = GsonBuilder().setLenient().create()
        val gsonFactory = GsonConverterFactory.create(gson)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.artic.edu/")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()

        return retrofit.create(ArtApi::class.java)
    }

    private class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            println("INTERCEPTOR request: " + getRequestBody(request))

            val response = chain.proceed(request)

            println("INTERCEPTOR response: " + getResponseBody(response))

            return response
        }
    }

    private fun getResponseBody(response: Response): String? {
        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source?.buffer
        val contentType = responseBody?.contentType()
        val charset: Charset =
            contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        return buffer?.clone()?.readString(charset)
    }

    private fun getRequestBody(request: Request): String {
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val contentType = requestBody?.contentType()
        val charset: Charset =
            contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        return buffer.readString(charset)
    }

    @Provides
    @Singleton
    fun provideArtService(api: ArtApi): ArtService {
        return ArtServiceImpl(api)
    }


}