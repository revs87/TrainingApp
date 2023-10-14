package com.example.simpletextcomposeapplication.accentureprep2app.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface ArtService2 {
    suspend fun getArtList(): List<ArtItemResponse>

    suspend fun getArtDetails(id: Long): ArtItemDetailsResponse
}

class ArtService2Impl : ArtService2 {

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

    override suspend fun getArtList(): List<ArtItemResponse> = service.getArtList().data
    override suspend fun getArtDetails(id: Long): ArtItemDetailsResponse = service.getArtDetails(id).data
}