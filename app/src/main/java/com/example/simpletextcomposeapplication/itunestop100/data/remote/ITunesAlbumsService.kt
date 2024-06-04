package com.example.simpletextcomposeapplication.itunestop100.data.remote

import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.CustomInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ITunesAlbumsService {

    private val api: ITunesAlbumsApi = createService()

    private fun createService(): ITunesAlbumsApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(CustomInterceptor()).build()
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/us/rss/topalbums/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ITunesAlbumsApi::class.java)
    }

    suspend fun getAlbums() = api.getAlbums()
}