package com.example.simpletextcomposeapplication.accentureprep3app.data.remote

import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.CustomInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

interface ArtyApi {

    @GET("/api/v1/artworks")
    suspend fun getArtyList(
        @Query("fields") fields: String = "id,title,artist_display,date_display,main_reference_number",
        @Query("limit") limit: String = "20",
    ): ArtyListResponse

    @GET("/api/v1/artworks/{id}")
    suspend fun getArtyDetails(
        @Path("id") id: Long
    ): ArtyDetailsResponse

}

class ArtyService @Inject constructor() : ArtyApi {

    private val service: ArtyApi

    init {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(CustomInterceptor()).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.artic.edu/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ArtyApi::class.java)
    }

    override suspend fun getArtyList(fields: String, limit: String): ArtyListResponse = service.getArtyList()
    override suspend fun getArtyDetails(id: Long): ArtyDetailsResponse = service.getArtyDetails(id)
}