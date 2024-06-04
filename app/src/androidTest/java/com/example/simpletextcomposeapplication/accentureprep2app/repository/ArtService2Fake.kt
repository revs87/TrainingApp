package com.example.simpletextcomposeapplication.accentureprep2app.repository

import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtApi2
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtItemDetailsResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtItemResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtService2
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ArtService2Fake(mockWebServer: MockWebServer) : ArtService2 {

    private val service: ArtApi2 = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ArtApi2::class.java)

    override suspend fun getArtList(): List<ArtItemResponse> = service.getArtList().data
    override suspend fun getArtDetails(id: Long): ArtItemDetailsResponse = service.getArtDetails(id).data
}