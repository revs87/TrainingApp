package com.example.simpletextcomposeapplication.di

import android.content.Context
import androidx.room.Room
import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.TestDispatchers
import com.example.simpletextcomposeapplication.accentureprep2app.data.db.ArtDao2
import com.example.simpletextcomposeapplication.accentureprep2app.data.db.ArtDatabase
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtService2
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtService2Impl
import com.example.simpletextcomposeapplication.accentureprep2app.repository.ArtRepository
import com.example.simpletextcomposeapplication.accentureprep2app.repository.ArtRepositoryImpl
import com.example.simpletextcomposeapplication.accentureprep3app.ArtyViewModel
import com.example.simpletextcomposeapplication.accentureprep3app.data.db.ArtyDao
import com.example.simpletextcomposeapplication.accentureprep3app.data.db.ArtyDatabase
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyApi
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyService
import com.example.simpletextcomposeapplication.accentureprep3app.data.repository.ArtyRepository
import com.example.simpletextcomposeapplication.accentureprep3app.data.repository.ArtyRepositoryImpl
import com.example.simpletextcomposeapplication.accentureprep4app.ArtiViewModel
import com.example.simpletextcomposeapplication.accentureprep4app.data.api.ArtiApi
import com.example.simpletextcomposeapplication.accentureprep4app.data.api.ArtiService
import com.example.simpletextcomposeapplication.accentureprep4app.data.repository.ArtiRepository
import com.example.simpletextcomposeapplication.accentureprep4app.data.repository.ArtiRepositoryImpl
import com.example.simpletextcomposeapplication.accentureprepapp.data.remote.ArtApi
import com.example.simpletextcomposeapplication.accentureprepapp.data.remote.ArtService
import com.example.simpletextcomposeapplication.accentureprepapp.data.remote.ArtServiceImpl
import com.example.simpletextcomposeapplication.itunestop100.data.local.ITunesAlbumsDatabase
import com.example.simpletextcomposeapplication.itunestop100.data.remote.ITunesAlbumsService
import com.example.simpletextcomposeapplication.itunestop100.data.repository.ITunesAlbumsRepositoryImpl
import com.example.simpletextcomposeapplication.itunestop100.domain.repository.ITunesAlbumsRepository
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsApi
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsService
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsServiceImpl
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepositoryFake
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFactsDatabase
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
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
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
    fun provideMeowFactsRepository(): MeowFactsRepository {
        return MeowFactsRepositoryFake()
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
            print(response)
            return response
        }

        private fun print(response: Response) {
            val json = getResponseBody(response).toString()
            (0..(json.length / LOGGER_ENTRY_MAX_LEN)).forEach {
                val endIndex = (it * LOGGER_ENTRY_MAX_LEN) + LOGGER_ENTRY_MAX_LEN
                val allowedEndIndex =
                    if (endIndex > json.length) json.length - 1
                    else endIndex - 1
                val sub = json.substring(
                    IntRange(
                        start = (it * LOGGER_ENTRY_MAX_LEN),
                        endInclusive = allowedEndIndex
                    )
                )
                println(sub)
            }
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

    /**/

    @Provides
    @Singleton
    fun provideArtService2(): ArtService2 {
        return ArtService2Impl()
    }

    /**/

    @Provides
    @Singleton
    fun provideArtDao2(@ApplicationContext context: Context): ArtDao2 {
        return ArtDatabase.createDbForTesting(context).artDao
    }

    @Provides
    @Singleton
    fun provideArtRepository(service: ArtService2, dao2: ArtDao2): ArtRepository {
        return ArtRepositoryImpl(service, dao2)
    }

    /**/

    @Provides
    @Singleton
    fun provideArtyDao(@ApplicationContext context: Context): ArtyDao {
        return ArtyDatabase.createDb(context).dao
    }

    @Provides
    @Singleton
    fun provideArtyRepository(service: ArtyService, dao: ArtyDao): ArtyRepository {
        return ArtyRepositoryImpl(service, dao)
    }

    @Provides
    @Singleton
    fun provideArtyApi(): ArtyApi {
        return ArtyService()
    }

    @Provides
    @Singleton
    fun provideArtyViewModel(repo: ArtyRepository): ArtyViewModel {
        return ArtyViewModel(repo)
    }

    /**/

    @Provides
    @Singleton
    fun providesArtiApi() : ArtiApi {
        return ArtiService()
    }

    @Provides
    @Singleton
    fun providesArtiRepository(api: ArtiApi) : ArtiRepository {
        return ArtiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesArtiViewModel(repo: ArtiRepository) : ArtiViewModel {
        return ArtiViewModel(repo)
    }


    @Provides
    @Singleton
    fun provideITunesAlbumsService(): ITunesAlbumsService {
        return ITunesAlbumsService()
    }
    @Provides
    @Singleton
    fun provideITunesAlbumsDatabase(@ApplicationContext context: Context): ITunesAlbumsDatabase {
        return ITunesAlbumsDatabase.createDbForTesting(context)
    }
    @Provides
    @Singleton
    fun provideITunesAlbumsRepository(
        service: ITunesAlbumsService,
        db: ITunesAlbumsDatabase
    ): ITunesAlbumsRepository {
        return ITunesAlbumsRepositoryImpl(service, db)
    }

}