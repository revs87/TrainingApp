package com.example.simpletextcomposeapplication.accentureprepapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApi {

    @GET("/api/v1/artworks")
    suspend fun getArtItems(@Query("fields") fields: String, @Query("limit") limit: Int): ArtItemsResponse

}
