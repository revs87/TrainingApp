package com.example.simpletextcomposeapplication.accentureapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApi {

    @GET("/api/v1/artworks")
    suspend fun getArtItems(@Query("fields") fields: String, @Query("limit") limit: Int): ArtItemsResponse

}
