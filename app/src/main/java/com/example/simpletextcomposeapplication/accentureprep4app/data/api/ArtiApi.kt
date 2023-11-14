package com.example.simpletextcomposeapplication.accentureprep4app.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtiApi {

    @GET("/api/v1/artworks")
    suspend fun getArtiList(@Query("fields") fields: String): ArtiListResponse

    @GET("/api/v1/artworks/{id}")
    suspend fun getArtiDetails(@Path("id") id: Long): ArtItemResponse

}

class ArtiService : ArtiApi {

    private val service: ArtiApi

    init {
        val okHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.artic.edu/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ArtiApi::class.java)
    }

    override suspend fun getArtiList(fields: String): ArtiListResponse = service.getArtiList(fields)

    override suspend fun getArtiDetails(id: Long): ArtItemResponse  = service.getArtiDetails(id)
}