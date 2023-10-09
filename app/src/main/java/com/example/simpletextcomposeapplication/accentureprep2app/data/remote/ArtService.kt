package com.example.simpletextcomposeapplication.accentureprep2app.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ArtService2 {

    private val service: ArtApi2

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(CustomInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.artic.edu/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ArtApi2::class.java)
    }

    suspend fun getArtList(): List<ArtItemResponse> = service.getArtList().data
}